<template>
  <div id="follow-header">
    <div class="cancel">
      <span class="material-icons-outlined" v-on:click="goBack">
        arrow_back_ios
      </span>
    </div>
    <div class="context" v-if="menu == 'following'">
      {{ $route.params.userName }}님의 팔로잉
    </div>
    <div v-else class="context">{{ $route.params.userName }}님의 팔로워</div>
    <div></div>
  </div>
</template>

<script>
export default {
  name: "FollowHeader",
  props: ["menu"],
  methods: {
    goBack() {
      this.$emit("goBack");
    },
  },
};
</script>

<style></style>
