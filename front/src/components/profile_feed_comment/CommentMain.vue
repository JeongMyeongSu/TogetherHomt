<template>
  <div id="comment-main">
    <!-- 피드 게시글 -->
    <div class="feed-content">
      <div>
        <div>
          <div>
            <img
              :src="'http://3.38.103.222:8092' + feedInfo.profileUrl"
              alt="피드작성자프로필"
            />
          </div>
          <div>{{ feedInfo.username }}</div>
        </div>
        <div>{{ feedInfo.content }}</div>
        <div class="tag">
          <div v-for="(tag, index) in feedInfo.tags" :key="index">
            #{{ tag }}
          </div>
        </div>
        <div>
          {{ feedInfo.createdAt[0] }}년 {{ feedInfo.createdAt[1] }}월
          {{ feedInfo.createdAt[2] }}일
        </div>
      </div>
    </div>

    <!-- 피드 댓글들 -->
    <div class="feed-comment">
      <div v-for="(comment, index) in commentInfo" :key="index">
        <div>
          <div>
            <img
              :src="'http://3.38.103.222:8092' + comment.profileUrl"
              alt="댓글 작성자 프로필"
            />
          </div>
          <div>{{ comment.username }}</div>
          <div>{{ comment.content }}</div>
        </div>
        <div v-if="comment.email == loginUser">
          <span
            class="material-icons"
            @click="deleteComment(feedInfo.feedId, comment.commentId)"
          >
            delete
          </span>
        </div>
        <div v-else></div>
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";

export default {
  name: "CommentMain",
  data() {
    return {
      token: sessionStorage.getItem("jwt"),
    };
  },
  props: ["info", "commentInfo", "feedInfo", "loginUser"],
  methods: {
    // 댓글 삭제
    async deleteComment(feedId, commentId) {
      if (confirm("정말 삭제하시겠습니까?")) {
        await api
          .delete(`/feed/${feedId}/comments/${commentId}`, {
            headers: {
              Authorization: this.token,
            },
          })
          .then((res) => {
            alert("삭제 성공!");
            console.log(res);
          })
          .catch((err) => {
            alert("삭제 실패!");
            console.log(err);
          });

        this.$router.go();
      }
    },
  },
};
</script>

<style></style>
