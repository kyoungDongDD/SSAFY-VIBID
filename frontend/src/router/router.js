import { createRouter, createWebHashHistory } from "vue-router";
import MainPage from "@/views/MainPage.vue";
import SignUp from "@/views/SignUp.vue";
import SearchPage from "@/views/SearchPage.vue";
import BoardWrite from "@/views/BoardWrite.vue";
import BoardModify from "@/views/BoardModify.vue";
import BoardDetail from "@/views/BoardDetail.vue";
import LoginPage from "@/views/LoginPage";
import MyPage from "@/views/MyPage.vue";
import AuctionPage from "@/views/AuctionPage.vue";
import FindPassword from "@/views/FindPassword.vue";
import ModifyPassword from "@/views/ModifyPassword.vue";

export default createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: "/",
      name: "Main",
      component: MainPage,
    },
    {
      path: "/signup",
      name: "SignUp",
      component: SignUp,
    },
    {
      path: "/boardwrite",
      name: "BoardWrite",
      component: BoardWrite,
    },
    {
      path: "/boarddetail",
      name: "BoardDetail",
      component: BoardDetail,
    },
    {
      path: "/boardmodify",
      name: "BoardModify",
      component: BoardModify,
    },
    {
      path: "/login",
      name: "LoginPage",
      component: LoginPage,
    },
    {
      path: "/findpassword",
      name: "FindPassword",
      component: FindPassword,
    },
    {
      path: "/modifypassword",
      name: "ModifyPassword",
      component: ModifyPassword,
    },
    {
      path: "/search",
      name: "SearchPage",
      component: SearchPage,
    },
    {
      path: "/mypage",
      name: "MyPage",
      component: MyPage,
    },
    {
      path: "/auction",
      name: "AuctionPage",
      component: AuctionPage,
    },
  ],
});
