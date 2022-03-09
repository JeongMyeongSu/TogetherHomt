import Main from "@/views/Main";
import Feed from "@/views/main/feed/Feed"; //뉴스 피드 조회
import CommentPage from "@/views/main/feed/CommentPage"; //댓글리스트
import LikeListPage from "@/views/main/feed/LikeListPage"; //좋아요 페이지
import SearchPage from "@/views/main/search/SearchPage";
import SearchFeedsDetail from "@/views/main/search/SearchFeedsDetail";
import Register from "@/views/main/Register";
import ExerciseList from "@/views/main/exercise/ExerciseList";
import MotionDetect from "@/views/main/exercise/MotionDetect";
import ExerciseDemo from "@/views/main/exercise/ExerciseDemo"; // 운동 시연영상 페이지

import Profile from "@/views/main/profile/Profile"; // 프로필 페이지
import Calendar from "@/views/main/profile/Calendar"; // 캘린더 페이지
import ProfileEdit from "@/views/main/profile/ProfileEdit"; // 프로필 편집 페이지
import PasswordEdit from "@/views/main/profile/PasswordEdit"; // 비밀번호 변경 페이지
import ProfileFeedDetail from "@/views/main/profile/ProfileFeedDetail"; // 프로필 피드 상세 페이지
import Follow from "@/views/main/Follow"; // 팔로우 페이지
import ProfileFeedComment from "@/views/main/profile/ProfileFeedComment"; // 프로필 피드 댓글 페이지
import ProfileFeedLike from "@/views/main/profile/ProfileFeedLike"; // 프로필 피드 좋아요 페이지
import AdminPage from "@/views/admin/AdminPage";
import Group from "@/views/admin/Group";
import AdminRegister from "@/views/admin/AdminRegister";

import Notification from "@/views/main/alarm/Notification"; // 알람 확인 페이지
import CheckWhoExercise from "@/views/main/alarm/CheckWhoExercise"; // 팔로우한 유저 퇴실 확인 페이지

export default {
  // MainPage : 피드, 검색, (피드)등록, 운동, 프로필
  path: "/main",
  name: "Main",
  component: Main,
  children: [
    {
      name: "Feed",
      path: "feed",
      component: Feed,
    },
    {
      name: "CommentPage",
      path: "feed/comment",
      component: CommentPage,
    },
    {
      name: "LikeListPage",
      path: "feed/likelistpage",
      component: LikeListPage,
    },
    {
      name: "Search",
      path: "search",
      component: SearchPage,
    },
    {
      name: "SearchFeedsDetail",
      path: "search/feeds",
      component: SearchFeedsDetail,
    },
    {
      name: "Register",
      path: "register",
      component: Register,
    },
    {
      name: "Exercise",
      path: "exercise",
      component: ExerciseList,
    },
    {
      name: "MotionDetect",
      path: "motiondetect",
      component: MotionDetect,
    },
    {
      name: "ExerciseDemo",
      path: "exercise/demo",
      component: ExerciseDemo,
    },
    {
      name: "Profile",
      path: "profile/:userName/:email",
      component: Profile,
      props: {
        default: true,
      },
    },
    {
      name: "Follow",
      path: "profile/:follow/:userName/:email",
      component: Follow,
      props: {
        default: true,
      },
    },
    {
      name: "ProfileEdit",
      path: "profile/edit",
      component: ProfileEdit,
    },
    {
      name: "PasswordEdit",
      path: "profile/password",
      component: PasswordEdit,
    },
    {
      name: "Calendar",
      path: "calendar/:userName/:email",
      component: Calendar,
      props: {
        default: true,
      },
    },
    {
      name: "ProfileFeedDetail",
      path: "profile/feed/:userName/:email:/:feedId",
      component: ProfileFeedDetail,
      props: {
        default: true,
      },
    },
    {
      name: "ProfileFeedComment",
      path: "profile/feed/comment/:userName/:email:/:feedId",
      component: ProfileFeedComment,
      props: {
        default: true,
      },
    },
    {
      name: "ProfileFeedLike",
      path: "profile/feed/like/:userName/:email:/:feedId",
      component: ProfileFeedLike,
      props: {
        default: true,
      },
    },
    {
      name: "AdminPage",
      path: "admin",
      component: AdminPage,
    },
    {
      name: "AdminRegister",
      path: "admin/register",
      component: AdminRegister,
    },
    {
      name: "Group",
      path: "admin/group",
      component: Group,
    },
    {
      name: "Notification",
      path: "notice",
      component: Notification,
    },
    {
      name: "CheckWhoExercise",
      path: "check-who-exercise",
      component: CheckWhoExercise,
    },
  ],
};
