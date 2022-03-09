<template>
  <div id="register-header">
    <div class="cancel" v-on:click="goBack()">취소</div>
    <div class="context">새 게시물</div>
    <div class="done" v-on:click="registerFeed()">공유</div>
  </div>
</template>

<script>
export default {
  name: "RegisterHeader",
  methods: {
    goBack() {
      this.$emit("goBack");
    },
    registerFeed() {
      this.$emit("registerFeed", true);
    },
  },
};
</script>

<style></style>
