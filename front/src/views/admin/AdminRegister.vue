<template>
  <div id="admin-register">
    <span @click="$router.go(-1)" id="back" class="material-icons-outlined"
      >arrow_back_ios</span
    >
    <h1>미퇴실자 명단</h1>
    <div>
      <div id="make-image">
        <canvas id="canvas"></canvas>
        <button @click="attendance()">미퇴실 명단 다운로드</button>
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";
import "@/assets/css/admin/adminRegister.css";
import { mapActions } from "vuex";

export default {
  name: "AdminRegister",
  data() {
    return {
      absent: [],
    };
  },
  methods: {
    ...mapActions(["setMyInfo"]),
    attendance() {
      api({
        method: "get",
        url: "/exercise/attendance/absentees",
        headers: {
          Authorization: sessionStorage.getItem("jwt"),
        },
      }).then((res) => {
        var arr = [];
        for (let student of res.data) {
          if (student.username != "admin") {
            arr.push(student.username);
          }
        }
        this.absent = arr;
        console.log(this.absent);
        this.draw();
      });
    },

    draw() {
      var canvas = document.getElementById("canvas");
      if (canvas.getContext) {
        var ctx = canvas.getContext("2d");
        ctx.font = "20px serif";
        ctx.fillStyle = "rgb(204,229,255)";
        ctx.fillRect(0, 0, canvas.width, canvas.height);
        ctx.fillStyle = "rgb(0,0,0)";
        ctx.fillText(this.absent, 20, 30);
      }
      this.saveImage();
    },

    saveImage() {
      var link = document.createElement("a");
      link.download = "미퇴실명단.png";
      link.href = document.getElementById("canvas").toDataURL("image/png");
      link.click();
    },
  },
};
</script>

<style></style>
