<template>
  <div id="profile-feed-like">
    <div>
      <div>
        <profile-feed-like-header v-on:goBack="onGoBack" />
        <profile-feed-like-main />
      </div>
    </div>
  </div>
</template>

<script>
import ProfileFeedLikeHeader from "@/components/profile_feed_like/ProfileFeedLikeHeader";
import ProfileFeedLikeMain from "@/components/profile_feed_like/ProfileFeedLikeMain";

import "@/assets/css/profileFeedLike.css";

export default {
  name: "ProfileFeedLike",
  data() {
    return {
      token: sessionStorage.getItem("jwt"),
    };
  },
  components: {
    ProfileFeedLikeHeader,
    ProfileFeedLikeMain,
  },
  methods: {
    onGoBack() {
      this.$router.back();
    },
  },
  async mounted() {
    // 피드 좋아요 리스트 가져오기
    await this.$store.dispatch("feedStore/setLikeInfo", {
      feedId: this.$route.params.feedId,
      token: this.token,
    });
  },
};
</script>

<style></style>
