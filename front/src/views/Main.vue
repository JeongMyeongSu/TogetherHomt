<template>
  <div id="main">
    <!-- jwt 토큰을 가지고 있을 경우 -->
    <div v-if="token" id="main-form">
      <!-- 헤더 -->
      <Header />

      <!-- 피드 : Feed.vue -->
      <!-- 검색 : Search.vue -->
      <!-- (피드)등록 : Register.vue -->
      <!-- 운동 : Exercise.vue -->
      <!-- 프로필 : Profile.vue -->
      <router-view />

      <!-- 메뉴바 -->
      <Navbar :myInfo="myInfo" />
    </div>

    <!-- jwt 토큰을 가지고 있지 않을 경우 -->
    <!-- <div v-else>{{ logout() }}</div> -->
  </div>
</template>

<script>
import Header from "@/components/common/Header";
import Navbar from "@/components/common/Navbar";

import "@/assets/css/main.css";
import { mapState } from "vuex";

export default {
  name: "Main",
  data() {
    return {
      token: sessionStorage.getItem("jwt"),
    };
  },
  components: {
    Header,
    Navbar,
  },
  computed: {
    // 로그인한 사용자 이메일 가져오기
    ...mapState({ loginUser: (state) => state.userStore.LoginUser }),

    ...mapState({ myInfo: (state) => state.myStore.myInfo }),
  },
  async mounted() {
    // 내 정보 가져오기
    await this.$store.dispatch("myStore/setMyInfo", {
      email: this.loginUser,
      token: this.token,
    });

    // 내 알람 정보 가져오기
    await this.$store.dispatch("myStore/setMyAlarms", {
      token: this.token,
    });
  },
  methods: {
    // 내 정보 조회
    // ...mapActions(["setMyInfo", "setMyFollow", "setMyFeeds"]),

    // 로그아웃
    logout() {
      sessionStorage.clear();
      this.$store.dispatch("userStore/getLoginUser", null);
      this.$router.push({ name: "Login" });
    },
  },
  async updated() {
    // jwt 토근 확인하기
    this.token = sessionStorage.getItem("jwt");
    console.log("Main에서 토큰 확인");
    // 내 정보 조회
    // this.setMyInfo({ email: this.loginUser, token: this.token });
    // 내 알람 정보 가져오기
    await this.$store.dispatch("myStore/setMyAlarms", {
      token: this.token,
    });
  },
};
</script>

<style></style>
