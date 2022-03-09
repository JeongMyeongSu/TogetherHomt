<template>
  <div id="exercise-demo">
    <exercise-demo-header v-on:goBack="onGoBack" />
    <exercise-demo-menu v-on:changeExerciseType="onChangeExerciseType" />
    <exercise-demo-list :exerciseType="exerciseType" />
  </div>
</template>

<script>
import "@/assets/css/exerciseDemo.css";

import ExerciseDemoHeader from "@/components/exercise_demo/ExerciseDemoHeader";
import ExerciseDemoMenu from "@/components/exercise_demo/ExerciseDemoMenu";
import ExerciseDemoList from "@/components/exercise_demo/ExerciseDemoList";

export default {
  name: "ExerciseDemo",
  components: {
    ExerciseDemoHeader,
    ExerciseDemoMenu,
    ExerciseDemoList,
  },
  data() {
    return {
      exerciseType: null,
    };
  },
  methods: {
    onGoBack() {
      this.$router.back();
    },
    onChangeExerciseType(type) {
      this.exerciseType = type;
      console.log(this.exerciseType);
    },
  },
};
</script>

<style></style>
