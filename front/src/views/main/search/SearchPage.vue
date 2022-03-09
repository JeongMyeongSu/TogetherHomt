<template>
  <div class="search-page">
    <div id="search-bar-box">
      <div id="input-box">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          height="27px"
          viewBox="-7 -3 29 29"
          width="27px"
          fill="#404040"
        >
          <path d="M0 0h24v24H0V0z" fill="none" />
          <path
            d="M15.5 14h-.79l-.28-.27C15.41 12.59 16 11.11 16 9.5 16 5.91 13.09 3 9.5 3S3 5.91 3 9.5 5.91 16 9.5 16c1.61 0 3.09-.59 4.23-1.57l.27.28v.79l5 4.99L20.49 19l-4.99-5zm-6 0C7.01 14 5 11.99 5 9.5S7.01 5 9.5 5 14 7.01 14 9.5 11.99 14 9.5 14z"
          />
        </svg>
        <input
          id="search-bar"
          class="no-search-mode-input"
          @click="search()"
          @input="autoComplete"
          placeholder="검색"
          type="text"
        />
      </div>
      <div v-if="searchMode" id="btn-standard">
        <button @click="noSearch()">취소</button>
      </div>
    </div>

    <div v-if="searchMode" id="search-detail">
      <search-detail
        :searchKeyword="searchKeyword"
        :searchUsers="searchUsers"
        :tagSearchFeeds="tagSearchFeeds"
        @show-search-feeds="showSearchFeeds"
      ></search-detail>
    </div>

    <div v-else id="search-list">
      <search-list
        @all-feeds="getAllFeeds"
        :searchKeyword="searchKeyword"
        :showFeedList="showFeedList"
      ></search-list>
    </div>
  </div>
</template>

<script>
import "@/assets/css/search/searchPage.css";
import "@/assets/css/search/searchDetail.css";
import "@/assets/css/search/searchList.css";
import SearchDetail from "@/components/search/SearchDetail.vue";
import SearchList from "@/components/search/SearchList.vue";
import { mapState, mapActions } from "vuex";

export default {
  name: "SearchPage",
  components: {
    SearchList,
    SearchDetail,
  },
  data: function () {
    return {
      searchKeyword: null,
      searchMode: false,
      searchUsers: null, // 리스트에 띄울 유저 목록

      allFeeds: null,
      tagSearchFeeds: null,

      showFeedList: null,
    };
  },
  methods: {
    ...mapActions(["getAllUsers"]),

    search: function () {
      this.searchMode = true;
      document
        .querySelector("#search-bar")
        .classList.remove("no-search-mode-input");
      document.querySelector("#search-bar").classList.add("search-mode-input");
    },
    noSearch: function () {
      this.searchKeyword = null;
      document.querySelector("#search-bar").value = "";
      document
        .querySelector("#search-bar")
        .classList.remove("search-mode-input");
      document
        .querySelector("#search-bar")
        .classList.add("no-search-mode-input");
      this.searchMode = false;
      this.showFeedList = null;
      this.tagSearchFeeds = null;
      this.searchUsers = null;
    },
    getAllFeeds: function (allFeeds) {
      this.allFeeds = allFeeds;
      console.log(this.allFeeds);
    },

    autoComplete: function (event) {
      this.searchKeyword = event.target.value;
      if (this.searchKeyword) {
        // 전체유저 조회
        this.searchUsers = this.allUsers.filter((user) => {
          return user.username.includes(this.searchKeyword);
        });

        // 태그 조회
        this.tagSearchFeeds = this.allFeeds.filter((feed) => {
          return feed.tags != null && feed.tags.includes(this.searchKeyword);
        });
        this.tagSearchFeeds.sort((a, b) => {
          return b.like_cnt - a.like_cnt;
        });
      }
    },

    showSearchFeeds: function (showFeedList) {
      this.showFeedList = showFeedList;
      this.searchMode = false;
    },
  },

  mounted() {
    this.getAllUsers();
  },

  computed: {
    ...mapState(["allUsers"]),
  },
};
</script>
