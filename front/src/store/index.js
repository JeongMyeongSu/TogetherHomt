import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";

// import State from "@/store/states.js";
// import Getters from "@/store/getters.js";
import Mutations from "@/store/mutations.js";
import Actions from "@/store/actions.js";
import userStore from "@/store/userStore";

import myStore from "@/store/myStore";
import otherStore from "@/store/otherStore";
import feedStore from "@/store/feedStore";
import exerciseStore from "@/store/exerciseStore";
import groupStore from "@/store/groupStore";

Vue.use(Vuex);

export default new Vuex.Store({
  ...Mutations,
  ...Actions,

  modules: {
    userStore: userStore,
    myStore: myStore,
    otherStore: otherStore,
    feedStore: feedStore,
    exerciseStore: exerciseStore,
    groupStore: groupStore,
  },

  plugins: [
    createPersistedState({
      paths: [
        "userStore",
        "myStore",
        "otherStore",
        "feedStore",
        "exerciseStore",
        "groupStore",
      ],
    }),
  ],
});
