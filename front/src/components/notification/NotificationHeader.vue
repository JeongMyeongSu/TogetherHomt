<template>
  <div id="notification-header">
    <div class="cancel" v-on:click="goBack()">
      <span class="material-icons"> arrow_back_ios_new </span>
    </div>
    <div class="context">퇴실 알림 메시지</div>
    <div></div>
  </div>
</template>

<script>
export default {
  name: "NotificationHeader",
  methods: {
    goBack() {
      this.$emit("goBack");
    },
  },
};
</script>

<style></style>
