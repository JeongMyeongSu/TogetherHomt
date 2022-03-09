<template>
  <div id="notification-list">
    <div v-if="viewAlarm.length != 0">
      <div
        class="container"
        @click.stop="deleteNotification(user.notificationId, index)"
        v-for="(user, index) in viewAlarm"
        :key="index"
      >
        <div
          class="profile"
          @click.stop="goProfile(user.sender.username, user.sender.email)"
        >
          <img
            :src="'http://3.38.103.222:8092' + user.sender.imagePath"
            alt="프로필사진"
          />
        </div>
        <div class="content">
          <span @click.stop="goProfile(user.sender.username, user.sender.email)"
            >{{ user.sender.username }}님</span
          >이 퇴실을 재촉하고 있습니다!
        </div>
      </div>
    </div>
    <div v-else>알림이 없습니다.</div>
  </div>
</template>

<script>
import api from "@/api/api.js";

import { mapState } from "vuex";

export default {
  name: "NotificationList",
  data() {
    return {
      token: sessionStorage.getItem("jwt"),
      viewAlarm: [],
    };
  },
  computed: {
    ...mapState({ myAlarms: (state) => state.myStore.myAlarms }),
  },
  methods: {
    // 알림 삭제
    async deleteNotification(id, index) {
      if (confirm("알림을 삭제하시겠습니까?")) {
        await api
          .delete(`/notification/notifications/${id}`, {
            headers: {
              Authorization: this.token,
            },
          })
          .then((res) => {
            console.log(res);

            this.viewAlarm.splice(index, index + 1);
            alert("알림이 삭제되었습니다.");
          })
          .catch((err) => {
            console.log(err);
            alert("알림 삭제 실패!");
          });
      }
    },
    // 프로필 페이지 이동하기
    goProfile(name, email) {
      this.$router.push({
        name: "Profile",
        params: { userName: name, email: email },
      });
    },
  },
  async mounted() {
    // 내 알람 정보 조회하기
    await this.$store.dispatch("myStore/setMyAlarms", {
      token: this.token,
    });

    this.viewAlarm = this.myAlarms;
  },
};
</script>

<style></style>
