import api from "@/api/api.js";

export default {
  namespaced: true,

  state: {
    // 나의 정보
    myInfo: {},

    // 나의 팔로워/팔로잉 정보
    myFollows: {},

    // 나의 팔로워 정보
    myFollowers: [],

    // 나의 팔로잉 정보
    myFollowings: [],

    // 나의 알람 정보
    myAlarms: [],
  },

  mutations: {
    SET_MY_INFO(state, myInfo) {
      state.myInfo = myInfo;
    },
    SET_MY_FOLLOWS(state, myFollows) {
      state.myFollows = myFollows;
    },
    SET_MY_FOLLOWERS(state, myFollowers) {
      state.myFollowers = myFollowers;
    },
    SET_MY_FOLLOWINGS(state, myFollowings) {
      state.myFollowings = myFollowings;
    },
    SET_MY_ALARMS(state, myAlarms) {
      state.myAlarms = myAlarms;
    },
  },

  actions: {
    // 내 정보 조회해서 저장하기
    async setMyInfo({ commit }, payload) {
      return await api
        .get(`/user/users/${payload.email}`, {
          headers: {
            Authorization: payload.token,
          },
        })
        .then((res) => {
          console.log(res);
          commit("SET_MY_INFO", res.data);
        });
    },

    // 나의 팔로워 + 팔로잉 조회해서 저장하기
    async setMyFollows({ commit }, payload) {
      return await api
        .get(`/communication/follows/${payload.email}`, {
          headers: {
            Authorization: payload.token,
          },
        })
        .then((res) => {
          console.log(res);

          commit("SET_MY_FOLLOWS", res.data);
        });
    },

    // 나의 팔로워 조회해서 저장하기
    async setMyFollowers({ commit }, payload) {
      return await api
        .get(`/communication/follows/${payload.email}/followers`, {
          headers: {
            Authorization: payload.token,
          },
        })
        .then((res) => {
          console.log(res);

          commit("SET_MY_FOLLOWERS", res.data);
        });
    },

    // 나의 팔로잉 조회해서 저장하기
    async setMyFollowings({ commit }, payload) {
      return await api
        .get(`/communication/follows/${payload.email}/followings`, {
          headers: {
            Authorization: payload.token,
          },
        })
        .then((res) => {
          console.log(res);

          commit("SET_MY_FOLLOWINGS", res.data);
        });
    },

    // 나의 알람 조회하기
    async setMyAlarms({ commit }, payload) {
      return await api
        .get(`/notification/notifications`, {
          headers: {
            Authorization: payload.token,
          },
        })
        .then((res) => {
          console.log(res);

          commit("SET_MY_ALARMS", res.data);
        });
    },
  },
};
