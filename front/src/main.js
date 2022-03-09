import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import VCalendar from "v-calendar";
// import axios from "axios";
// axios.defaults.headers.common["Authorization"] = sessionStorage.getItem("jwt");

Vue.config.productionTip = false;
// Vue.prototype.$axios = axios;

Vue.use(VCalendar);

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
