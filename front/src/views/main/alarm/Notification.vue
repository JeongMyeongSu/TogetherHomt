<template>
  <div id="notification">
    <div>
      <div>
        <notification-header v-on:goBack="onGoBack" />
        <notification-list />
      </div>
    </div>
  </div>
</template>

<script>
import "@/assets/css/notification.css";

import NotificationHeader from "@/components/notification/NotificationHeader";
import NotificationList from "@/components/notification/NotificationList";

export default {
  name: "Notification",
  components: {
    NotificationHeader,
    NotificationList,
  },
  methods: {
    onGoBack() {
      this.$router.back();
    },
  },
};
</script>

<style></style>
