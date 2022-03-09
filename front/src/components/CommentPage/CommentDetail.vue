<template>
  <div class="comment-detail">
    <!-- 피드 게시글  -->
    <!-- 피드, 유저정보를 물려줘야할 것 같은데 / 불러오기 구현중 -->

    <!-- 피드 댓글 -->
    <div class="repeat" v-for="(item, index) in comments" :key="index">
      <div class="CommentUserInfo">
        <div class="userimage">
          <img :src="item.profileUrl" alt="댓글 작성자 프로필" />
        </div>
        <div class="username">{{ item.username }}</div>
        <div class="comment-content">{{ item.content }}</div>
      </div>
      <!-- <div v-if="comment.email == loginUser">
          <span
            class="material-icons"
            @click="deleteComment(feed.feedId, comment.commentId)"
          >
            delete
          </span>
        </div> 
      <div v-else></div>-->
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";
api.defaults.headers.common["Authorization"] = sessionStorage.getItem("jwt");

export default {
  name: "Commentdetail",
  props: {
    comments: Array,
  },
  components: {},
  methods: {
    //댓글삭제
    // deleteComment(commentid, feedId) {
    //   const commentId = commentid;
    //   console.log("삭제 요청");
    //   console.log(commentId);
    //   console.log(feedId);
    //   api
    //     .delete(`/feed/${feedId}/comments/${commentId}`)
    //     .then((res) => {
    //       alert("댓글 삭제");
    //       console.log(res);
    //     })
    //     .catch((err) => {
    //       alert("댓글 안삭제");
    //       console.log(err);
    //     });
    // },
  },
};
</script>

<style lang="scss" scoped>
// .comment-detail > .repeat > .CommentUserInfo {
//   border: 1px solid black;
//   display: flex;
//   margin: 10px;
// }
// .comment-detail > .repeat > .CommentUserInfo > .userimage {
//   width: 40px;
//   height: 40px;
//   border-radius: 50%;
//   border: 1px solid rgba(0, 0, 0, 0.1);
//   margin-left: 5px;
// }
// .comment-detail > .repeat > .CommentUserInfo > .userimage > img {
//   width: 100%;
//   height: 100%;
//   border-radius: 50%;
// }
// .comment-detail > .repeat > .CommentUserInfo > .username {
//   margin-left: 10px;
//   text-align: center;
// }
// .comment-detail > .repeat > .CommentUserInfo > .comment-content {
//   margin-left: 10px;
//   text-align: center;
// }
</style>
