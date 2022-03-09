<template>
  <div class="search-detail">
    <div id="search-detail-box">
      <div id="search-nav">
        <div id="popularity" @click="activeCategory('popularity')">인기</div>
        <div id="user" @click="activeCategory('user')">계정</div>
        <div id="tag" @click="activeCategory('tag')">태그</div>
      </div>

      <div id="search-items">
        <div
          id="tag-box"
          v-if="tagSearchFeeds != null && tagSearchFeeds.length != 0"
          v-show="active == 'popularity' || active == 'tag'"
          @click="showList()"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            height="100%"
            viewBox="-1 -1 52 52"
            width="100%"
            fill="#000000"
          >
            <g>
              <circle cx="25" fill="#ffffff" cy="25" r="25" stroke="#E5E5E5" />
            </g>
            <g>
              <path
                transform="translate(13 13)"
                d="M20,10V8h-4V4h-2v4h-4V4H8v4H4v2h4v4H4v2h4v4h2v-4h4v4h2v-4h4v-2h-4v-4H20z M14,14h-4v-4h4V14z"
              />
            </g>
          </svg>
          <div id="info-box">
            <p id="main-info">{{ searchKeyword }}</p>
            <p id="sub-info">{{ tagSearchFeeds.length }} 게시물</p>
          </div>
        </div>

        <div v-show="active == 'popularity' || active == 'user'">
          <div v-for="(item, index) in searchUsers" :key="index">
            <div id="account-box" @click="goProfile(item.username, item.email)">
              <img :src="mediaURL(index)" alt="프로필" />
              <div id="info-box">
                <p id="main-info">{{ item.username }}</p>
                <p id="sub-info">{{ item.introduce }}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const server = "http://3.38.103.222:8092";

export default {
  name: "SearchDetail",
  data: function () {
    return {
      active: "popularity",
      sortedResult: null,
    };
  },
  props: {
    searchKeyword: String,
    searchUsers: Array,
    tagSearchFeeds: Array,
  },
  methods: {
    activeCategory: function (subject) {
      if (this.active != subject) {
        document
          .querySelector(`#${this.active}`)
          .classList.remove("active-sub");
        this.active = subject;
        document.querySelector(`#${subject}`).classList.add("active-sub");
      }
    },

    mediaURL(idx) {
      return server + this.searchUsers[idx].imagePath;
    },

    goProfile(username, email) {
      this.$router.push({
        name: "Profile",
        params: { userName: username, email: email },
      });
    },

    showList() {
      this.$emit("show-search-feeds", this.tagSearchFeeds);
    },
  },
  mounted() {
    document.querySelector("#popularity").classList.add("active-sub");
  },
};
</script>

<style></style>
