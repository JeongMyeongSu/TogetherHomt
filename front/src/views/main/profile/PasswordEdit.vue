<template>
  <div id="password-edit">
    <div class="header">
      <div class="cancel" v-on:click="goBack()">취소</div>
      <div class="context">비밀번호 변경</div>
      <div class="done" v-on:click="changePassword()">완료</div>
    </div>

    <div class="container">
      <div>
        <div class="info">
          <div class="current-pw">
            <label>현재 비밀번호</label>
            <input type="text" v-model="currentPassword" />
          </div>
          <div class="new-pw">
            <label>새 비밀번호</label>
            <input type="text" v-model="newPassword" />
          </div>
          <div class="confirm-pw">
            <label>새 비밀번호 확인</label>
            <input type="text" v-model="confirmPassword" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import api from "@/api/api.js";

import "@/assets/css/passwordEdit.css";

export default {
  name: "PasswordEdit",
  data() {
    return {
      currentPassword: null,
      newPassword: null,
      confirmPassword: null,
    };
  },
  computed: {
    // 로그인한 사용자 이메일 가져오기
    ...mapState({ loginUser: (state) => state.userStore.LoginUser }),
  },
  methods: {
    // 취소
    goBack() {
      this.$router.back();
    },

    // 비밀번호 변경
    async changePassword() {
      await api
        .post(
          `/user/login`,
          {
            email: this.loginUser,
            password: this.currentPassword,
          },
          {
            headers: {
              Authorization: sessionStorage.getItem("jwt"),
            },
          }
        )
        .then((res) => {
          const token = res.headers.authorization;
          sessionStorage.setItem("jwt", token);

          // 새 비밀번호 = 새 비밀번호 확인
          if (this.newPassword == this.confirmPassword) {
            // 비밀번호 변경하기
            api
              .put(`/user/account/change-password`, this.confirmPassword, {
                headers: {
                  Authorization: sessionStorage.getItem("jwt"),
                },
              })
              .then((res) => {
                console.log(this.confirmPassword);
                console.log(res);
                alert("비밀번호가 변경되었습니다.");
                this.goBack();
              })
              .catch((err) => {
                console.log(err);
                alert("비밀번호 변경을 실패하였습니다.");
              });
          } else {
            alert("새 비밀번호와 새 비밀번호 확인이 일치하지 않습니다.");
          }
        })
        .catch((err) => {
          console.log(err);
          alert("현재 비밀번호가 일치하지 않습니다.");
        });
    },
  },
};
</script>

<style></style>
