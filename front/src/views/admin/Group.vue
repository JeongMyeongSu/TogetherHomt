<template>
  <div id="group">
    <!-- 전체 유저
    <div id="group-user"
    v-for="(member, index) in groupState"
    :key="index"
    >
      <member-box
      :member="member"
      />
    </div> -->
  </div>
</template>

<script>
// import memberBox from "@/components/group/memberBox"
import { mapState } from 'vuex'

export default {
  name: 'Group',
  components: {
  },
  data() {
    return {
      groupName: "",
      groupState: [],
    }
  },
  methods: {
    makeGroup() {

    }
  },

  // mounted() {
  //   this.$store.dispatch("groupStore/getGroupMembers", this.myInfo.groupId)
  // },
  computed: {
    ...mapState({ groupMembers : (state) => state.groupStore.groupMembers}),
    ...mapState({ allUsers: (state) =>  state.allUsers})
  }

}
</script>
