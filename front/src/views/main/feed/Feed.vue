<template>
  <div id="feed">
    <THpost />
  </div>
</template>

<script>
import THpost from "@/components/feed/THpost";

import { mapState } from "vuex";

export default {
  name: "Feed",
  data: () => {
    return {
      token: sessionStorage.getItem("jwt"),
    };
  },
  components: {
    THpost,
  },
  computed: {
    // 로그인한 사용자 이메일 가져오기
    ...mapState({ loginUser: (state) => state.userStore.LoginUser }),
  },
  async mounted() {
    // 내 정보 가져오기
    await this.$store.dispatch("myStore/setMyInfo", {
      email: this.loginUser,
      token: this.token,
    });

    //팔로우 기반 피드 리스트 가져오기
    await this.$store.dispatch("feedStore/setFollowFeedsInfo", {
      email: this.email,
      token: this.token,
    });
  },
};
</script>

<style lang="scss" scoped></style>
