<template>
  <div id="profile-feed-comment">
    <comment-header v-on:goBack="onGoBack" />

    <comment-main
      v-if="loginUser == $route.params.email"
      :info="myInfo"
      :commentInfo="commentInfo"
      :feedInfo="feedInfo"
      :loginUser="loginUser"
    />
    <comment-main
      v-else
      :info="otherInfo"
      :commentInfo="commentInfo"
      :feedInfo="feedInfo"
      :loginUser="loginUser"
    />

    <comment-register :myInfo="myInfo" />
  </div>
</template>

<script>
import CommentHeader from "@/components/profile_feed_comment/CommentHeader";
import CommentRegister from "@/components/profile_feed_comment/CommentRegister";
import CommentMain from "@/components/profile_feed_comment/CommentMain";

import { mapState } from "vuex";

import "@/assets/css/profileFeedComment.css";

export default {
  name: "ProfileFeedComment",
  data() {
    return {
      token: sessionStorage.getItem("jwt"),
      feedId: this.$route.params.feedId,
    };
  },
  components: {
    CommentHeader,
    CommentRegister,
    CommentMain,
  },
  methods: {
    onGoBack() {
      this.$router.back();
    },
  },
  computed: {
    ...mapState({ loginUser: (state) => state.userStore.LoginUser }),

    ...mapState({ myInfo: (state) => state.myStore.myInfo }),
    ...mapState({ otherInfo: (state) => state.otherStore.otherInfo }),

    ...mapState({ commentInfo: (state) => state.feedStore.commentInfo }),
    ...mapState({ feedInfo: (state) => state.feedStore.feedInfo }),
  },
  async mounted() {
    console.log("hello");
    // 피드 댓글 리스트 가져오기
    await this.$store.dispatch("feedStore/setCommentInfo", {
      feedId: this.feedId,
      token: this.token,
    });
  },
};
</script>

<style></style>
