<template>
  <div id="comment-header">
    <div class="cancel" v-on:click="goBack()">
      <span class="material-icons"> arrow_back_ios_new </span>
    </div>
    <div class="context">댓글</div>
    <div></div>
  </div>
</template>

<script>
export default {
  name: "CommentHeader",
  methods: {
    goBack() {
      this.$emit("goBack");
    },
  },
};
</script>

<style>
/* #comment-header {
  background-color: #ffffff;
  display: flex;
  height: 40px;
  text-align: center;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  padding: 0 10px;
}

#comment-header > .cancel {
  height: 24px;
}

#comment-header > .cancel > span {
  font-size: 24px;
  color: rgba(0, 0, 0, 0.87);
}

#comment-header > .context {
  font-weight: bold;
}

#comment-header > .cancel > .material-icons {
  cursor: pointer;
} */
</style>
