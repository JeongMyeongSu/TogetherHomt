<template>
    <div id="exercise-list-top">
      <div id="exercise-title">
        <div>운동 목록</div><div>운동 시연 영상</div>
      </div>
      <div id="exercise-list-menu">
        <span @click="changeExerciseType('전체')">전체</span>
        <span @click="changeExerciseType('상체')"># 상체</span>
        <span @click="changeExerciseType('하체')"># 하체</span>
        <span @click="changeExerciseType('스트레칭')"># 스트레칭</span>
        <span @click="changeExerciseType('근력운동')"># 근력운동</span>
      </div>
    </div>
</template>

<script>
export default {
  name: "ExerciseListMenu",
  methods: {
    changeExerciseType(value) {
      this.$emit("changeExerciseType", value);
    },
  },
};
</script>

<style lang="scss" scoped></style>
