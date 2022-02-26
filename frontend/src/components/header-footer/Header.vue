h2<template>
  <div id="header">
    <h1 class="heading">
      네비게이션 메뉴
    </h1>
    <div
      v-if="$store.state.Window.windowWidth > 768"
      id="userMenu">
      <ul class="list_menu">
        <h2 class="heading">
          유저 메뉴
        </h2>
        <div v-if="needDisplay">
          <li
            v-for="button in loginButtons"
            :key="button.name"
            class="menu">
            <h3 class="heading">
              {{ button.content }}
            </h3>
            <route-button
              :request-url="button.requestUrl ? button.requestUrl : ''"
              :data="button"
              class="link-menu" />
          </li>
        </div>
        <div v-else>
          <li
            v-for="button in logoutButtons"
            :key="button.name"
            class="menu">
            <h3 class="heading">
              {{ button.content }}
            </h3>
            <route-button
              :request-url="button.requestUrl ? button.requestUrl : ''"
              :data="button"
              class="link-menu" />
          </li>
        </div>
      </ul>
    </div>
    <div id="headerLogo">
      <h2 class="heading">
        홈 버튼
      </h2>
      <div class="logo">
        <route-button
          :request-url="'api/board/main'"
          :data="{
            name: 'Main',
            content: 'Vibid',
            params: {
              tags: '',
              live: false,
              sort: 'DATE',
              order: 'DESC',
              page: 1,
            },
          }"
          class="link_main" />
      </div>
    </div>
    <div
      v-if="$store.state.Window.windowWidth > 768"
      id="generalNavigationBar"
      :class="{ stop: isScrolled }">
      <h2 class="heading">
        하단 고정 메뉴
      </h2>
      <div class="fixed_container">
        <div class="general_navigation_bar_vibid">
          <div class="inner">
            <div class="general_navigation_bar_main">
              <div class="left">
                <h3 class="heading">
                  전체 경매
                </h3>
                <route-button
                  :request-url="'api/board/goods'"
                  :data="{
                    name: 'SearchPage',
                    path: '/search',
                    content: '전체 경매',
                    params: {
                      tags: '',
                      live: false,
                      sort: 'DATE',
                      order: 'DESC',
                      page: 1,
                    },
                  }"
                  class="all-list-button" />
                <h3 class="heading">
                  라이브
                </h3>
                <route-button
                  :data="{
                    name: 'SearchPage',
                    path: '/search', 
                    content: 'LIVE',
                    params: {
                      tags: '',
                      live: true,
                      sort: 'DATE',
                      order: 'DESC',
                      page: 1,
                    },
                  }"
                  class="live-list-button" />
              </div>
              <div class="right">
                <h3 class="heading">
                  검색창
                </h3>
                <div style="position: relative">
                  <user-input class="search" />
                  <button
                    class="glass-button"
                    @click="searching">
                    <img
                      src="@/assets/search.png"
                      alt="" />
                  </button>
                </div>
                <h3 class="heading">
                  알림
                </h3>
                <button
                  class="alret-button"
                  @click="showAlert(!alertShow)">
                  <img
                    src="@/assets/bell.png"
                    alt="" />
                </button>
                <h3 class="heading">
                  찜 목록
                </h3>
                <route-button
                  :request-url="'api/mypage/wish'"
                  :data="{
                    name: 'MyPage',
                    params: {
                      tags: '',
                      live: false,
                      sort: 'DATE',
                      order: 'DESC',
                      page: 1,
                    },
                  }"
                  class="like-list-button">
                  <img
                    src="@/assets/love.png"
                    alt="" />
                </route-button>
                <div
                  v-show="alertShow"
                  class="alert">
                  <alert-list />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AlertList from "@/components/header-footer/AlertList.vue";
import RouteButton from "@/components/common/RouteButton.vue";
import UserInput from "@/components/common/UserInput.vue";
import updateItemList from '@/functions/updateItemList.js'

import { ref, onMounted, onUnmounted, computed } from "vue";
import { useStore } from "vuex";
import { useRoute, useRouter } from 'vue-router';

