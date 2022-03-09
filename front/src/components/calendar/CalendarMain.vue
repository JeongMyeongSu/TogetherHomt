<template>
  <div id="calendar-main">
    <div class="calendar">
      <v-calendar :attributes="attrs" @dayclick="onDayClick" is-expanded />
    </div>
    <div class="record" v-for="(viewRecord, index) in viewRecords" :key="index">
      <div class="date" v-if="index == 0">
        {{ viewRecord.date }}
      </div>
      <div class="context">
        <div>
          {{ viewRecord.exercise }}
        </div>
        <!-- <div v-if="viewRecord.count">&nbsp;{{ viewRecord.count }}회</div>
        <div v-else>&nbsp;10회</div> -->
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";
import { mapState } from "vuex";

export default {
  name: "CalendarMain",
  data() {
    return {
      token: sessionStorage.getItem("jwt"),
      records: [],

      viewRecords: [],
      attrs: [],
    };
  },
  methods: {
    onDayClick(day) {
      this.viewRecords = this.records.filter((record) => record.id == day.id);
    },
  },
  computed: {
    // 로그인한 사용자 이메일 가져오기
    ...mapState({ loginUser: (state) => state.userStore.LoginUser }),
  },
  async created() {
    await api
      .get(`/exercise/records/${this.loginUser}`, {
        headers: {
          Authorization: this.token,
        },
      })
      .then((res) => {
        const resData = res.data;

        for (const data of resData) {
          let date = data.date;
          let year = date.slice(0, 4);
          let month = date.slice(4, 6);
          let day = date.slice(6, 8);

          let temp = year + "-" + month + "-" + day;
          // 퇴실 체크하기
          this.attrs.push({
            highlight: {
              color: "blue",
              fillMode: "light",
            },
            dates: [new Date(temp)],
          });

          // 운동 기록 추가하기
          this.records.push({
            id: temp,
            date: `${year}년 ${month}월 ${day}일`,
            exercise: data.exercise,
            count: data.countCheck,
          });
        }
      })
      .then((err) => {
        console.log(err);
      });
  },
};
</script>

<style></style>
