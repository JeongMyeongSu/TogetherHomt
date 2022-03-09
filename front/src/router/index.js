import Vue from "vue";
import VueRouter from "vue-router";

import UserRouters from "@/router/user";
import MainRouters from "@/router/main";

Vue.use(VueRouter);

const routes = [
  // 로그인, 회원가입, 비밀번호 찾기
  { ...UserRouters },

  // 피드, 검색, 등록, 운동, 프로필
  { ...MainRouters },
];

const router = new VueRouter({
  mode: "history",
  routes,
});

export default router;
