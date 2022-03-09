<template>
  <div id="navbar">
    <div>
      <ul>
        <!-- 피드 -->
        <li class="menu-feed">
          <router-link :to="{ name: 'Feed' }">
            <span class="material-icons" @click="changeMenu(0)"> home </span>
          </router-link>
        </li>

        <!-- 검색 -->
        <li class="menu-search">
          <router-link :to="{ name: 'Search' }">
            <span class="material-icons" @click="changeMenu(1)"> search </span>
          </router-link>
        </li>

        <!-- (피드) 등록 -->
        <li class="menu-add">
          <router-link :to="{ name: 'Register' }">
            <span class="material-icons-outlined" @click="changeMenu(2)">
              add_box
            </span>
          </router-link>
        </li>

        <!-- 운동 -->
        <li class="menu-homt">
          <router-link :to="{ name: 'Exercise' }">
            <span class="material-icons-outlined" @click="changeMenu(3)">
              fitness_center
            </span>
          </router-link>
        </li>

        <!-- 프로필 -->
        <li class="menu-my" @click="changeMenu(4)">
          <div v-if="myInfo.username != 'admin'">
            <router-link
              :to="{
                name: 'Profile',
                params: { userName: myInfo.username, email: myInfo.email },
              }"
            >
              <span class="material-icons"> account_circle </span>
            </router-link>
          </div>
          <div v-else>
            <router-link
              :to="{ name: 'AdminPage', params: { userName: 'admin' } }"
            >
              <span class="material-icons"> account_circle </span>
            </router-link>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import "@/assets/css/navbar.css";

export default {
  name: "Navbar",
  data() {
    return {
      menu: ["menu-feed", "menu-search", "menu-add", "menu-homt", "menu-my"],
    };
  },
  props: ["myInfo"],
  methods: {
    // 메뉴 아이콘 색깔 바꾸기
    changeMenu() {
      // for (let i = 0; i < 5; i++) {
      //   // 활성화색으로 바꾸기
      //   if (num == i) {
      //     document.querySelector(
      //       `#navbar .${this.menu[i]}`
      //     ).style.borderTopColor = "rgba(41, 98, 255, 0.87)";
      //     document.querySelector(
      //       `#navbar > ul > .${this.menu[i]} > a > span`
      //     ).style.color = "rgba(41, 98, 255, 0.87)";
      //   }
      //   // 비활성화색으로 바꾸기
      //   else {
      //     document.querySelector(
      //       `#navbar > ul > .${this.menu[i]}`
      //     ).style.borderTopColor = "rgba(0, 0, 0, 0.1)";
      //     document.querySelector(
      //       `#navbar > ul > .${this.menu[i]} > a > span`
      //     ).style.color = "rgba(0, 0, 0, 0.87)";
      //   }
      // }
    },
  },
};
</script>

<style></style>
