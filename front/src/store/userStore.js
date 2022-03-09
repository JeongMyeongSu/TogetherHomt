export default {
  namespaced: true,

  state: {
    LoginUser: null, //username
  },

  mutations: {
    GET_LOGIN_USER: function (state, username) {
      state.LoginUser = username;
    },
  },

  actions: {
    getLoginUser: function ({ commit }, username) {
      commit("GET_LOGIN_USER", username);
    },
  },
};
