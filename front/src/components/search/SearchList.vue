<template>
  <div class="search-list">
    <div id="feed-container">
      <div
        id="search-thumbnail"
        v-for="(feed, index) in showList"
        :key="index"
        @click="goFeeds(index)"
      >
        <div
          id="thumbnail-detail"
          v-if="
            mediaURL(index).slice(-3) == 'jpg' ||
            mediaURL(index).slice(-3) == 'png' ||
            mediaURL(index).slice(-3) == 'gif' ||
            mediaURL(index).slice(-4) == 'jpeg' ||
            mediaURL(index).slice(-4) == 'jfif'
          "
        >
          <img id="thumbnail-media" :src="mediaURL(index)" />
        </div>
        <div id="thumbnail-detail" v-else>
          <video
            id="thumbnail-media"
            :src="mediaURL(index)"
            playsinline
            muted
          ></video>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/api/api.js";
const server = "http://3.38.103.222:8092";

export default {
  name: "SearchList",
  props: {
    searchKeyword: String,
    showFeedList: Array,
    searchFeedDetailList: Array,
  },
  data: function () {
    return {
      allFeeds: null,
      showList: null,
    };
  },
  methods: {
    mediaURL(idx) {
      return server + this.showList[idx].mediaUrl;
    },

    goFeeds(idx) {
      console.log(idx);
      if (this.showList != null) {
        this.$store.dispatch(
          "feedStore/setSearchFeeds",
          this.showList.slice(idx)
        );
        this.$router.push({
          name: "SearchFeedsDetail",
          query: { searchKeywordDetail: this.searchKeyword },
        });
      } else {
        this.$store.dispatch(
          "feedStore/setSearchFeeds",
          this.allFeeds.slice(idx)
        );
        this.$router.push({
          name: "SearchFeedsDetail",
          query: { searchKeywordDetail: this.searchKeyword },
        });
      }
    },
  },
  mounted() {
    api({
      method: "get",
      url: `/feed/feeds/search`,
      headers: {
        Authorization: sessionStorage.getItem("jwt"),
      },
    })
      .then((res) => {
        this.allFeeds = res.data;
        this.$emit("all-feeds", this.allFeeds);

        if (this.showFeedList == null) {
          this.showList = this.allFeeds;
        } else {
          this.showList = this.showFeedList;
        }
      })
      .catch((err) => {
        alert(err);
      });
  },
};
</script>
