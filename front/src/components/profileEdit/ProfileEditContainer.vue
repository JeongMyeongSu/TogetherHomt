<template>
  <div id="profile-edit-container">
    <div>
      <div id="profile-edit-main">
        <div class="profile">
          <div class="view">
            <img :src="info.imagePath" alt="프로필 사진" />
          </div>
          <div class="edit">
            <label for="upload_profile">프로필 사진 바꾸기</label>
            <input
              type="file"
              id="upload_profile"
              @change="handleFileChange($event)"
              style="display: none"
              accept="image/*"
            />
          </div>
        </div>

        <div class="info">
          <div class="email">
            <span>이메일</span>
            <input type="text" v-model="info.email" disabled />
          </div>
          <div class="username">
            <span>닉네임</span>

            <input type="text" v-model="info.username" />
          </div>
          <div class="introduction">
            <span>자기소개</span>
            <textarea rows="3" v-model="info.introduce" />
          </div>
        </div>

        <div id="profile-edit-footer">
          <div>
            <a @click="deleteUser()">회원 탈퇴</a>
            <router-link :to="{ name: 'PasswordEdit' }">
              비밀번호 변경</router-link
            >
            <a @click="logout()">로그아웃</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";
import { mapState } from "vuex";

export default {
  name: "ProfileEditMain",
  props: ["isEdit"],
  data() {
    return {
      info: {},
      editProfile: null,
      token: sessionStorage.getItem("jwt"),
    };
  },
  computed: {
    // 로그인한 사용자 이메일 가져오기
    ...mapState({ loginUser: (state) => state.userStore.LoginUser }),

    // 내 정보
    ...mapState({ myInfo: (state) => state.myStore.myInfo }),
  },
  methods: {
    handleFileChange(event) {
      this.editProfile = event.target.files[0];
      console.log(this.editProfile);

      const reader = new FileReader();
      reader.onload = (e) => {
        this.info.imagePath = e.target.result;
      };
      reader.readAsDataURL(this.editProfile);
    },

    // 로그아웃
    logout() {
      if (confirm("로그아웃 하시겠습니까?")) {
        sessionStorage.clear();
        localStorage.clear();

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

            this.$router.push({ name: "Login" });
          })
          .catch((err) => {
            console.log(err);
            alert("회원탈퇴를 실패하였습니다.");
          });
      }
    },
  },
  mounted() {
    this.info = this.myInfo;
    this.info.imagePath = "http://3.38.103.222:8092" + this.info.imagePath;
  },
  watch: {
    async isEdit(value) {
      if (value) {
        let formData = new FormData();

        formData.append("username", this.info.username);
        formData.append("introduce", this.info.introduce);

        if (this.editProfile) {
          console.log("들어옴");
          formData.append("picture", this.editProfile);
        }

        // 프로필 편집하기
        await api
          .put(`/user/users/${this.info.email}`, formData, {
            headers: {
              "Content-Type": "multipart/form-data",
              Authorization: sessionStorage.getItem("jwt"),
            },
          })
          .then((res) => {
            alert("변경 완료");
            console.log(res);
          })
          .catch((err) => {
            alert("변경 실패");
            console.log(err.response.data.msg);
          });

        await this.$router.push({
          name: "Profile",
          params: { userName: this.info.username, email: this.info.email },
        });
      }
    },
  },
};
</script>

<style></style>
