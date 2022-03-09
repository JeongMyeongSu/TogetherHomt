<template>
  <div id="register">
    <register-header
      v-on:goBack="onGoBack"
      v-on:registerFeed="onRegisterFeed"
    />
    <div id="register-container">
      <register-main :isRegister="isRegister" />
    </div>
  </div>
</template>

<script>
import RegisterHeader from "@/components/register/RegisterHeader";
import RegisterMain from "@/components/register/RegisterMain";

import "@/assets/css/register.css";

export default {
  name: "Register",
  components: {
    RegisterHeader,
    RegisterMain,
  },
  data() {
    return {
      isRegister: false,
    };
  },
  methods: {
    // 뒤로 가기
    onGoBack() {
      this.$router.back();
    },
    // 피드 등록하기
    onRegisterFeed(value) {
      this.isRegister = value;
    },
  },
};
</script>

<style></style>
