<template>
  <div id="calendar-header">
    <div class="cancel" v-on:click="goBack()">
      <span class="material-icons"> arrow_back_ios_new </span>
    </div>
    <div class="context">캘린더</div>
    <div></div>
  </div>
</template>

<script>
export default {
  name: "CalendarHeader",
  methods: {
    goBack() {
      this.$emit("goBack");
    },
  },
};
</script>

<style></style>
