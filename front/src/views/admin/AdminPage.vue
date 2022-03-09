<template>
  <div class="admin" id="profile">
    <profile-info
      :info="{ userName: myInfo.username, profileUrl: myInfo.imagePath }"
    />
    <div id="admin-edit">
      <!-- <button @click="$router.push({ name: 'Group' })">그룹 나누기</button> -->
    </div>
    <profile-edit-control />
    <canvas id="canvas"></canvas>
    <button id="download-absent-file" @click="attendance()">
      <p>미퇴실 명단 불러오기</p>
      <div class="material-icons-outlined">file_download</div>
    </button>
    <profile-feed-list
      :feedList="userFeeds"
      :userName="myInfo.username"
      :email="myInfo.email"
    />
  </div>
</template>

<script>
import "@/assets/css/admin/admin.css";
import ProfileInfo from "@/components/profile/ProfileInfo";
import ProfileFeedList from "@/components/profile/ProfileFeedList";
import ProfileEditControl from "@/components/profile/ProfileEditControl";
import api from "@/api/api.js";
import { mapState, mapActions } from "vuex";

export default {
  name: "AdminPage",
  components: {
    ProfileInfo,
    ProfileFeedList,
    ProfileEditControl,
  },
  data() {
    return {
      token: sessionStorage.getItem("jwt"),
      userFeeds: null,
      absent: [],
    };
  },
  computed: {
    // 로그인한 유저
    ...mapState({ loginUser: (state) => state.userStore.LoginUser }),
    // 내 정보
    ...mapState({ myInfo: (state) => state.myStore.myInfo }),
  },

  methods: {
    // 내 정보 조회, 내 피드 조회
    ...mapActions(["setMyInfo"]),

    adminFeeds() {
      api({
        method: "get",
        url: `/feed/feeds/profiles/${this.myInfo.email}/temp`,
        headers: {
          Authorization: sessionStorage.getItem("jwt"),
        },
      })
        .then((res) => {
          console.log("admin 게시글" + res.data);
          this.userFeeds = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },

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

  mounted() {
    this.setMyInfo({ email: this.loginUser, token: this.token });
    this.adminFeeds();
  },
};
</script>
