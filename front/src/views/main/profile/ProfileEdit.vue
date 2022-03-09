<template>
  <div id="profile-edit">
    <profile-edit-header
      v-on:goBack="onGoBack"
      v-on:editProfile="onEditProfile"
    />
    <profile-edit-container :isEdit="isEdit" />
  </div>
</template>

<script>
import ProfileEditHeader from "@/components/profileEdit/ProfileEditHeader";
import ProfileEditContainer from "@/components/profileEdit/ProfileEditContainer";

import "@/assets/css/profileEdit.css";

export default {
  name: "ProfileEdit",
  components: {
    ProfileEditHeader,
    ProfileEditContainer,
  },
  data() {
    return { isEdit: false };
  },
  methods: {
    onGoBack() {
      this.$router.back();
    },
    onEditProfile(value) {
      this.isEdit = value;
    },
  },
};
</script>

<style></style>