export default {
  components: {
    AlertList,
    RouteButton,
    UserInput,
  },
  setup() {
    const store = useStore()
    const router = useRouter()
    const route = useRoute()
    const searching = async () => {
      if(route.name === 'SearchPage'){
        await updateItemList('api/board/goods' ,store)
      }
      router.push({name: 'SearchPage', params:{requestUrl: 'api/board/goods'}})
    }
    const needDisplay = computed(() => {
      return store.state.LoginData.isLogin
    })
    const loginButtons = [
      {
        name: 'MyPage',
        content: '회원정보',
        requestUrl: 'api/mypage/wish',
        params: {
          tags: '',
          live: false,
          sort: 'DATE',
          order: 'DESC',
          page: 1,
        },
      },
      {
        name: 'Main',
        content: '로그아웃',
        params: {
          tags: '',
          live: false,
          sort: 'DATE',
          order: 'DESC',
          page: 1,
        },
      },
    ]
    const logoutButtons = [
      {
        name: "SignUp",
        content: "회원가입",
        params: {
          tags: '',
          live: false,
          sort: 'DATE',
          order: 'DESC',
          page: 1,
        },
      },
      {
        name: "LoginPage",
        content: "로그인",
        params: {
          tags: '',
          live: false,
          sort: 'DATE',
          order: 'DESC',
          page: 1,
        },
      },
    ];

    //알림창 토글
    const alertShow = ref(false);
    const showAlert = (status) => (alertShow.value = status);

    //스크롤 이벤트 추적
    const scrollLocation = ref(0);
    const isScrolled = computed(() => {
      if(scrollLocation.value >= 79) return true
      else return false
    })
    onMounted(() => window.addEventListener("scroll", () => scrollLocation.value = window.scrollY))
    onUnmounted(() => window.removeEventListener("scroll"), () => {})
    return {
      isScrolled,
      alertShow,
      loginButtons,
      logoutButtons,
      showAlert,
      needDisplay,
      searching
    }
  },
};
</script>

<style lang="scss" scoped>
#header {
  position: absolute;
  top: 0;
  width: 100%;
  #userMenu {
    width: 1050px;
    @media #{$tablet} {
      width: 100vw;
    }
    margin: 0 auto;

    * {
      font-weight: 400;
      letter-spacing: -0.3px;
    }

    .list_menu {
      float: right;
      font-size: 12px;
      line-height: 1;
      color: #4c4c4c;
      font-size: 12px;
      max-width: 100%;
    }

    .menu {
      position: relative;
      z-index: 400;
      float: left;
      cursor: pointer;     
    }
    .link-menu {
      float: left;
      height: 37px;
      padding: 0 12px;
      color: #333;
      line-height: 35px;
      white-space: nowrap;
    }
    &::after {
      content: "";
      display: block;
      overflow: hidden;
      width: 100%;
      height: 0;
      font-size: 0;
      text-indent: -9999px;
    }
  }
  #headerLogo {
    position: relative;
    width: 1050px;
    height: 63px;
    z-index: 300;
    @media #{$tablet} {
      height: 50px;
      width: 100vw;
    }
    @media #{$mobile} {
      position: fixed;
      width: 100vw;
      height: 0px;
    }
    margin: 0 auto;
    .logo {
      position: absolute;
      left: 0;
      text-align: center;
      width: 100%;
      background-color: #fff;
      font-weight: 500;
      font-family: "Sacramento", cursive;
      font-size: 60px;
      color: $main-color1;
      @media #{$tablet} {
        position: relative;
        height: 60px;
        font-size: 50px;
      }
      @media #{$mobile} {
        position: relative;
        height: 53px;
        font-size: 45px;
        background-color: $main-color1;
        color: #fff;
      }
    }
  }
  #generalNavigationBar {
    &.stop {
      height: 56px;
      .fixed_container {
        position: fixed;
        z-index: 300;
        left: 0;
        top: 0;
        width: 100%;
      }
    }
    .general_navigation_bar_vibid {
      position: relative;
      z-index: 300;
      background-color: #fff;
      font-family: "Noto Sans";
      letter-spacing: -0.3px;
      border-bottom: 1px solid black;
    }
    .inner {
      position: relative;
      height: 56px;
      .general_navigation_bar_main {
        display: flex;
        justify-content: space-between;
        height: inherit;
        .left {
          display: flex;
          .all-list-button,
          .live-list-button {
            width: 100px;
            margin: 0 20px;
            font-size: 20px;
          }
          .live-list-button {
            color: $main-color7;
          }
        }
        .right {
          position: relative;
          display: flex;
          align-items: center;
          * {
            margin: 5px;
          }
          .search {
            width: 242px;
            height: 36px;
            padding: 0 60px 0 14px;
            border: 1px solid $main-color9;
            border-radius: 18px;
            background-color: $main-color9;
            margin-right: 20px;
            font-weight: 400;
            font-size: 14px;
            color: #666;
            line-height: 16px;
            outline: none;
          }
          .glass-button {
            position: absolute;
            top: 1px;
            right: 20px;
            img {
              width: 25px;
            }
          }
          .alert {
            position: absolute;
            width: 400px;
            height: 326px;
            top: 50px;
            overflow-y: scroll;
            background-color: #fff;
            border: 1px solid #000;
          }
        }
        img {
          width: 40px;
        }
      }
    }
  }
}
</style>
