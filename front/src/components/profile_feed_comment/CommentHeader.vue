<template>
  <div id="comment-header">
    <div class="cancel" v-on:click="goBack()">
      <span class="material-icons"> arrow_back_ios_new </span>
    </div>
    <div class="context">댓글</div>
    <div></div>
  </div>
</template>

<script>
export default {
  name: "CommentHeader",
  methods: {
    goBack() {
      this.$emit("goBack");
    },
  },
};
</script>

<style></style>
