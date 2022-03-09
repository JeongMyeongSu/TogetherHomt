import User from "@/views/User";
import Login from "@/views/user/Login";
import Signup from "@/views/user/Signup";
import DoneSignup from "@/views/user/DoneSignup";
import FindPW from "@/views/user/FindPW";

export default {
  // StartPage : 로그인, 회원가입, 비밀번호 찾기
  path: "/",
  name: "User",
  component: User,
  children: [
    {
      name: "Login",
      path: "",
      component: Login,
    },
    {
      name: "Signup",
      path: "signup",
      component: Signup,
    },
    {
      name: "DoneSignup",
      path: "donesignup",
      component: DoneSignup,
    },
    {
      name: "FindPW",
      path: "findpw",
      component: FindPW,
    },
  ],
};
