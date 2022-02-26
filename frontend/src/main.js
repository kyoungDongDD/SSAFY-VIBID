import { createApp } from "vue";
import App from "./App";
import store from "./store/store";
import router from "./router/router";
import "bootstrap";

createApp(App).use(store).use(router).mount("#app");
