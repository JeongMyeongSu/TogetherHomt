<template>
  <div id="profile-feed-navbar">
    <div>
      <ul>
        <li class="menu1">
          <router-link :to="{ name: '' }">
            <span class="material-icons" @click="changeMenu(0)"> grid_on </span>
          </router-link>
        </li>
        <li class="menu2">
          <router-link :to="{ name: '' }">
            <span class="material-icons" @click="changeMenu(1)">
              assignment_ind
            </span>
          </router-link>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: "ProfileFeedNavbar",
  data() {
    return {
      menu: ["menu1", "menu2"],
    };
  },
  methods: {
    changeMenu(num) {
      for (let i = 0; i < 2; i++) {
        // 활성화색으로 바꾸기
        if (num == i) {
          document.querySelector(
            `#profile-feed-navbar > ul > .${this.menu[i]}`
          ).style.borderBottomColor = "rgba(0, 0, 0, 0.38)";
          document.querySelector(
            `#profile-feed-navbar > ul > .${this.menu[i]} > a > span`
          ).style.color = "rgba(41, 98, 255, 0.87)";
        }
        // 비활성화색으로 바꾸기
        else {
          document.querySelector(
            `#profile-feed-navbar > ul > .${this.menu[i]}`
          ).style.borderBottomColor = "rgba(0, 0, 0, 0.1)";
          document.querySelector(
            `#profile-feed-navbar > ul > .${this.menu[i]} > a > span`
          ).style.color = "rgba(0, 0, 0, 0.38)";
        }
      }
    },
  },
};
</script>

<style></style>
