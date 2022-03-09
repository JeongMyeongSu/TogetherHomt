<template>
  <div class="userPage">
    <!-- 로그인 : Login.vue -->
    <!-- 회원가입 : Singup.vue -->
    <!-- 비밀번호 찾기 -->
    <router-view />
  </div>
</template>

<script>
import "@/assets/css/user/user.css";
import "@/assets/css/user/login.css";
import "@/assets/css/user/signup.css";
import "@/assets/css/user/doneSingup.css";
import "@/assets/css/user/findPW.css";

export default {
  name: "User",
};
</script>

<style></style>
