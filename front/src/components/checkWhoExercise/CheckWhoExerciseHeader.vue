<template>
  <div id="check-who-exercise-header">
    <div class="cancel" v-on:click="goBack()">
      <span class="material-icons"> arrow_back_ios_new </span>
    </div>
    <div class="context">퇴실 명단</div>
    <div></div>
  </div>
</template>

<script>
export default {
  name: "CheckWhoExerciseHeader",
  methods: {
    goBack() {
      this.$emit("goBack");
    },
  },
};
</script>

<style></style>
