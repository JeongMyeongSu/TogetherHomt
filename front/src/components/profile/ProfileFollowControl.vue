<template>
  <div id="profile-follow-control">
    <div class="follow-btn" v-show="isFollow == -1" @click="onFollow">
      팔로우
    </div>
    <div class="follow-btn" v-show="isFollow != -1" @click="onUnFollow">
      팔로잉
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";
api.defaults.headers.common["Authorization"] = sessionStorage.getItem("jwt");

export default {
  name: "ProfileFollowControl",
  props: ["email", "isFollow"],
  data() {
    return {
      token: sessionStorage.getItem("jwt"),
    };
  },
  methods: {
    // 팔로우 하기
    async onFollow() {
      await api
        .post(`/communication/follows/${this.email}`, null)
        .then((res) => {
          console.log(res);
          alert("팔로우 성공!");
        })
        .catch((err) => {
          console.log(err);
          alert("팔로우 실패!");
        });
      this.$router.go();
    },

    // 언팔로우 하기
    async onUnFollow() {
      await api
        .delete(`/communication/follows/${this.email}`)
        .then((res) => {
          console.log(res);
          alert("언팔로우 성공!");
        })
        .catch((err) => {
          console.log(err);
          alert("언팔로우 실패!");
        });
      this.$router.go();
    },
  },
};
</script>

<style></style>
