<template>
  <div id="exercise-list-basket">
    <div v-for="(exer, index) in todayExercises" :key="index">
      <div id="basket-item" v-if="!exer.done">
        <span id="exercise-circle" @click="goExercise(exer.exercise)">
          <div>{{ exer.exercise }}</div>
        </span>
        <i
          class="fa-solid fa-trash-can"
          @click="removeExercise(exer.exercise)"
        ></i>
      </div>
      <div id="basket-item" v-else>
        <span id="exercise-circle-done">
          <div>
            <span>{{ exer.exercise }}</span> 완료
          </div>
        </span>
        <i
          class="fa-solid fa-trash-can"
          @click="removeExercise(exer.exercise)"
        ></i>
      </div>
      <!-- <span class=""> </span> -->
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";
export default {
  name: "Exerciselistbasket",
  data() {
    return {
      todayExercises: [],
    };
  },
  mounted() {
    // if (localStorage.length > 0) {
    //   for (let i = 0; i < localStorage.length; i++) {
    //     if (
    //       localStorage.key(i) !== "loglevel:webpack-dev-server" &&
    //       localStorage.key(i) !== "vuex"
    //     ) {
    //       this.todayExercises.push(localStorage.getItem(localStorage.key(i)));
    //       // 운동을 판별하는 로직을 다시 짜야할듯 - 리스트랑 비교
    //     }
    //   }
    // } else {
    //   console.log("아직 운동바구니가 비었습니다. ");
    // }
    api
      .get("/exercise/today-exercises", {
        headers: {
          Authorization: sessionStorage.getItem("jwt"),
        },
      })
      .then((res) => {
        this.todayExercises = res.data;
        console.log(res.data);
        alert("장바구니 불러오기 성공");
      })
      .catch((err) => {
        console.log(err);
        alert("장바구니 불러오기 실패");
      });
    // this.$router.go();
    console.log(this.token);
  },
  methods: {
    goExercise(exer) {
      this.exercise = exer;
      this.$router.push({
        name: "MotionDetect",
        query: { exercise: this.exercise },
      });
    },

    //시연영상으로 연결, 준비중..
    gotoexample() {
      this.$router.push({});
    },

    //삭제
    // removeExercise(exercise) {
    //   this.exercise = exercise;
    //   localStorage.removeItem(this.exercise);
    //   alert("운동을 삭제했습니다.");
    //   this.$router.go();
    // },

    removeExercise(exer) {
      this.exercise = exer;
      console.log(this.exercise);
      api
        .delete(`/exercise/today-exercises/${this.exercise}`, {
          //post, patch할때만 null넣는다.
          headers: {
            Authorization: sessionStorage.getItem("jwt"),
          },
        })
        .then((res) => {
          console.log(res);
          alert("삭제성공!");
        })
        .catch((err) => {
          console.log(err);
          alert("삭제실패");
        });

      this.$router.go();
    },
  },
};
</script>

<style lang="scss" scoped></style>
