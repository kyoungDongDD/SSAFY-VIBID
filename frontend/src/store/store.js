import { createStore } from "vuex";
import { Window } from "@/store/modules/Window.js";
import { LoginData } from "@/store/modules/LoginData.js";
import { Search } from "@/store/modules/Search.js";
import { MyPage } from "@/store/modules/MyPage.js";
import { Chating } from "@/store/modules/Chating.js";

export default createStore({
  modules: {
    Window,
    LoginData,
    Search,
    MyPage,
    Chating,
  },
});
