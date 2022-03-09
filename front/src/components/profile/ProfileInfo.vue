<template>
  <div id="profile-info">
    <div class="profile">
      <img :src="pictureUrl()" alt="유저 프로필 사진" />
    </div>
    <div class="name">@{{ info.userName }}</div>
  </div>
</template>

<script>
export default {
  name: "ProfileInfo",
  props: ["info"],
  methods: {
    pictureUrl() {
      console.log(this.info.profileUrl);
      return "http://3.38.103.222:8092" + this.info.profileUrl;
    },
  },
};
</script>

<style></style>
