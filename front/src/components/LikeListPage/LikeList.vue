<template>
  <div id="like-list">
    <div class="repeat" v-for="(item, index) in likes" v-bind:key="index">
      <div class="LikeUserInfo">
        <div class="userimage">
          <img :src="item.profileUrl" alt="좋아요 누른 사람 프로필" />
        </div>
        <div class="username">{{ item.username }}</div>
        <!-- 사진과 아이디를 누르면 프로필로 이동할 수 잇어야함 -->
        <div class="userintro">{{ item.introduce }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";
api.defaults.headers.common["Authorization"] = sessionStorage.getItem("jwt");

export default {
  name: "LikeList",
};
</script>

<style lang="scss" scoped>
#like-list > .repeat > .LikeUserInfo {
  border: 1px solid black;
  display: flex;
  margin: 10px;
}
#like-list > .repeat > .LikeUserInfo > .userimage {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: 1px solid rgba(0, 0, 0, 0.1);
  margin-left: 5px;
}
#like-list > .repeat > .LikeUserInfo > .userimage > img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
}
#like-list > .repeat > .LikeUserInfo > .username {
  margin-left: 10px;
  text-align: center;
}
#like-list > .repeat > .LikeUserInfo > .userintro {
  margin-left: 10px;
  text-align: center;
}
</style>
