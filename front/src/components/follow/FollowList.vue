<template>
  <div id="follow-list">
    <!-- 나의 팔로우 정보-->
    <div v-if="isMe">
      <!-- 팔로잉 정보 -->
      <div v-if="viewFollow == 'following'">
        <div class="default" v-if="myFollows.followings.length == 0">
          <span class="material-icons"> person_add </span>
          <h4>팔로잉</h4>
          <p>
            회원님이 다른 사람을 팔로우하면 <br />여기에 회원님이 팔로우한
            사람이 표시됩니다.
          </p>
        </div>

        <div class="list" v-else>
          <div
            class="follow"
            v-for="(value, index) in myFollows.followings"
            :key="index"
          >
            <div
              class="profile"
              @click="goProfile(value.username, value.email)"
            >
              <img
                :src="'http://3.38.103.222:8092' + value.imagePath"
                alt="프로필 사진"
              />
            </div>
            <div
              class="username"
              @click="goProfile(value.username, value.email)"
            >
              {{ value.username }}
            </div>
            <div class="btn">
              <!-- 모두 다 팔로잉 표시 -->
              <button class="following-btn" @click="onUnfollow(value.email)">
                팔로잉
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- 팔로워 정보 -->
      <div v-else>
        <div class="default" v-if="myFollows.followers.length == 0">
          <span class="material-icons"> person_add </span>
          <h4>팔로워</h4>
          <p>
            회원님이 다른 사람을 팔로우하면 <br />여기에 회원님이 팔로우한
            사람이 표시됩니다.
          </p>
        </div>

        <div class="list" v-else>
          <div
            class="follow"
            v-for="(value, index) in myFollows.followers"
            :key="index"
          >
            <div
              class="profile"
              @click="goProfile(value.username, value.email)"
            >
              <img
                :src="'http://3.38.103.222:8092' + value.imagePath"
                alt="프로필 사진"
              />
            </div>
            <div
              class="username"
              @click="goProfile(value.username, value.email)"
            >
              {{ value.username }}
            </div>
            <div class="btn">
              <!-- 팔로우 하지 않은 사람 (팔로우 표시)-->
              <button
                class="following-btn"
                v-if="myFollowingList.includes(value.email)"
                @click="onUnfollow(value.email)"
              >
                팔로잉
              </button>
              <button class="follow-btn" v-else @click="onFollow(value.email)">
                팔로우
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 다른 유저의 팔로우 정보-->
    <div v-else>
      <!-- 팔로잉 정보 -->
      <div v-if="viewFollow == 'following'">
        <div class="default" v-if="otherFollows.followings.length == 0">
          <span class="material-icons"> person_add </span>
          <h4>팔로잉</h4>
          <p>
            회원님이 다른 사람을 팔로우하면 <br />여기에 회원님이 팔로우한
            사람이 표시됩니다.
          </p>
        </div>

        <div class="list" v-else>
          <div
            class="follow"
            v-for="(value, index) in otherFollows.followings"
            :key="index"
          >
            <div
              class="profile"
              @click="goProfile(value.username, value.email)"
            >
              <img
                :src="'http://3.38.103.222:8092' + value.imagePath"
                alt="프로필 사진"
              />
            </div>
            <div
              class="username"
              @click="goProfile(value.username, value.email)"
            >
              {{ value.username }}
            </div>
            <div class="btn">
              <!-- 팔로우 하지 않은 사람 (팔로우 표시)-->
              <span v-if="value.email == loginUser"></span>
              <button
                class="following-btn"
                v-else-if="myFollowingList.includes(value.email)"
                @click="onUnfollow(value.email)"
              >
                팔로잉
              </button>
              <button class="follow-btn" v-else @click="onFollow(value.email)">
                팔로우
              </button>
            </div>
          </div>
        </div>
      </div>
      <!-- 팔로워 정보 -->
      <div v-else>
        <div class="default" v-if="otherFollows.followers.length == 0">
          <span class="material-icons"> person_add </span>
          <h4>팔로워</h4>
          <p>
            회원님이 다른 사람을 팔로우하면 <br />여기에 회원님이 팔로우한
            사람이 표시됩니다.
          </p>
        </div>

        <div class="list" v-else>
          <div
            class="follow"
            v-for="(value, index) in otherFollows.followers"
            :key="index"
          >
            <div
              class="profile"
              @click="goProfile(value.username, value.email)"
            >
              <img
                :src="'http://3.38.103.222:8092' + value.imagePath"
                alt="프로필 사진"
              />
            </div>
            <div
              class="username"
              @click="goProfile(value.username, value.email)"
            >
              {{ value.username }}
            </div>
            <div class="btn">
              <!-- 팔로우 하지 않은 사람 (팔로우 표시)-->
              <span v-if="value.email == loginUser"></span>
              <button
                class="following-btn"
                v-else-if="myFollowingList.includes(value.email)"
                @click="onUnfollow(value.email)"
              >
                팔로잉
              </button>
              <button class="follow-btn" v-else @click="onFollow(value.email)">
                팔로우
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";

import { mapState } from "vuex";

export default {
  name: "FollowList",
  data() {
    return {
      token: sessionStorage.getItem("jwt"),
      email: this.$route.params.email,
      viewFollow: this.$route.params.follow,
      myFollowingList: [], // 내가 팔로우한 유저의 이메일
    };
  },
  props: ["isMe"],
  computed: {
    // 로그인한 사용자 이메일 가져오기
    ...mapState({ loginUser: (state) => state.userStore.LoginUser }),

    // 나의 팔로우 정보
    ...mapState({ myFollows: (state) => state.myStore.myFollows }),

    // 다른 사람의 팔로우 정보
    ...mapState({ otherFollows: (state) => state.otherStore.otherFollows }),
  },
  methods: {
    // 프로필 페이지 이동하기
    goProfile(name, email) {
      this.$router.push({
        name: "Profile",
        params: { userName: name, email: email },
      });
    },

    // 팔로우 하기
    async onFollow(email) {
      await api
        .post(`/communication/follows/${email}`, null, {
          headers: {
            Authorization: this.token,
          },
        })
        .then((res) => {
          console.log(res);
          alert("팔로우 성공!");
          this.$router.go();
        })
        .catch((err) => {
          console.log(err);
          alert("팔로우 실패!");
        });
    },

    // 언팔로우 하기
    async onUnfollow(email) {
      await api
        .delete(`/communication/follows/${email}`, {
          headers: {
            Authorization: this.token,
          },
        })
        .then((res) => {
          console.log(res);
          alert("언팔로우 성공!");
          this.$router.go();
        })
        .catch((err) => {
          console.log(err);
          alert("언팔로우 실패!");
        });
    },
  },
  async mounted() {
    // 나의 팔로우 정보 조회하기
    await this.$store.dispatch("myStore/setMyFollows", {
      email: this.loginUser,
      token: this.token,
    });
    console.log("Follow에서 나의 팔로우 정보 get요청함!");

    // 다른 유저의 팔로우 정보 조회하기
    if (!this.isMe) {
      await this.$store.dispatch("otherStore/setOtherFollows", {
        email: this.email,
        token: this.token,
      });
      console.log("Follow에서 유저 팔로워 정보 get요청함!");
    }

    // 나의 팔로잉 정보 조회하기
    this.myFollowingList = this.myFollows.followings.map((x) => x.email); // 객체 배열 중 email만 뽑아서 배열 생성하기
    console.log(this.myFollowingList);
  },
};
</script>

<style></style>
