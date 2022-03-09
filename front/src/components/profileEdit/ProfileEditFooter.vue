<template>
  <div id="profile-edit-footer">
    <a @click="deleteUser()">회원 탈퇴</a>
    <router-link :to="{ name: 'PasswordEdit' }"> 비밀번호 변경</router-link>
    <a @click="logout()">로그아웃</a>
  </div>
</template>

<script>
import api from "@/api/api.js";

import { mapState } from "vuex";
export default {
  name: "ProfileEditFooter",
  computed: {
    // 로그인한 사용자 이메일 가져오기
    ...mapState({ loginUser: (state) => state.userStore.LoginUser }),
  },
  methods: {
    // 로그아웃
    logout() {
      if (confirm("로그아웃 하시겠습니까?")) {
        sessionStorage.clear();
        localStorage.clear();
        // this.$store.dispatch("userStore/getLoginUser", null);

        this.$router.push({ name: "Login" });
      }
    },
    // 회원 탈퇴
    async deleteUser() {
      console.log(sessionStorage.getItem("jwt"));
      if (confirm("정말 탈퇴하시겠습니까?")) {
        await api
          .delete(`/user/account/withdrawal`, {
            headers: {
              // Authorization: sessionStorage.getItem("jwt"),
              Authorization: localStorage.setItem("jwt"),
            },
          })
          .then((res) => {
            alert("그동안 서비스를 이용해주셔서 감사합니다.");
            console.log(res);

            localStorage.clear();
            sessionStorage.clear(); // 토큰 삭제
            this.$store.clear();
            // this.$store.dispatch("userStore/getLoginUser", null); // LocalStorage 초기화
            // this.$store.dispatch("userStore/getLoginUser", null); // LocalStorage 초기화
            // this.$store.dispatch("userStore/getLoginUser", null); // LocalStorage 초기화
            // this.$store.dispatch("userStore/getLoginUser", null); // LocalStorage 초기화

            this.$router.push({ name: "Login" });
          })
          .catch((err) => {
            console.log(err);
            alert("회원탈퇴를 실패하였습니다.");
          });
      }
    },
  },
};
</script>

<style></style>
