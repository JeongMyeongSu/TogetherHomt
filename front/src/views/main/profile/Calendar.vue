<template>
  <div id="calendar">
    <calendar-header v-on:goBack="onGoBack" />
    <calendar-main />
  </div>
</template>

<script>
import CalendarHeader from "@/components/calendar/CalendarHeader";
import CalendarMain from "@/components/calendar/CalendarMain";

import "@/assets/css/calendar.css";

export default {
  name: "Calendar",
  components: {
    CalendarHeader,
    CalendarMain,
  },

  methods: {
    // 뒤로 가기
    onGoBack() {
      this.$router.back();
    },
  },
};
</script>

<style></style>
