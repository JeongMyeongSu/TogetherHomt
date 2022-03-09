<template>
  <div id="check-who-exercise">
    <div>
      <div>
        <check-who-exercise-header v-on:goBack="onGoBack" />
        <check-who-exercise-list />
      </div>
    </div>
  </div>
</template>

<script>
import "@/assets/css/checkWhoExercise.css";

import CheckWhoExerciseHeader from "@/components/checkWhoExercise/CheckWhoExerciseHeader";
import CheckWhoExerciseList from "@/components/checkWhoExercise/CheckWhoExerciseList";

import { mapState } from "vuex";

export default {
  name: "CheckWhoExercise",
  components: {
    CheckWhoExerciseHeader,
    CheckWhoExerciseList,
  },
  computed: {
    ...mapState({ myAlarms: (state) => state.myStore.myAlarms }),
  },
  methods: {
    onGoBack() {
      this.$router.back();
    },
  },
};
</script>

<style></style>
