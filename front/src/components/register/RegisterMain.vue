<template>
  <div id="register-main">
    <!-- 피드에 등록할 이미지와 내용-->
    <div id="register-feed">
      <div class="feed-img">
        <input
          type="file"
          id="upload_file"
          style="display: none"
          @change="handleFileChange($event)"
        />
        <label for="upload_file">
          <div v-if="viewImg">
            <video
              v-if="isVideo"
              :src="viewImg"
              alt="피드 동영상"
              id="upload_file"
              playsinline
            />
            <img v-else :src="viewImg" alt="피드 사진" id="upload_file" />
          </div>
          <div v-else>
            <img
              src="https://cdn.pixabay.com/photo/2017/02/25/22/55/color-2098963_960_720.png"
              alt="기본 이미지"
              id="upload_file"
            />
          </div>
        </label>
      </div>
      <div class="feed-content">
        <textarea
          placeholder="문구 입력..."
          v-model="postData.content"
        ></textarea>
      </div>
    </div>

    <!-- 키워드 태그 하기 -->
    <div id="register-feed-tag">
      <div class="feed-tag-input">
        <span class="material-icons"> local_offer </span>
        <input
          @keyup.enter="addTag()"
          type="text"
          placeholder="키워드 태그하기..."
          v-model="tag"
        />
      </div>
      <div class="feed-tag-list">
        <div v-for="(tag, index) in postData.tags" :key="index">
          {{ tag }}
          <span class="material-icons" @click="deleteTag(index)"> close </span>
        </div>
      </div>
    </div>

    <!-- 운동 장바구니 목록 -->
    <div id="register-today-exercise">
      <div>
        <div v-for="(todayExercise, index) in todayExercises" :key="index">
          <div v-if="!todayExercise.done">
            <input
              type="checkbox"
              :id="todayExercise.exercise"
              :value="todayExercise.exercise"
              v-model="doneExercises"
            />
            <label :for="todayExercise.exercise">{{
              todayExercise.exercise
            }}</label>
          </div>
        </div>
        <!-- <span>{{ doneExercises }}</span> -->
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";

import { mapState } from "vuex";

export default {
  name: "RegisterMain",
  props: {
    isRegister: {
      type: Boolean,
    },
  },
  data() {
    return {
      todayExercises: [],
      doneExercises: [],

      token: sessionStorage.getItem("jwt"),

      viewImg: null,
      tag: null,
      isVideo: null,

      postData: {
        content: null,
        tags: [],
        media: null,
      },
    };
  },
  computed: {
    // 로그인 유저 아이디
    ...mapState({ loginUser: (state) => state.userStore.LoginUser }),

    // 내 정보
    ...mapState({ myInfo: (state) => state.myStore.myInfo }),
  },
  methods: {
    addTag() {
      this.postData.tags.push(this.tag.replace(/ /gi, ""));
      this.tag = "";
    },
    deleteTag(index) {
      this.postData.tags.splice(index, 1);
    },
    handleFileChange(event) {
      const file = event.target.files[0];

      // 확장자 확인
      this.isVideo = file.type.startsWith("video");

      // 파일 읽기
      if (this.isVideo) {
        this.viewImg = URL.createObjectURL(file);
      } else {
        const reader = new FileReader();
        reader.readAsDataURL(file);

        // 로드 한 후
        reader.onload = (e) => {
          this.viewImg = e.target.result;
        };
      }

      this.postData.media = file;
    },
  },
  watch: {
    // 게시글 등록하기
    async isRegister(value) {
      if (value) {
        // 운동 완료하기
        for (let exercise of this.doneExercises) {
          await api({
            method: "put",
            url: `/exercise/today-exercises/${exercise}?count-check=1`,
            header: { Authorization: sessionStorage.getItem("jwt") },
          });
        }

        const userName = this.myInfo.username;
        console.log(this.myInfo.username);
        let formData = new FormData();

        formData.append("content", this.postData.content);
        formData.append("tagList", this.postData.tags.join(", "));
        formData.append("media", this.postData.media);
        formData.append("username", userName);

        console.log(formData);

        await api
          .post("/feed/feeds", formData, {
            headers: {
              "Content-Type": "multipart/form-data",
              Authorization: sessionStorage.getItem("jwt"),
            },
          })
          .then((res) => {
            alert("게시글 등록 완료!");
            console.log(res);
          })
          .catch((err) => {
            alert("게시글 등록 실패");
            console.log(err);
          });
        if (this.myInfo.username != "admin") {
          await this.$router.push({
            name: "Profile",
            params: {
              userName: this.myInfo.username,
              email: this.myInfo.email,
            },
          });
        } else {
          await this.$router.push({
            name: "AdminPage",
            params: {
              userName: this.myInfo.username,
              email: this.myInfo.email,
            },
          });
        }
      }
    },
  },
  mounted() {
    // 장바구니 목록 가져와서 보여주기
    api
      .get("/exercise/today-exercises", {
        headers: {
          Authorization: sessionStorage.getItem("jwt"),
        },
      })
      .then((res) => {
        this.todayExercises = res.data;
        console.log(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  },
};
</script>

<style></style>
