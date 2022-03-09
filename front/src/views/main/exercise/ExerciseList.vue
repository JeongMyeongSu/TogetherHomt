<template>
  <div class="exercise-list">
    <exercise-list-header v-on:goBack="onGoBack" />
      <div id="exercise-box">
        <exercise-list-menu v-on:changeExerciseType="onChangeExerciseType" />
        <exercise-list-main :exerciseType="exerciseType" />
        <!-- @sendLocal="onAddExercise" -->
      </div>
      <div id="exercise-box">
        <exercise-list-basket-header v-on:DeleteStorage="onDeleteStorage" />
        <exercise-list-basket :todayExercise="todayExercises" />
      </div>
    </div>
</template>

<script>
import "@/assets/css/exercise/exerciseList.css";
import "@/assets/css/exercise/exerciseBasket.css";
import ExerciseListHeader from "@/components/exercise/ExerciseListHeader";
import ExerciseListMain from "@/components/exercise/ExerciseListMain";
import ExerciseListMenu from "@/components/exercise/ExerciseListMenu";
import ExerciseListBasketHeader from "@/components/exercise/ExerciseListBasketHeader";
import ExerciseListBasket from "@/components/exercise/ExerciseListBasket";

export default {
  name: "ExerciseList",
  components: {
    ExerciseListHeader,
    ExerciseListMenu,
    ExerciseListMain,
    ExerciseListBasketHeader,
    ExerciseListBasket,
  },
  data() {
    return {
      exerciseType: null,
      todayExercises: [],
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
    onDeleteStorage() {
      //조건에 맞는 키 값들만 삭제하기
      // localStorage.clear();
      if (localStorage.length > 0) {
        //if (
        // localStorage.key(i) !== "loglevel:webpack-dev-server" &&
        // localStorage.key(i) !== "vuex"
        //) {
        // localStorage.remove(키값);
        // }
      }
      console.log("운동전체삭제-if문필요?");
    },
    // onAddExercise(todayExercise) {
    //   //밑에서 이벤트로 올리면 받아서 로컬스토리지에 추가 - 다시 아래로 내려준다.
    //   this.todayExercise = todayExercise;
    //   console.log(this.todayExercise); //undefined
    //   localStorage.setItem(this.todayExercise, this.todayExercise);
    //   //push??
    //   if (localStorage.length > 0) {
    //     for (let i = 0; i < localStorage.length; i++) {
    //       if (
    //         localStorage.key(i) !== "loglevel:webpack-dev-server" &&
    //         localStorage.key(i) !== "vuex"
    //       ) {
    //         this.todayExercises += localStorage.getItem(
    //           JSON.stringify(localStorage.key(i))
    //         );
    //         // JSON.parse();

    //         // 로컬스토리지의 아이템을 todayExercise에 넣어준다.
    //         console.log("여기계속찍히나?");
    //       }
    //     }
    //   }
    // },
  },
};
</script>

<style lang="scss" scoped></style>
