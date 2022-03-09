<template>
  <div id="exercise-demo-header">
    <div class="cancel" v-on:click="goBack()">
      <span class="material-icons"> arrow_back_ios_new </span>
    </div>
    <div class="context">운동 시연</div>
    <div></div>
  </div>
</template>

<script>
export default {
  name: "ExerciseDemoHeader",
  methods: {
    goBack() {
      this.$emit("goBack");
    },
  },
};
</script>

<style></style>
