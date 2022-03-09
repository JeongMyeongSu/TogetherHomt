<template>
  <div id="check-who-exercise-list">
    <div v-if="viewList.length != 0">
      <div class="container" v-for="(user, index) in viewList" :key="index">
        <div class="profile">
          <img
            :src="'http://3.38.103.222:8092' + user.image"
            alt="프로필사진"
          />
        </div>
        <div class="content">
          <div class="name">{{ user.username }}</div>
          <div class="check" v-if="user.done">퇴실 완료</div>
          <div class="not-check" v-else>퇴실 미완료</div>
        </div>
        <div class="send-no" v-if="user.done">
          <span class="material-icons"> send </span>
        </div>
        <div
          class="send-yes"
          v-else
          @click="sendNotification(user.username, user.email)"
        >
          <span class="material-icons"> send </span>
        </div>
      </div>
    </div>
    <div v-else>팔로우한 유저가 없습니다.</div>
  </div>
</template>

<script>
import api from "@/api/api.js";

export default {
  name: "CheckWhoExerciseList",
  data() {
    return {
      isShow: false,
      token: sessionStorage.getItem("jwt"),

      viewList: [],
    };
  },
  methods: {
    // 알림 보내기
    async sendNotification(name, email) {
      await api
        .post(
          `/notification/notifications`,
          {
            receiver: {
              email: email,
            },
          },
          {
            headers: {
              Authorization: this.token,
            },
          }
        )
        .then((res) => {
          console.log(res);
          alert(name + "님께 알림을 보냈습니다.");
        })
        .catch((err) => {
          console.log(err);
          alert("알림 보내기 실패!");
        });
    },
  },
  async mounted() {
    // 내가 팔로우한 사람의 명단 가져오기
    await api
      .get(`/exercise/attendance/followings/absentees`, {
        headers: {
          Authorization: this.token,
        },
      })
      .then((res) => {
        console.log(res);
        this.viewList = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  },
};
</script>

<style></style>
