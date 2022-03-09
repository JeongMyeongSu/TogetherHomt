export default {
  mutations: {
    SET_MY_INFO(state, myInfo) {
      state.myInfo = myInfo;
    },
    SET_USER_INFO(state, userInfo) {
      state.userInfo = userInfo;
    },
    SET_USER_FEEDS(state, userFeeds) {
      state.userFeeds = userFeeds;
    },
    SET_MY_FOLLOW(state, myFollow) {
      state.myFollow = myFollow;
    },
    SET_MY_FOLLOWER(state, myFollower) {
      state.myFollower = myFollower;
    },
    SET_MY_FOLLOWING(state, myFollowing) {
      state.myFollowing = myFollowing;
    },
    SET_USER_FOLLOW(state, userFollow) {
      state.userFollow = userFollow;
    },
    SET_USER_FOLLOWER(state, userFollower) {
      state.userFollower = userFollower;
    },
    SET_USER_FOLLOWING(state, userFollowing) {
      state.userFollowing = userFollowing;
    },
    SET_FEED_COMMENTS(state, feedComments) {
      state.feedComments = feedComments;
    },

    GET_ALL_USERS(state, allUsers) {
      state.allUsers = allUsers;
    },
  },
};
