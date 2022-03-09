<template>
  <div id="profile-edit-header">
    <div class="cancel" v-on:click="goBack">취소</div>
    <div class="context">프로필 편집</div>
    <div class="done" v-on:click="editProfile">완료</div>
  </div>
</template>

<script>
export default {
  name: "ProfileEditHeader",
  methods: {
    goBack() {
      this.$emit("goBack");
    },
    editProfile() {
      this.$emit("editProfile", true);
    },
  },
};
</script>

<style></style>
