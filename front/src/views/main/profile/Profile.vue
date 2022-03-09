<template>
  <div id="profile">
    <!-- 내 프로필 -->
    <div v-if="loginUser == email">
      <profile-info
        :info="{ userName: myInfo.username, profileUrl: myInfo.imagePath }"
      />

      <profile-data-cnt
        :info="{
          feedCnt: feedsInfo.length,
          followerCnt: myInfo.cntFollower,
          followingCnt: myInfo.cntFollowing,
          userName: myInfo.username,
          email: myInfo.email,
        }"
      />

      <profile-edit-control />

      <profile-introduction :introduction="myInfo.introduce" />

      <profile-feed-navbar />

      <profile-feed-list
        :feedList="feedsInfo"
        :userName="myInfo.username"
        :email="loginUser"
      />
    </div>

    <!-- 다른 유저 프로필 -->
    <div v-else>
      <profile-info
        :info="{
          userName: otherInfo.username,
          profileUrl: otherInfo.imagePath,
        }"
      />

      <profile-data-cnt
        :info="{
          feedCnt: feedsInfo.length,
          followerCnt: otherInfo.cntFollower,
          followingCnt: otherInfo.cntFollowing,
          userName: otherInfo.username,
          email: otherInfo.email,
        }"
      />

      <profile-follow-control :email="otherInfo.email" :isFollow="isFollow" />

      <profile-introduction :introduction="otherInfo.introduce" />

      <profile-feed-navbar />

      <profile-feed-list
        :feedList="feedsInfo"
        :userName="otherInfo.username"
        :email="otherInfo.email"
      />
    </div>
  </div>
</template>

<script>
import ProfileInfo from "@/components/profile/ProfileInfo";
import ProfileDataCnt from "@/components/profile/ProfileDataCnt";
import ProfileEditControl from "@/components/profile/ProfileEditControl";
import ProfileFollowControl from "@/components/profile/ProfileFollowControl";
import ProfileIntroduction from "@/components/profile/ProfileIntroduction";
import ProfileFeedNavbar from "@/components/profile/ProfileFeedNavbar";
import ProfileFeedList from "@/components/profile/ProfileFeedList";

import { mapState } from "vuex";

import "@/assets/css/profile.css";

export default {
  name: "Profile",
  components: {
    ProfileInfo,
    ProfileDataCnt,
    ProfileEditControl,
    ProfileIntroduction,
    ProfileFeedNavbar,
    ProfileFeedList,
    ProfileFollowControl,
  },
  data() {
    return {
      token: sessionStorage.getItem("jwt"),
      email: this.$route.params.email,
      isFollow: -1,
    };
  },
  computed: {
    // 로그인한 사용자 이메일 가져오기
    ...mapState({ loginUser: (state) => state.userStore.LoginUser }),

    // 내 정보
    ...mapState({ myInfo: (state) => state.myStore.myInfo }),

    // 다른 유저 정보
    ...mapState({ otherInfo: (state) => state.otherStore.otherInfo }),
    ...mapState({ otherFollowers: (state) => state.otherStore.otherFollowers }),

    // 피드 정보
    ...mapState({ feedsInfo: (state) => state.feedStore.feedsInfo }),
  },
  methods: {
    // 팔로우 했는지 안했는지 체크
    checkFollow(value) {
      if (value.email == this.loginUser) {
        return true;
      }
      return false;
    },
  },
  watch: {
    $route() {
      this.$router.go();
    },
  },
  async mounted() {
    if (this.loginUser != this.email) {
      // 다른 유저 정보 가져오기
      await this.$store.dispatch("otherStore/setOtherInfo", {
        email: this.email,
        token: this.token,
      });

      await this.$store.dispatch("otherStore/setOtherFollowers", {
        email: this.email,
        token: this.token,
      });

      // 팔로우 체크하기
      if (typeof this.otherFollowers != "string") {
        this.isFollow = this.otherFollowers.findIndex(this.checkFollow);
      }
    } else {
      // 내 정보 가져오기
      await this.$store.dispatch("myStore/setMyInfo", {
        email: this.email,
        token: this.token,
      });
    }

    //피드 리스트 가져오기
    await this.$store.dispatch("feedStore/setFeedsInfo", {
      email: this.email,
      token: this.token,
    });
  },
};
</script>

<style></style>
