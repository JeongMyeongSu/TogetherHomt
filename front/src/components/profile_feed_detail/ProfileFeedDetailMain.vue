<template>
  <div id="profile-feed-detail-main">
    <!-- 피드 출력 -->
    <div>
      <div>
        <!-- 사용자 프로필, 삭제 버튼-->
        <div class="feed-detail-header">
          <div>
            <div>
              <img
                :src="'http://3.38.103.222:8092' + info.imagePath"
                alt="내프로필"
              />
            </div>
            <div>{{ info.username }}</div>
          </div>
          <div v-if="isDelete">
            <span class="material-icons" @click="deleteFeed(feedInfo.feedId)">
              delete
            </span>
          </div>
        </div>

        <!-- 피드 사진-->
        <div class="feed-detail-img">
          <video
            v-if="isVideo(feedInfo.mediaUrl)"
            :src="'http://3.38.103.222:8092' + feedInfo.mediaUrl"
            alt=""
            playsinline
            controls
          />
          <img
            v-else
            :src="'http://3.38.103.222:8092' + feedInfo.mediaUrl"
            alt=""
          />
        </div>

        <!-- 피드 메뉴바 (좋아요, 댓글, 스크랩) -->
        <div class="feed-detail-menu">
          <div>
            <span
              v-if="feedInfo.likeStatus"
              class="material-icons-outlined"
              @click="unlikeFeed(feedInfo.feedId)"
            >
              favorite
            </span>
            <span
              v-else
              class="material-icons-outlined"
              @click="likeFeed(feedInfo.feedId)"
            >
              favorite_border
            </span>
          </div>
          <div>
            <span
              class="material-icons-outlined"
              @click="goComment(info.username, info.email, feedInfo.feedId)"
            >
              comment
            </span>
          </div>
        </div>

        <!-- 피드 내용 (좋아요 개수, 피드 내용, 댓글) -->
        <div class="feed-detail-content">
          <div v-if="feedInfo.likeCnt == 0">가장 먼저 좋아요를 눌러보세요.</div>
          <div
            v-else
            @click="goLikeList(info.username, info.email, feedInfo.feedId)"
          >
            좋아요 {{ feedInfo.likeCnt }}개
          </div>
          <div>
            <div>
              {{ feedInfo.content }}
            </div>
          </div>
          <div @click="goComment(info.username, info.email, feedInfo.feedId)">
            댓글 모두 보기
          </div>
        </div>

        <!-- 피드 게시 날짜-->
        <div class="feed-detail-date">
          {{ feedInfo.createdAt[0] }}년 {{ feedInfo.createdAt[1] }}월
          {{ feedInfo.createdAt[2] }}일
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";

export default {
  name: "ProfileFeedDetailMain",
  props: ["info", "feedInfo", "isDelete"],
  data() {
    return {
      token: sessionStorage.getItem("jwt"),
      email: this.$route.params.email,
      name: this.$route.params.userName,
    };
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

    // 뒤로 가기
    goBack() {
      this.$emit("goBack");
    },

    // 피드 삭제
    async deleteFeed(feedId) {
      if (confirm("정말 삭제하시겠습니까?")) {
        await api
          .delete(`/feed/feeds/${feedId}`, {
            headers: {
              Authorization: this.token,
            },
          })
          .then((res) => {
            alert("삭제 성공!");
            console.log(res);
          })
          .catch((err) => {
            alert("삭제 실패!");
            console.log(err);
          });

        // 뒤로 가기
        this.$emit("goBack");
      }
    },

    // 좋아요 등록
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
    goComment(name, email, id) {
      this.$router.push({
        name: "ProfileFeedComment",
        params: { userName: name, email: email, feedId: id },
      });
    },

    // 좋아요 리스트 이동
    goLikeList(name, email, id) {
      this.$router.push({
        name: "ProfileFeedLike",
        params: { userName: name, email: email, feedId: id },
      });
    },
  },
};
</script>

<style></style>
