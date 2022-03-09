<template>
  <div id="exercise-list-main">
    <div v-for="(exercise, index) in viewExercise" :key="index">
      <div id="exercise-circle" @click="sendLocal(exercise)">
        <div>{{ exercise }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";

export default {
  name: "ExerciseListMain",
  props: ["exerciseType"],
  data() {
    return {
      viewExercise: [
        "목 스트레칭",
        "허리 스트레칭",
        "팔 스트레칭",
        "크로스 사이드 크런치",
        "스쿼트",
        "런지",
        "푸쉬업",
        "레터럴 레이즈",
      ],
      exercises: {
        전체: [
          "목 스트레칭",
          "허리 스트레칭",
          "팔 스트레칭",
          "크로스 사이드 크런치",
          "스쿼트",
          "런지",
          "푸쉬업",
          "레터럴 레이즈",
        ],
        상체: [
          "목 스트레칭",
          "허리 스트레칭",
          "팔 스트레칭",
          "크로스 사이드 크런치",
          "푸쉬업",
          "레터럴 레이즈",
        ],
        하체: ["스쿼트", "런지"],
        스트레칭: ["목 스트레칭", "허리 스트레칭", "팔 스트레칭"],
        근력운동: [
          "크로스 사이드 크런치",
          "스쿼트",
          "런지",
          "푸쉬업",
          "레터럴 레이즈",
        ],
      },
    };
  },
  watch: {
    async exerciseType(value) {
      this.viewExercise = this.exercises[value];
    },
  },
  methods: {
    sendLocal(todayExer) {
      console.log(todayExer);
      this.todayExercise = todayExer;
      console.log(this.todayExercise);
      api
        .post(
          "/exercise/today-exercises",
          {
            exercise: this.todayExercise,
          },
          {
            headers: {
              Authorization: sessionStorage.getItem("jwt"),
            },
          }
        )
        .then((res) => {
          console.log(res);
          // alert("장바구니담기성공");
        })
        .catch((err) => {
          console.log(err);
          alert("장바구니담기실패");
        });

      //  localStorage.setItem(this.todayExercise, this.todayExercise);
      //   if (localStorage.length > 0) {
      //     // alert("장바구니에 운동을 담았습니다.");
      //     this.$router.go();
      //   }
      this.$router.go();
    },
  },
};
</script>

<style lang="scss" scoped></style>
