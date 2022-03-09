<template>
  <div id="comment-page">
    <comment-header v-on:goBack="onGoBack" />
    <comment-detail :comments="comments"></comment-detail>
    <comment-register v-bind:feedId="feedId"></comment-register>
  </div>
</template>

<script>
import CommentHeader from "@/components/CommentPage/CommentHeader";
import CommentDetail from "@/components/CommentPage/CommentDetail";
import CommentRegister from "@/components/CommentPage/CommentRegister";
import api from "@/api/api.js";
import "@/assets/css/CommentPage.css";

export default {
  name: "CommentPage",
  components: {
    CommentHeader,
    CommentDetail,
    CommentRegister,
  },
  created() {
    const feedId = this.$route.query.feedId;
    console.log(feedId);
    api
      .get(`/feed/${feedId}/comments`, {
        headers: {
          Authorization: sessionStorage.getItem("jwt"),
        },
      })
      .then((res) => {
        this.comments = res.data;
        console.log(this.comments);
      })
      .catch((error) => {
        console.log(error);
      });
  },
  computed: {
    //   // 로그인한 유저 정보
    //   ...mapState(["myInfo"]),
  },
  methods: {
    created() {
      //const feedId = this.myInfo.feeds[this.$route.params.feedId].feed_id;
      const feedId = this.$route.query.feedId;
      const username = this.$route.query.username;
      console.log(feedId);
      console.log(username);

      api
        .get(`/feed/${feedId}/comments`, {
          headers: {
            Authorization: sessionStorage.getItem("jwt"),
          },
        })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    onGoBack() {
      this.$router.back();
    },
    async onRegisterComment(value) {
      const feedId = this.myInfo.feeds[this.$route.params.feedId].feed_id;
      await api
        .post(
          `/feed/${feedId}/comments`,
          { content: value, feed_id: feedId, username: this.myInfo.username },
          {
            headers: {
              Authorization: sessionStorage.getItem("jwt"),
            },
          }
        )
        .then((res) => {
          alert("댓글 등록 완료!");
          console.log(res);
        })
        .catch((err) => {
          alert("댓글 등록 실패!");
          console.log(err);
        });

      this.$router.go(this.$router.currentRoute);
    },
  },
};
</script>

<style></style>
