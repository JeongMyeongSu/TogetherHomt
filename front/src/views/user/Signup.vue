<template>
  <div class="user" id="signup">
    <!-- 회원가입 로고 -->
    <div id="logo">회원 가입</div>

    <!-- 닉네임 -->
    <div id="form-box">
      <div class="input-with-label" id="short">
        <label for="nickname">닉네임</label>
        <input
          class="signup-input-data"
          v-model="user.username"
          id="nickname"
          placeholder="닉네임을 3글자 이상 입력하세요."
          type="text"
        />
      </div>

      <!-- 이메일 -->
      <div class="input-with-label" id="short">
        <label for="email">이메일</label>

        <input
          v-model="user.email"
          class="signup-input-data"
          id="email"
          placeholder="이메일을 입력해주세요."
          type="text"
        />
      </div>

      <!-- 인증번호 -->
      <div class="input-with-label">
        <label for="input-confirmnum">인증번호</label>
        <input
          class="signup-input-data"
          v-model="inputConfirmNum"
          id="input-confirmnum"
          placeholder="인증번호를 입력하세요."
          type="text"
        />
        <div v-if="isSendNum">
          <!-- 확인 -->
          <button
            class="send-confirmnum"
            @click="emailConfirm()"
            id="input-confirmnum-btn"
          >
            <div class="font-scale-8">확인</div>
          </button>
          <!-- 재전송 -->
        </div>
        <div v-else>
          <button class="send-confirmnum" @click="sendConfirm()">
            <div class="font-scale-8">인증번호 받기</div>
          </button>
        </div>
      </div>

      <!-- 비밀번호 -->
      <div class="input-with-label" id="password">
        <input
          class="signup-input-data"
          v-model="user.password"
          placeholder="비밀번호를 입력하세요.(영문+숫자 6자리)"
          type="password"
        />
        <label for="password">비밀번호</label>
      </div>

      <!-- 비밀번호 확인 -->
      <div class="input-with-label" id="password-confirm">
        <input
          class="signup-input-data"
          v-model="passwordConfirm"
          type="password"
          placeholder="비밀번호를 입력하세요."
        />
        <label for="passwordConfirm">비밀번호 확인</label>
      </div>

      <!-- 이용약관 -->
      <div class="checkbox">
        <input class="" type="checkbox" id="checkbox" v-model="checked" />
        <label for="checkbox"
          ><strong>이용약관</strong> 및 <strong>개인정보 처리방침</strong>에
          동의합니다.</label
        >
      </div>
    </div>

    <!-- 회원가입 -->
    <div v-if="isSubmit" id="btn-status">
      <button class="user-btn" @click="Signup()">가입 하기</button>
    </div>
    <div v-else id="btn-status">
      <button class="user-btn-reject">가입 하기</button>
    </div>

    <!-- 로그인 -->
    <div class="end-text-signup">
      <span>이미 계정이 있으신가요?</span>
      <router-link :to="{ name: 'Login' }" class="link-go-signup">
        <strong>로그인</strong>
      </router-link>
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";

export default {
  name: "Signup",
  data: function () {
    return {
      user: {
        username: "",
        email: "",
        password: "",
      },
      inputConfirmNum: "", // 인증번호 입력
      emailConfirmNum: "",

      isSendNum: false, // 인증번호 보내졌니
      isConfirm: false, // 인증번호 일치?

      passwordConfirm: null,

      ispasswordConfirm: false,
      isSubmit: false, // true: 버튼 활성화

      passwordType: "password",
      checked: false,
    };
  },

  methods: {
    // 회원가입
    Signup: function () {
      console.log(this.user);
      api({
        method: "post",
        url: `/user/account/sign-up`,
        data: this.user,
      })
        .then(() => {
          this.$router.push({ name: "DoneSignup" });
        })
        .catch((err) => {
          alert(err);
        });
    },

    // 이메일 인증 발송
    sendConfirm: function () {
      if (this.user.email != null && this.user.email.includes("@")) {
        api({
          method: "post",
          url: `/user/account/sign-up/confirm`,
          data: this.user.email,
          headers: { "Content-Type": "text/plain" },
        })
          .then((res) => {
            this.isSendNum = true;

            console.log(res.data);
            this.emailConfirmNum = res.data;
          })
          .catch((err) => {
            alert(err);
          });
      } else {
        alert("올바른 이메일을 입력해주세요!");
      }
    },

    // 인증번호 확인
    emailConfirm: function () {
      if (this.inputConfirmNum == this.emailConfirmNum) {
        console.log("true");
        const confirmNumInputBox = document.querySelector("#input-confirmnum");
        const confirmNumInputBtn = document.querySelector(
          "#input-confirmnum-btn"
        );
        const emailBox = document.querySelector("#email");
        confirmNumInputBox.setAttribute("disabled", true);
        confirmNumInputBtn.setAttribute("disabled", true);
        emailBox.setAttribute("disabled", true);
        this.isConfirm = true;
      } else {
        console.log(this.inputConfirmNum);
        console.log(this.emailConfirmNum);
      }
    },

    isValidForm: function () {
      // 비밀번호 일치여부
      if (this.passwordConfirm === this.user.password) {
        this.ispasswordConfirm = true;
        if (this.user.username && this.isConfirm) {
          this.isSubmit = true;
        }
      } else {
        this.isSubmit = false;
      }

      if (
        this.user.username == "" ||
        this.user.username.length < 3 ||
        this.isConfirm == false ||
        this.checked == false
      ) {
        this.isSubmit = false;
      }
    },
  },

  watch: {
    passwordConfirm: function () {
      this.isValidForm();
    },
    "user.password": function () {
      this.isValidForm();
    },

    user: {
      handler() {
        this.isValidForm();
      },
      deep: true,
    },

    checked: function () {
      this.isValidForm();
    },
  },
};
</script>
