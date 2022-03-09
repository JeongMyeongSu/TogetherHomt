<template>
  <div id="exercise-demo-menu">
    <div @click="changeExerciseType('전체')">전체</div>
    <div @click="changeExerciseType('상체')">상체</div>
    <div @click="changeExerciseType('하체')">하체</div>
    <div @click="changeExerciseType('스트레칭')">스트레칭</div>
    <div @click="changeExerciseType('근력운동')">근력운동</div>
  </div>
</template>

<script>
export default {
  name: "ExerciseDemoMenu",
  methods: {
    changeExerciseType(value) {
      this.$emit("changeExerciseType", value);
    },
  },
};
</script>

<style></style>
