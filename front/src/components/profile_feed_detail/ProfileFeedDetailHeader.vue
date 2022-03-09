<template>
  <div id="profile-feed-detail-header">
    <div class="cancel" @click="goBack()">
      <span class="material-icons"> arrow_back_ios_new </span>
    </div>
    <div class="context">{{ $route.params.userName }}님의 게시글</div>
    <div></div>
  </div>
</template>

<script>
export default {
  name: "ProfileFeedDetailHeader",
  methods: {
    goBack() {
      this.$emit("goBack");
    },
  },
};
</script>

<style></style>
