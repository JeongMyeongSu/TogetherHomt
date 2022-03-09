<template>
  <div>
    <div class="motion-detect">
      <div class="container">
        <!-- header -->
        <div id="exercise-header">
          <div @click="goOut()">
            <span class="back-btn material-icons-outlined"
              >keyboard_arrow_left</span
            >
          </div>
          <p class="exercise-title">{{ exercise }}</p>
          <button class="is-exercise fail-btn" @click="doneExercise()">
            미완료
          </button>
        </div>

        <!-- webcam -->
        <div v-if="webcam != null && model != null">
          <canvas id="canvas"></canvas>

          <!-- progress -->
          <!-- <div id="all-progress" v-if="percent2 != null">
            <progress id="percent" :value="percent2" max="1.00"></progress>
          </div> -->
        </div>
        <div id="exercise-gage">
          <div id="label-container"></div>
          <div>
            <progress id="percent" :value="percent" max="1.00"></progress>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import * as tmPose from "@teachablemachine/pose";

import api from "@/api/api.js";
import "@/assets/css/exercise/motiondetect.scss";

let ctx, labelContainer, maxPredictions;

export default {
  data() {
    return {
      exercise: this.$route.query.exercise,
      status: "default",
      percent: null,
      percent2: null,
      success: false,

      webcam: null,
      model: null,

      leftCnt: 0,
    };
  },
  watch: {
    success: function () {
      const btnStatus = document.querySelector(".is-exercise");
      btnStatus.classList.remove("fail-btn");
      btnStatus.classList.add("success-btn");
      btnStatus.innerHTML = "완료";
    },
  },

  mounted() {
    // this.ready();
    this.setModel();
  },

  methods: {
    goOut() {
      this.$router.push({ name: "Exercise" });
      this.webcam.stop();
      this.webcam = null;
    },

    async setModel() {
      if (this.exercise == "하이") {
        this.URL = "https://teachablemachine.withgoogle.com/models/76bwaNQBY/";
      } else if (this.exercise == "목 스트레칭") {
        this.URL = "https://teachablemachine.withgoogle.com/models/GSn9StvR4/";
      } else if (this.exercise == "허리 스트레칭") {
        this.URL = "https://teachablemachine.withgoogle.com/models/mHC8FAGiF/";
      } else if (this.exercise == "팔 스트레칭") {
        this.URL = "https://teachablemachine.withgoogle.com/models/CklpGq-46/";
      } else if (this.exercise == "스쿼트") {
        this.URL = "https://teachablemachine.withgoogle.com/models/FePB01NR1/";
      } else if (this.exercise == "레터럴 레이즈") {
        this.URL = "https://teachablemachine.withgoogle.com/models/gGLZZKc-5/";
      } else if (this.exercise == "크로스 사이드 크런치") {
        this.URL = "https://teachablemachine.withgoogle.com/models/0mC24nKFc/";
      }
      const modelURL = this.URL + "model.json";
      const metadataURL = this.URL + "metadata.json";

      this.model = await tmPose.load(modelURL, metadataURL);
      this.init();
    },

    async init() {
      const size = 400;
      const flip = true; // whether to flip the webcam
      this.webcam = new tmPose.Webcam(size, size, flip); // width, height, flip

      console.log("1");
      console.log(this.webcam);
      await this.webcam.setup(); // request access to the webcam
      console.log("2");
      await this.webcam.play();

      console.log("3");
      window.requestAnimationFrame(this.loop);

      const canvas = document.getElementById("canvas");
      canvas.width = 300;
      canvas.height = size;
      ctx = canvas.getContext("2d");
      labelContainer = document.getElementById("label-container");

      maxPredictions = this.model.getTotalClasses();
      for (let i = 0; i < maxPredictions; i++) {
        labelContainer.appendChild(document.createElement("div"));
      }
      console.log("4");
    },

    async loop() {
      if (this.webcam) {
        this.webcam.update();

        if (this.exercise == "하이") {
          await this.hiPredict();
        } else if (
          this.exercise == "목 스트레칭" ||
          this.exercise == "허리 스트레칭" ||
          this.exercise == "크로스 사이드 크런치"
        ) {
          await this.leftRightPredict();
        } else if (this.exercise == "팔 스트레칭") {
          await this.armPredict();
        } else if (
          this.exercise == "스쿼트" ||
          this.exercise == "레터럴 레이즈"
        ) {
          await this.squatraisePredict();
        }
        window.requestAnimationFrame(this.loop);
      }
    },

    // 안녕 운동
    async hiPredict() {
      const { pose, posenetOutput } = await this.model.estimatePose(
        this.webcam.canvas
      );
      const prediction = await this.model.predict(posenetOutput);

      // 0 : defalut, 1: hi
      if (prediction[0].probability.toFixed(2) == 1.0) {
        this.status = "default";
      } else if (prediction[1].probability.toFixed(2) == 1.0) {
        if (this.status == "default") {
          this.success = true;
        }
        this.status = "hi";
      }
      this.percent = prediction[1].probability.toFixed(2);
      this.drawPose(pose);
    },

    // 목 스트레칭, 허리 스트레칭, 크로스 크런치
    async leftRightPredict() {
      const { pose, posenetOutput } = await this.model.estimatePose(
        this.webcam.canvas
      );
      const prediction = await this.model.predict(posenetOutput);

      // 0 : defalut, 1: left, 2: right
      if (prediction[0].probability.toFixed(2) == 1.0) {
        this.status = "default";
      } else if (prediction[1].probability.toFixed(2) == 1.0) {
        if (this.status != "left") {
          this.leftCnt = 1;
        }
        this.status = "left";
      } else if (prediction[2].probability.toFixed(2) == 1.0) {
        if (this.leftCnt != 0 && this.status == "default") {
          this.success = true;
          this.leftCnt = 0;
        }
        this.status = "right";
      }

      if (
        this.status == "default" &&
        (this.leftCnt == 0 || prediction[1].probability.toFixed(2) > 0.89)
      ) {
        labelContainer.innerHTML = "left";
        this.percent = prediction[1].probability.toFixed(2);
      } else if (
        this.leftCnt != 0 ||
        prediction[2].probability.toFixed(2) > 0.9
      ) {
        labelContainer.innerHTML = "right";
        this.percent = prediction[2].probability.toFixed(2);
      }

      this.drawPose(pose);
    },

    async armPredict() {
      const { pose, posenetOutput } = await this.model.estimatePose(
        this.webcam.canvas
      );
      const prediction = await this.model.predict(posenetOutput);

      // 0 : defalut, 1: good, 2: bent
      if (prediction[0].probability.toFixed(2) == 1.0) {
        this.status = "default";
      } else if (prediction[1].probability.toFixed(2) == 1.0) {
        if (this.status == "default" || this.status == "bent") {
          this.success = true;
        }
        this.status = "good";
      } else if (prediction[2].probability.toFixed(2) == 1.0) {
        this.status = "bent";
      }
      for (let i = 0; i < maxPredictions; i++) {
        const classPrediction = prediction[1].className;
        labelContainer.childNodes[0].innerHTML = classPrediction;
      }

      this.percent = prediction[1].probability.toFixed(2);
      this.drawPose(pose);
    },

    // 스쿼트
    async squatraisePredict() {
      const { pose, posenetOutput } = await this.model.estimatePose(
        this.webcam.canvas
      );
      const prediction = await this.model.predict(posenetOutput);

      // 0 : defalut, 1: squat
      // 0 : default, 1: up
      if (prediction[0].probability.toFixed(2) == 1.0) {
        if (this.status == "squat" || this.status == "up") {
          this.success = true;
        }
        this.status = "default";
      } else if (prediction[1].probability.toFixed(2) == 1.0) {
        if (this.exercise == "squat") {
          this.status = "squat";
        } else if (this.exercise == "lateral_raise") {
          this.status = "up";
        }
      }
      for (let i = 0; i < maxPredictions; i++) {
        const classPrediction =
          prediction[i].className + ": " + prediction[i].probability.toFixed(2);
        labelContainer.childNodes[i].innerHTML = classPrediction;
      }

      this.percent = prediction[1].probability.toFixed(2);
      this.drawPose(pose);
    },

    drawPose(pose) {
      if (this.webcam && this.webcam.canvas) {
        ctx.drawImage(this.webcam.canvas, 0, 0);
        // draw the keypoints and skeleton
        if (pose) {
          const minPartConfidence = 1;
          tmPose.drawKeypoints(pose.keypoints, minPartConfidence, ctx);
          tmPose.drawSkeleton(pose.keypoints, minPartConfidence, ctx);
        }
      }
    },

    doneExercise() {
      if (this.success) {
        api({
          method: "put",
          url: `/exercise/today-exercises/${this.exercise}?count-check=1`,
          header: { Authorization: sessionStorage.getItem("jwt") },
        });
        console.log(this.exercise);
        this.goOut();
      } else {
        alert("운동을 완료해주세요");
      }
    },
  },
};
</script>

<style></style>
