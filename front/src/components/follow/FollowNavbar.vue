<template>
  <div id="follow-navbar">
    <ul v-if="isMe">
      <li class="following" @click="goFollowing">
        {{ myInfo.cntFollowing }} 팔로잉
      </li>
      <li class="follower" @click="goFollower">
        {{ myInfo.cntFollower }} 팔로워
      </li>
    </ul>
    <ul v-else>
      <li class="following" @click="goFollowing">
        {{ otherInfo.cntFollowing }} 팔로잉
      </li>
      <li class="follower" @click="goFollower">
        {{ otherInfo.cntFollower }} 팔로워
      </li>
    </ul>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "FollowNavbar",
  props: ["isMe"],
  methods: {
    goFollowing() {
      this.$emit("goFollowing");
    },
    goFollower() {
      this.$emit("goFollower");
    },
  },
  computed: {
    // 나의 정보
    ...mapState({ myInfo: (state) => state.myStore.myInfo }),

    // 다른 사람의 정보
    ...mapState({ otherInfo: (state) => state.otherStore.otherInfo }),
  },
  async mounted() {
    // 스타일 적용
    if (this.$route.params.follow == "following") {
      document.querySelector(
        `#follow-navbar > ul > .following`
      ).style.borderBottom = "1px solid rgba(0, 0, 0, 0.87)";
      document.querySelector(`#follow-navbar > ul > .following`).style.color =
        "rgba(0, 0, 0, 0.87)";
      document.querySelector(
        `#follow-navbar > ul > .following`
      ).style.fontWeight = "bold";

      document.querySelector(
        `#follow-navbar > ul > .follower`
      ).style.borderBottom = "1px solid rgba(0, 0, 0, 0.1)";
      document.querySelector(`#follow-navbar > ul > .follower`).style.color =
        "rgba(0, 0, 0, 0.38)";
      document.querySelector(
        `#follow-navbar > ul > .follower`
      ).style.fontWeight = "normal";
    } else {
      document.querySelector(
        `#follow-navbar > ul > .follower`
      ).style.borderBottom = "1px solid rgba(0, 0, 0, 0.87)";
      document.querySelector(`#follow-navbar > ul > .follower`).style.color =
        "rgba(0, 0, 0, 0.87)";
      document.querySelector(
        `#follow-navbar > ul > .follower`
      ).style.fontWeight = "bold";

      document.querySelector(
        `#follow-navbar > ul > .following`
      ).style.borderBottom = "1px solid rgba(0, 0, 0, 0.1)";
      document.querySelector(`#follow-navbar > ul > .following`).style.color =
        "rgba(0, 0, 0, 0.38)";
      document.querySelector(
        `#follow-navbar > ul > .following`
      ).style.fontWeight = "normal";
    }
  },
};
</script>

<style></style>
