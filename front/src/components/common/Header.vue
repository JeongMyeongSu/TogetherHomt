<template>
  <div id="header">
    <div>
      <div class="logo">
        <img
          src="@/assets/투게더 홈트.png"
          alt="로고"
          style="width: 120px; height: 30px"
        />
      </div>
      <div class="menu">
        <div>
          <span
            class="material-icons"
            v-if="myAlarms.length == 0"
            @click="goOtherPage('Notification')"
          >
            notifications
          </span>
          <span
            class="material-icons alarm"
            v-else
            @click="goOtherPage('Notification')"
          >
            notifications_active
          </span>
        </div>
        <div>
          <span class="material-icons" @click="goOtherPage('CheckWhoExercise')">
            people
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "@/assets/css/header.css";

import { mapState } from "vuex";

export default {
  name: "Header",
  methods: {
    goOtherPage(pageName) {
      this.$router.push({
        name: pageName,
      });
    },
  },
  computed: {
    ...mapState({ myAlarms: (state) => state.myStore.myAlarms }),
  },
};
</script>

<style></style>
