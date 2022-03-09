<template>
  <div class="user" id="findPW">
    <svg
      xmlns="http://www.w3.org/2000/svg"
      enable-background="new 0 0 20 20"
      height="100%"
      width="100%"
      viewBox="0 0 20 20"
      fill="#000000"
    >
      <path
        d="M17,10c0,3.87-3.13,7-7,7c-2.22,0-4.2-1.04-5.48-2.65l1.07-1.07c1,1.35,2.6,2.22,4.41,2.22c3.03,0,5.5-2.47,5.5-5.5 S13.03,4.5,10,4.5c-3.04,0-5.56,2.49-5.49,5.62l1.18-1.18L6.75,10l-3,3l-3-3l1.06-1.06l1.2,1.2C2.93,6.11,6.18,3,10,3 C13.87,3,17,6.13,17,10z M12.25,10v2.25c0,0.41-0.34,0.75-0.75,0.75h-3c-0.41,0-0.75-0.34-0.75-0.75V10c0-0.41,0.34-0.75,0.75-0.75 V8.5C8.5,7.67,9.17,7,10,7s1.5,0.67,1.5,1.5v0.75C11.91,9.25,12.25,9.59,12.25,10z M10.75,8.5c0-0.41-0.34-0.75-0.75-0.75 S9.25,8.09,9.25,8.5v0.75h1.5V8.5z"
      />
    </svg>
    <h3>비밀번호를 잊으셨나요?</h3>

    <div>이메일로 임시 비밀번호를 발송해드립니다.</div>
    <div class="email">
      <input v-model="emailFront" type="text" class="form-style" />
      <p>@</p>
      <input v-model="emailBack" type="text" class="form-style" />
    </div>
    <button class="user-btn" @click="sendPW()">임시 비밀번호 받기</button>
    <div class="end-text-signup">
      <span>임시비밀번호로</span>
      <router-link :to="{ name: 'Login' }" class="link-go-signup">
        <strong>로그인</strong></router-link
      >
      <span>하러가기</span>
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";

export default {
  name: "FindPW",
  data() {
    return {
      emailFront: "",
      emailBack: "",
    };
  },
  methods: {
    sendPW: function () {
      console.log("임시비밀번호 찾기 요청 보냄!");
      api({
        method: "patch",
        url: "/user/account/reset-password",
        data: this.emailFront + "@" + this.emailBack,
        headers: {
          // Authorization : sessionStorage.getItem("jwt"),
          "Content-Type": "text/plain",
        },
      })
        .then((res) => {
          console.log(res);
          this.emailFront = "";
          this.emailBack = "";
          alert("임시 비밀번호가 성공적으로 발송되었습니다.");
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>

<style></style>
