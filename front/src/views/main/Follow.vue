<template>
  <div id="follow">
    <div>
      <div>
        <follow-header
          v-on:goBack="onGoBack"
          :menu="this.$route.params.follow"
        />
        <follow-list :isMe="loginUser == email" />
      </div>
    </div>
  </div>
</template>

<script>
import FollowHeader from "@/components/follow/FollowHeader";
import FollowList from "@/components/follow/FollowList";

import { mapState } from "vuex";
import "@/assets/css/follow.css";

export default {
  name: "Follow",
  components: {
    FollowHeader,
    // FollowNavbar,
    FollowList,
  },
  data() {
    return {
      token: sessionStorage.getItem("jwt"),
      email: this.$route.params.email,
      name: this.$route.params.userName,
    };
  },
  computed: {
    // 로그인한 사용자 이메일 가져오기
    ...mapState({ loginUser: (state) => state.userStore.LoginUser }),
  },
  methods: {
    // 뒤로 가기
    onGoBack() {
      this.$router.back();
    },

    // 팔로잉 페이지
    onGoFollowing() {
      this.$router.replace({
        name: "Follow",
        params: {
          userName: this.name,
          email: this.email,
          follow: "following",
        },
      });
    },

    // 팔로우 페이지
    onGoFollower() {
      this.$router.replace({
        name: "Follow",
        params: {
          userName: this.name,
          email: this.email,
          follow: "follower",
        },
      });
    },
  },
  watch: {
    $route() {
      this.$router.go();
    },
  },
};
</script>

<style></style>
