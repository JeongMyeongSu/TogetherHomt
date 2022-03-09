<template>
  <div id="exercise-list-header">
    <div class="cancel" v-on:click="goBack()">
      <span class="material-icons"> arrow_back_ios_new </span>
    </div>
    <div class="context">@{{ myInfo.username}}</div>
    <div></div>
  </div>
</template>

<script>
import { mapState } from 'vuex';
export default {
  name: "ExerciseListHeader",
  methods: {
    goBack() {
      this.$emit("goBack");
    },
  },
  computed: {
    ...mapState({ myInfo : (state) => state.myStore.myInfo}),
  }
};
</script>

<style lang="scss" scoped></style>
