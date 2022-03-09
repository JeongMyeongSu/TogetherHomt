<template>
  <div id="profile-data-cnt">
    <div class="data">
      <a>
        <div class="cnt">{{ info.feedCnt }}</div>
        <div class="name">게시글</div>
      </a>
    </div>
    <div class="data">
      <router-link
        :to="{
          name: 'Follow',
          params: {
            userName: info.userName,
            email: info.email,
            follow: 'following',
          },
        }"
      >
        <div class="cnt">{{ info.followingCnt }}</div>
        <div class="name">팔로잉</div>
      </router-link>
    </div>
    <div class="data">
      <router-link
        :to="{
          name: 'Follow',
          params: {
            userName: info.userName,
            email: info.email,
            follow: 'follower',
          },
        }"
      >
        <div class="cnt">{{ info.followerCnt }}</div>
        <div class="name">팔로워</div>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "ProfileDataCnt",
  props: ["info"],
};
</script>

<style></style>
