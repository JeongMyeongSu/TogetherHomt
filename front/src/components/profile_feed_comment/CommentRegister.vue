<template>
  <div id="comment-register">
    <div>
      <div>
        <div>
          <img
            :src="'http://3.38.103.222:8092' + myInfo.imagePath"
            alt="사용자프로필"
          />
        </div>
        <div>
          <input
            type="text"
            placeholder="댓글 달기..."
            v-model="comment"
            @keyup.enter="registerComment()"
          />
          <span class="material-icons" @click="registerComment()">
            play_circle_filled
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";

export default {
  name: "CommentRegister",
  data() {
    return {
      token: sessionStorage.getItem("jwt"),
      comment: "",
    };
  },
  props: ["myInfo"],
  methods: {
    async registerComment() {
      await api
        .post(
          `/feed/${this.$route.params.feedId}/comments`,
          { content: this.comment },
          {
            headers: {
              Authorization: this.token,
            },
          }
        )
        .then((res) => {
          alert("댓글 등록 성공!");
          console.log(res);
        })
        .catch((err) => {
          alert("댓글 등록 실패!");
          console.log(err);
        });
      await this.$router.go();
    },
  },
};
</script>

<style></style>
