import api from "@/api/api.js";

export default {
  namespaced: true,

  state: {
    // 나의 정보
    otherInfo: {},

    // 나의 팔로워/팔로잉 정보
    otherFollows: {},

    // 나의 팔로워 정보
    otherFollowers: [],

    // 나의 팔로잉 정보
    otherFollowings: [],
  },

  mutations: {
    SET_OTHER_INFO(state, otherInfo) {
      state.otherInfo = otherInfo;
    },
    SET_OTHER_FOLLOWS(state, otherFollows) {
      state.otherFollows = otherFollows;
    },
    SET_OTHER_FOLLOWERS(state, otherFollowers) {
      state.otherFollowers = otherFollowers;
    },
    SET_OTHER_FOLLOWINGS(state, otherFollowings) {
      state.otherFollowings = otherFollowings;
    },
  },

  actions: {
    // 내 정보 조회해서 저장하기
    async setOtherInfo({ commit }, payload) {
      return await api
        .get(`/user/users/${payload.email}`, {
          headers: {
            Authorization: payload.token,
          },
        })
        .then((res) => {
          console.log(res);
          commit("SET_OTHER_INFO", res.data);
        });
    },

    // 나의 팔로워 + 팔로잉 조회해서 저장하기
    async setOtherFollows({ commit }, payload) {
      return await api
        .get(`/communication/follows/${payload.email}`, {
          headers: {
            Authorization: payload.token,
          },
        })
        .then((res) => {
          console.log(res);

          commit("SET_OTHER_FOLLOWS", res.data);
        });
    },

    // 나의 팔로워 조회해서 저장하기
    async setOtherFollowers({ commit }, payload) {
      return await api
        .get(`/communication/follows/${payload.email}/followers`, {
          headers: {
            Authorization: payload.token,
          },
        })
        .then((res) => {
          console.log(res);

          commit("SET_OTHER_FOLLOWERS", res.data);
        });
    },

    // 나의 팔로잉 조회해서 저장하기
    async setOtherFollowings({ commit }, payload) {
      return await api
        .get(`/communication/follows/${payload.email}/followings`, {
          headers: {
            Authorization: payload.token,
          },
        })
        .then((res) => {
          console.log(res);

          commit("SET_OTHER_FOLLOWINGS", res.data);
        });
    },
  },
};
