<template>
  <div id="main">
    <div id="inner">
      <div
        id="qnb"
        class="quick-navigation"></div>
      <div class="section-login">
        <p class="title-login">
          로그인
        </p>
        <div class="write-form">
          <div class="write-view login-view">
            <form @submit.prevent="clickLogin">
              <input
                v-model="userInput.id"
                type="email"
                name="email"
                size="20"
                tabindex="1"
                placeholder="아이디를 입력해주세요" />
              <input
                v-model="userInput.password"
                type="password"
                name="password" 
                size="20"
                tabindex="2"
                placeholder="비밀번호를 입력해주세요" />
              <div class="login_search">
                <button @click="$router.push({name: 'FindPassword'})">
                  로그인에 문제가 있나요?
                </button>
              </div>
              <button
                class="btn_type1"
                type="submit">
                <span class="txt_type">로그인</span>
              </button>
            </form>
            <a
              class="btn_type2 btn_member"
              href="/#">
              <span class="txt_type">회원가입</span>
            </a>
            <div
              id="SocialLogin"
              class="image">
            </div>
          </div>
        </div>
        <div class="social-login">
          <a
            v-for="socialButton in socialButtons"
            :key="socialButton.href"
            :href="socialButton.href">
            <img
              :src="socialButton.src"
              alt="" />
          </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { postRequest, getRequest } from "@/api/requests.js";
import { ref } from '@vue/reactivity';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
const BASE_URL = "https://i6b207.p.ssafy.io:8080/oauth2/authorization/"
export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const userInput = ref({ id: '', password: ''})
    const clickLogin = async () => {
      await postRequest('/api/auth', userInput.value)
              .then(async response => {
                localStorage.setItem('jwt', response.data.response.jwtToken)
                getRequest('/api/user', '', response.data.response.jwtToken)
                .then(async (response) => {
                  await store.dispatch('changeUserEmail', response.data.response.email)
                  await store.dispatch('changeImageUrl', response.data.response.profileImageUrl)
                })
              })
              .catch(error => console.log(error))
      await store.dispatch('login')
      router.push({name: 'Main'})
    }

    return { userInput, clickLogin }
  },
  data () {
    return {
      socialButtons: [
        {
          src: require('@/assets/google_login.png'),
          href: BASE_URL + 'google'
        },
        {
          src: require('@/assets/naver_login.png'),
          href: BASE_URL + 'naver'
        },
        {
          src: require('@/assets/kakao_login.png'),
          href: BASE_URL + 'kakao'
        },
        {
          src: require('@/assets/facebook_login.png'),
          href: BASE_URL + 'facebook'
        },
      ]
    }
  }
};
</script>

<style lang="scss" scoped>
#inner {
  width: 1050px;
  @media #{$tablet} {
    width: 100vw;
  }
  padding-bottom: 200px;
  position: relative;
  margin: 0 auto;

  .section-login {
    width: 340px;
    margin: 0 auto;
    padding-top: 90px;
    letter-spacing: -0.6px;
    .title-login {
      font-weight: 800;
      font-size: 20px;
      line-height: 20px;
      text-align: center;
      color: #333;
    }
    .write-form {
      padding: 36px 9px 0 9px;
      color: #333;
    }
    .login_view {
      text-align: center;
    }
      input {
      width: 100%;
      height: 52px;
      padding: 0 19px;
      border: 1px solid #ccc;
      border-radius: 3px;
      background-color: #fff;
      font-size: 14px;
      line-height: 20px;
      box-sizing: border-box;
      outline: none;
    }
    input[type="password"] {
      margin-top: 10px;
    }
    .btn_member {
      margin-top: 10px;
    }
    .login_search {
      float: right;
      padding: 13px 0;
      letter-spacing: -0.6px;
      display: block;
    }
    .login_search .link {
      float: left;
      font-size: 13px;
    }
    
    .social-login {
      margin-top: 50px;
      a{
        cursor: pointer;
        img {
          width: 100%;
        }
      }
    }
  }
  select,
  textarea,
  button {
    line-height: 1;
    letter-spacing: -0.05em;
    color: #4c4c4c;
    font-size: 12px;
    max-width: 100%;
  }
  .btn_type1 {
    border: 1px solid #98327e;
    background-color: #98327e;
  }
  .btn_type1,
  .btn_type2 {
    display: block;
    overflow: hidden;
    width: 100%;
    height: 50px;
    border-radius: 5px;
    font-size: 0;
    text-align: center;
    box-sizing: border-box;
  }
  .btn_type2 {
    border: 2px solid #98327e;
    background-color: #fff;
  }
  .btn_type1 .txt_type {
    display: inline-block;
    height: 100%;
    font-weight: 500;
    font-size: 16px;
    color: #fff;
    line-height: 50px;
    text-align: center;
  }
  .btn_type1 .txt_type {
    color: #fff;
  }
  .btn_type2 .txt_type {
    color: #98327e;
  }
  .btn_type2 .txt_type {
    display: inline-block;
    height: 100%;
    font-weight: 500;
    font-size: 16px;
    color: #98327e;
    line-height: 51px;
    text-align: center;
  }
}
</style>
