<template>
  <div id="profile-feed-detail">
    <profile-feed-detail-header v-on:goBack="onGoBack" />
    <profile-feed-detail-main
      v-if="loginUser == email"
      v-on:goBack="onGoBack"
      :isDelete="true"
      :info="myInfo"
      :feedInfo="feedInfo"
    />
    <profile-feed-detail-main
      v-else
      v-on:goBack="onGoBack"
      :isDelete="false"
      :info="otherInfo"
      :feedInfo="feedInfo"
    />
  </div>
</template>

<script>
import ProfileFeedDetailHeader from "@/components/profile_feed_detail/ProfileFeedDetailHeader";
import ProfileFeedDetailMain from "@/components/profile_feed_detail/ProfileFeedDetailMain";

import { mapState } from "vuex";
import "@/assets/css/profileFeedDetail.css";

export default {
  name: "ProfileFeedDetail",
  components: {
    ProfileFeedDetailHeader,
    ProfileFeedDetailMain,
  },
  data() {
    return {
      token: sessionStorage.getItem("jwt"),
      email: this.$route.params.email,
      feedId: this.$route.params.feedId,
    };
  },
  methods: {
    onGoBack() {
      this.$router.back();
    },
  },
  computed: {
    // 로그인한 사용자 이메일 가져오기
    ...mapState({ loginUser: (state) => state.userStore.LoginUser }),

    ...mapState({ myInfo: (state) => state.myStore.myInfo }),
    ...mapState({ otherInfo: (state) => state.otherStore.otherInfo }),

    ...mapState({ feedInfo: (state) => state.feedStore.feedInfo }),
  },
  async mounted() {
    // 상세 피드 가져오기
    await this.$store.dispatch("feedStore/setFeedInfo", {
      feedId: this.feedId,
      token: this.token,
    });
  },
};
</script>

<style></style>
