<template>
  <div id="profile-feed-like-header">
    <div class="cancel" @click="goBack()">
      <span class="material-icons"> arrow_back_ios_new </span>
    </div>
    <div class="context">좋아요</div>
    <div></div>
  </div>
</template>

<script>
export default {
  name: "ProfileFeedLikeHeader",
  methods: {
    goBack() {
      this.$emit("goBack");
    },
  },
};
</script>

<style></style>
