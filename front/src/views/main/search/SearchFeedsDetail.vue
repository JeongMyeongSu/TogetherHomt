<template>
  <div id="search-keyword-feedlist" v-if="searchFeedList">
    <div id="search-feed-detail-header">
      <span @click="goBack()" class="material-icons-outlined"
        >arrow_back_ios_new</span
      >

      <div v-if="searchKeywordDetail">#{{ searchKeywordDetail }}</div>
      <div v-else>탐색 탭</div>
      <div></div>
    </div>

    <div id="search-feed-detail-main">
      <!-- 피드 출력 -->
      <div>
        <div v-if="searchFeedList.length == 0">
          {{ goBack() }}
        </div>
        <div
          id="search-one-feed"
          v-else
          v-for="(feed, index) in searchFeedList"
          :key="index"
        >
          <!-- 사용자 프로필, 삭제 버튼-->
          <div class="feed-detail-header">
            <div @click="goProfile(feed.username, feed.email)">
              <div id="profile-img">
                <img
                  :src="`http://3.38.103.222:8092` + feed.profileUrl"
                  alt="피드작성자프로필"
                />
              </div>
              <div id="profile-username">{{ feed.username }}</div>
            </div>
            <div v-if="canDelete(feed.username, feed.email)">
              <span
                class="material-icons"
                @click="deleteFeed(feed.feedId, index)"
              >
                delete
              </span>
            </div>
          </div>

          <!-- 피드 사진-->
          <div
            id="feed-detail-img"
            v-if="
              feed.mediaUrl.slice(-3) == 'jpg' ||
              feed.mediaUrl.slice(-3) == 'png' ||
              feed.mediaUrl.slice(-3) == 'gif'
            "
          >
            <img :src="`http://3.38.103.222:8092` + feed.mediaUrl" />
          </div>
          <div v-else id="feed-detail-img">
            <video
              :src="`http://3.38.103.222:8092` + feed.mediaUrl"
              controls
              playsinline
            ></video>
          </div>

          <!-- 피드 메뉴바 (좋아요, 댓글, 스크랩) -->
          <div class="feed-detail-menu">
            <div id="like-btn">
              <img
                v-if="feed.likeStatus"
                id="like-status-btn"
                @click="unlikeFeed(feed.feedId, index)"
                src="@/assets/icon/full-heart.png"
                alt=""
              />
              <img
                v-else
                id="unlike-status-btn"
                @click="likeFeed(feed.feedId, index)"
                src="@/assets/icon/empty-heart.png"
                alt=""
              />
            </div>
            <img
              id="comment-btn"
              src="@/assets/icon/comment-btn.png"
              alt=""
              @click="goComment(feed.username, feed.email, feed.feedId)"
            />
          </div>

          <!-- 피드 내용 (좋아요 개수, 피드 내용, 댓글) -->
          <div class="feed-detail-content">
            <div v-if="feed.likeCnt == 0">가장 먼저 좋아요를 눌러보세요.</div>
            <div
              v-else
              @click="goLikeList(feed.username, feed.email, feed.feedId)"
            >
              좋아요 {{ feed.likeCnt }}개
            </div>
            <div>
              <div v-if="feed.content.length > 15">
                {{ feed.content.substring(0, 15) }}
                <span @click="goContentDetail()">...더보기</span>
              </div>
              <div v-else id="feed-one-content">
                <p>{{ feed.username }}</p>
                <p>{{ feed.content }}</p>
              </div>
            </div>
            <div @click="goComment(feed.username, feed.email, feed.feedId)">
              ...더보기
            </div>
          </div>

          <!-- 피드 게시 날짜-->
          <div class="feed-detail-date">
            {{ feed.createdAt[0] }}년 {{ feed.createdAt[1] }}월
            {{ feed.createdAt[2] }}일
          </div>
          <div></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";
import { mapState } from "vuex";
import "@/assets/css/search/searchFeedsDetail.css";
api.defaults.headers.common["Authorization"] = sessionStorage.getItem("jwt");

export default {
  name: "ProfileFeedDetailMain",
  data() {
    return {
      token: sessionStorage.getItem("jwt"),
      searchKeywordDetail: this.$route.query.searchKeywordDetail,
    };
  },
  computed: {
    ...mapState({ myInfo: (state) => state.myStore.myInfo }),
    ...mapState({ searchFeedList: (state) => state.feedStore.searchFeedList }),
  },
  methods: {
    // 뒤로 가기
    goBack() {
      this.$router.push({ name: "Search" });
    },
    // 삭제가능
    canDelete(author) {
      if (author == this.myInfo.username) {
        return true;
      }
    },

    // 피드 삭제
    async deleteFeed(feedId, index) {
      if (confirm("정말 삭제하시겠습니까?")) {
        this.$store.dispatch("feedStore/searchFeedsDelete", {
          feedId: feedId,
          index: index,
        });
      }
    },

    // 좋아요 등록
    async likeFeed(feedId, index) {
      await api
        .post(`/feed/${feedId}/likes`, null)
        .then(() => {
          this.$store.dispatch("feedStore/updateSearchFeeds", {
            feedId: feedId,
            index: index,
          });
          alert("좋아요 등록 성공!");
        })
        .catch((err) => {
          console.log(err);
          alert("좋아요 등록 실패!");
        });
      this.$router.go();
    },

    // 좋아요 취소
    async unlikeFeed(feedId, index) {
      await api
        .delete(`/feed/${feedId}/likes`)
        .then(() => {
          this.$store.dispatch("feedStore/updateSearchFeeds", {
            feedId: feedId,
            index: index,
          });
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

    // 좋아요 리스트 이동
    goLikeList(name, email, id) {
      this.$router.push({
        name: "ProfileFeedLike",
        params: { userName: name, email: email, feedId: id },
      });
    },

    goProfile(name, email) {
      console.log(email);
      this.$router.push({
        name: "Profile",
        params: { userName: name, email: email },
      });
    },
  },
};
</script>
