<template>
  <div id="exercise-list-basket-header">
    <div id="basket-title">
      <p>오늘의 운동</p>
      <p>{{ timestamp }}</p></div>
    <div>
      <!-- <button v-on:click="DeleteStorage()">운동 전체삭제</button> -->
    <button v-on:click="QuitExercise()">운동 종료</button>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  name: "Exerciselistbasketheader",
  data() {
    return {
      timestamp: "",
    };
  },
  methods: {
    DeleteStorage() {
      this.$emit("DeleteStorage");
    },
    QuitExercise() {
      this.$router.push({
        name: "Profile",
        params: { username: this.myInfo.username, email: this.myInfo.email },
      });
    },
  },
  created() {
    const now = new Date();
    const month = now.getMonth() + 1;
    const date = now.getDate();
    const weekList = new Array(
      "일요일",
      "월요일",
      "화요일",
      "수요일",
      "목요일",
      "금요일",
      "토요일"
    );
    const week = weekList[now.getDay()];
    this.timestamp = `${month} 월 ${date} 일 ${week}`;
  },

  computed: {
    ...mapState({ myInfo: (state) => state.myStore.myInfo }),
  }
};
</script>

<style lang="scss" scoped></style>
