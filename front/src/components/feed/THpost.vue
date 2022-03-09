<template>
  <div class="th-post">
    <div class="print" v-for="(item, index) in feedsInfo" v-bind:key="index">
      <!-- re:사용자 프로필, 삭제 버튼 -->
      <div class="header-level">
        <div>
          <div id="img">
            <img
              :src="'http://3.38.103.222:8092' + item.profileUrl"
              alt="프로필 사진"
            />
          </div>
          <div id="usrnm" @click="goProfile(item.username, item.email)">
            {{ item.username }}
          </div>
          <!-- 프로필로 이동하기 -->
        </div>
      </div>

      <!-- re: 피드 이미지 -->
      <div class="feed-image">
        <div class="feed-image-container">
          <video
            v-if="isVideo(item.mediaUrl)"
            :src="'http://3.38.103.222:8092' + item.mediaUrl"
            alt=""
            playsinline
            controls
            muted
          />
          <img
            v-else
            :src="'http://3.38.103.222:8092' + item.mediaUrl"
            alt=""
          />
        </div>
      </div>

      <!-- 피드 메뉴바 (좋아요, 댓글, 스크랩) -->
      <div class="feed-detail-menu">
        <div>
          <span
            v-if="item.likeStatus"
            class="material-icons-outlined"
            @click="unlikeFeed(item.feedId)"
          >
            favorite
          </span>
          <span
            v-else
            class="material-icons-outlined"
            @click="likeFeed(item.feedId)"
          >
            favorite_border
          </span>
        </div>
        <div>
          <span
            class="material-icons-outlined"
            @click="goComment(item.username, item.email, item.feedId)"
          >
            comment
          </span>
        </div>
      </div>

      <!-- 피드 내용 (좋아요 개수, 피드 내용, 댓글) -->
      <div class="feed-detail-content">
        <div v-if="item.likeCnt == 0">가장 먼저 좋아요를 눌러보세요.</div>
        <div v-else @click="goLikeList(item.username, item.email, item.feedId)">
          좋아요 {{ item.likeCnt }}개
        </div>
        <div>
          <div>
            {{ item.content }}
          </div>
        </div>
        <div @click="goComment(item.username, item.email, item.feedId)">
          댓글 모두 보기
        </div>
      </div>

      <!-- 피드 게시 날짜-->
      <div class="feed-detail-date">
        {{ item.createdAt[0] }}년 {{ item.createdAt[1] }}월
        {{ item.createdAt[2] }}일
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import api from "@/api/api.js";
api.defaults.headers.common["Authorization"] = sessionStorage.getItem("jwt");

import "@/assets/css/THpost.css";

export default {
  name: "Thpost",
  data() {
    return {
      token: sessionStorage.getItem("jwt"),
    };
  },
  computed: {
    // 로그인한 사용자 이메일 가져오기
    ...mapState({ loginUser: (state) => state.userStore.LoginUser }),

    // 내 정보
    ...mapState({ myInfo: (state) => state.myStore.myInfo }),

    // 피드 정보
    ...mapState({ feedsInfo: (state) => state.feedStore.feedsInfo }),
  },
  methods: {
    // 확장자 체크하기
    isVideo(fileName) {
      let fileLength = fileName.length;
      let lastDot = fileName.lastIndexOf(".");

      const name = fileName.substring(lastDot + 1, fileLength).toLowerCase();

      if (
        name == "png" ||
        name == "jpg" ||
        name == "gif" ||
        name == "jpeg" ||
        name == "jfif"
      )
        return false;
      else return true;
    },

    //프로필 이동
    goProfile(name, email) {
      this.$router.push({
        name: "Profile",
        params: { userName: name, email: email },
      });
    },

    //좋아요
    async likeFeed(feedId) {
      await api
        .post(`/feed/${feedId}/likes`, null, {
          headers: {
            Authorization: this.token,
          },
        })
        .then((res) => {
          console.log(res);
          alert("좋아요 등록 성공!");
        })
        .catch((err) => {
          console.log(err);
          alert("좋아요 등록 실패!");
        });

      this.$router.go();
    },

    // 좋아요 취소
    async unlikeFeed(feedId) {
      await api
        .delete(`/feed/${feedId}/likes`, {
          headers: {
            Authorization: this.token,
          },
        })
        .then((res) => {
          console.log(res);
          alert("좋아요 취소 성공!");
        })
        .catch((err) => {
          console.log(err);
          alert("좋아요 취소 실패!");
        });

      this.$router.go();
    },

    // 피드 댓글 이동
    async goComment(name, email, id) {
      // 상세 피드 가져오기
      await this.$store.dispatch("feedStore/setFeedInfo", {
        feedId: id,
        token: sessionStorage.getItem("jwt"),
      });

      this.$router.push({
        name: "ProfileFeedComment",
        params: { userName: name, email: email, feedId: id },
      });
    },

    //좋아요 리스트 조회
    goLikeList(name, email, id) {
      this.$router.push({
        name: "ProfileFeedLike",
        params: { userName: name, email: email, feedId: id },
      });
    },

    //피드 상세페이지
    goCaptionDetail(name, email, id) {
      this.$router.push({
        name: "ProfileFeedDetail",
        params: { userName: name, email: email, feedId: id },
      });
    },
  },
};
</script>

<style lang="scss" scoped></style>
