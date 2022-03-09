<template>
  <div id="profile-feed-like-main">
    <div class="like" v-for="(value, index) in likeInfo" :key="index">
      <div class="profile" @click="goProfile(value.username, value.email)">
        <img
          :src="'http://3.38.103.222:8092' + value.profileUrl"
          alt="프로필 사진"
        />
      </div>
      <div class="username" @click="goProfile(value.username, value.email)">
        {{ value.username }}
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "ProfileFeedLikeMain",
  computed: {
    ...mapState({ likeInfo: (state) => state.feedStore.likeInfo }),
  },
  methods: {
    // 프로필 페이지 이동하기
    goProfile(name, email) {
      this.$router.push({
        name: "Profile",
        params: { userName: name, email: email },
      });
    },
  },
};
</script>

<style></style>
