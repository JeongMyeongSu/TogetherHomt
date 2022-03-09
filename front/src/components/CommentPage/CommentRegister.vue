<template>
  <div class="comment-register">
    <div class="Userprofile">
      <img :src="comment.profileUrl" alt="사용자프로필" />
    </div>
    <div class="registerInput">
      <input
        type="text"
        placeholder="댓글 달기..."
        v-model="comment"
        @keyup.enter="createComment(feedId)"
      />
    </div>
    <div class="registerBtn">
      <i class="fa-regular fa-paper-plane" @click="createComment(feedId)"></i>
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";
api.defaults.headers.common["Authorization"] = sessionStorage.getItem("jwt");

export default {
  name: "Commentregister",
  data() {
    return {
      token: sessionStorage.getItem("jwt"),
      comment: "",
    };
  },
  props: {
    comments: [],
    feedId: Object,
  },

  methods: {
    createComment(id) {
      const feedId = id;
      api
        .post(`/feed/${feedId}/comments`, {
          content: this.comment,
        })
        .then((res) => {
          alert("댓글등록완료");
          console.log(res);
        })
        .catch((err) => {
          alert("댓글등록실패");
          console.log(err);
        });
    },
  },
};
</script>

<style lang="scss" scoped>
// .comment-register {
//   display: flex;
//   margin: 10px;
// }
// .comment-register > .Userprofile {
//   width: 40px;
//   height: 40px;
//   border-radius: 50%;
//   border: 1px solid rgba(0, 0, 0, 0.1);
//   margin-left: 5px;
// }
// .comment-register > .Userprofile > img {
//   width: 100%;
//   height: 100%;
//   border-radius: 50%;
// }
// .comment-register > .registerInput {
//   margin-left: 10px;
// }
// .comment-register > .registerBtn {
//   margin-left: 10px;
// }
</style>
