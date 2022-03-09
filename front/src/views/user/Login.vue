<template>
  <div class="user" id="login">
    <!-- 투게더 홈트 로고 -->
    <div id="logo">투게더 홈트</div>

    <!-- 이메일 입력 -->
    <input
      class="login-input-data form-style"
      v-model="user.email"
      placeholder="이메일을 입력해주세요."
      type="text"
    />

    <!-- 비밀번호 입력 -->
    <input
      class="login-input-data form-style"
      v-model="user.password"
      placeholder="비밀번호를 입력해주세요."
      type="password"
      v-on:keyup.enter="Login()"
    />

    <button class="user-btn form-style" @click="Login()">로그인</button>

    <!-- 비밀번호 찾기 -->
    <div class="find-password-box">
      <router-link :to="{ name: 'FindPW' }" class="link-find-password">
        비밀번호 찾기
      </router-link>
    </div>

    <!-- 소셜 로그인 -->
    <div class="social-login">
      <div><Naver /></div>
      <div><Kakao /></div>
      <div><Google /></div>
    </div>

    <!-- 회원 가입 -->
    <div class="end-text-signup">
      <span>아직 <strong>투게더 홈트</strong> 아이디가 없으신가요?</span>
      <router-link :to="{ name: 'Signup' }" class="link-go-signup">
        <strong>회원가입</strong>
      </router-link>
    </div>
  </div>
</template>

<script>
import Naver from "@/components/user/Naver.vue";
import Kakao from "@/components/user/Kakao.vue";
import Google from "@/components/user/Google.vue";

import api from "@/api/api.js";

export default {
  name: "Login",
  components: {
    Naver,
    Kakao,
    Google,
  },

  data: function () {
    return {
      user: {
        email: "",
        password: "",
      },
      errForm: {
        email: "",
        password: "",
      },
    };
  },

  methods: {
    // 로그인
    // get : 데이터 select
    // post : 데이터 insert
    // put : 데이터 modify
    // delete : 데이터 delete

    async Login() {
      await api
        .post("/user/account/login", this.user)
        .then((res) => {
          const token = res.data;
          console.log(res);
          console.log(token);
          sessionStorage.setItem("jwt", token);
          console.log(sessionStorage.getItem("jwt"));

          this.$store.dispatch("userStore/getLoginUser", this.user.email); //로그인 응답으로 username 받음
          this.$router.push({ name: "Feed" });
        })
        .catch((err) => {
          // 어떤 에러냐에 따라 문구 변경
          alert(err);
          this.user.email = "";
          this.user.password = "";
        });

      // await api({
      //   method: "post",
      //   url: `/user/account/login`,
      //   data: this.user,
      // })
      //   .then((res) => {
      //     const token = res.data;
      //     console.log(res);
      //     console.log(token);
      //     sessionStorage.setItem("jwt", token);
      //     console.log(sessionStorage.getItem("jwt"));

      //     this.$store.dispatch("userStore/getLoginUser", this.user.email); //로그인 응답으로 username 받음
      //     this.$router.push({ name: "Feed" });
      //   })
      //   .catch((err) => {
      //     // 어떤 에러냐에 따라 문구 변경
      //     alert(err);
      //     this.user.email = "";
      //     this.user.password = "";
      //   });
    },

    // socialLogin: function () {
    //   axios({
    //     method:'get',
    //     url:`/oauth2/authorization/google`,
    //   })
    //   .then((res) => {
    //     console.log(res)
    //   })
    // },
  },
};
</script>
