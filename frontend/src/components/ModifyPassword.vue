<template>
  <div class="content">
    <div class="find-password">
      비밀번호 변경
    </div>
    <div class="main">
      <div class="section-form">
        <div class="section-form1">
          <div class="password-form">
            <label
              for="token"
              class="password-write">인증토큰 입력</label>
            <div class="input-form">
              <input
                id="token"
                v-model="data.token" 
                name="token"
                placeholder="발급받은 토큰을 입력해주세요"
                type="text"
                class="input-password" />
            </div>
          </div>
          <p class="guide">
            메일로 발급받은 토큰을 정확히 입력해주세요
          </p>
          <div class="password-form">
            <label
              for="password"
              class="password-write">비밀번호 입력</label>
            <div class="input-form">
              <input
                id="password"
                v-model="data.password" 
                name="password"
                placeholder="새로운 비밀번호를 입력해주세요"
                type="password"
                class="input-password" />
            </div>
          </div>
          <div class="password-form">
            <label
              for="password-confirm"
              class="password-write">비밀번호 확인</label>
            <div class="input-form">
              <input
                id="password-confirm"
                v-model="data.passwordConfirm"
                name="password-confirm"
                placeholder="새로운 비밀번호를 입력해주세요"
                type="password"
                class="input-password" />
            </div>
          </div>
          <p class="guide">
            8~20자를 입력해주세요
          </p>
          <p class="guide">
            알파벳 대문자, 소문자, 특수문자, 숫자가 최소 하나를 포함해주세요
          </p>
        </div>
        <button
          class="button-text"
          @click="changePassword">
          비밀번호 변경
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { validatePassword } from "@/assets/js/validation.js";
import { patchRequest } from "@/api/requests.js"
import { ref } from '@vue/reactivity';
import { computed } from '@vue/runtime-core';
export default {
  setup() {
    const data = ref({ token: '', password: '', passwordConfirm: ''})
    const isPasswordValid = computed(() => {
      return (!validatePassword(data.value.password) || data.value.password === "") ? false : true
    })
    const isPasswordConfirmValid = computed(() => {
      return (!validatePassword(data.value.passwordConfirm) || data.value.passwordConfirm === "") ? false : true
    })
    const changePassword = () => {
      console.log(isPasswordValid.value)
      console.log(isPasswordConfirmValid.value)
      console.log(data.value.password === data.value.passwordConfirm)
      if(isPasswordValid.value && isPasswordConfirmValid.value && data.value.password === data.value.passwordConfirm) {
        patchRequest('https://i6b207.p.ssafy.io:8080/api/auth/password', data.value)
        // .then(response => console.log(response))
        // .error(error => console.log(error))
      }
      else {
        alert('비밀번호를 확인해주세요')
      }
    }

    return {
      data,
      changePassword
    }
  }
};
</script>

<style lang="scss" scoped>
.content {
  // width: 1050px;
  padding: 50px 0;
  background-color: white;
}
.find-password {
  padding-bottom: 30px;
  font-weight: 500;
  font-size: 28px;
  text-align: center;
}
.main {
  max-width: 400px;
  padding: 0 10px 6px 10px;
  margin: 0 auto;
  position: relative;
  background-color: white;
}
.write-form {
  background-color: white;
  padding: 0 15px 0 15px;
  margin: 0;
  box-shadow: inset 0 -0.5px 0 0 #ddd;
  display: flex;
  flex-wrap: nowrap;
  text-align: center;
}
.title {
  display: block;
  -webkit-box-flex: 1;
  flex-grow: 1;
  position: relative;
  height: 48px;
  font-weight: 500;
  font-size: 16px;
  color: #98327e;
  line-height: 18px;
  box-shadow: inset 0px -2px 0px 0px #98327e;
}
.section-form {
  padding: 24px 20px;
  margin-bottom: 20%;
}
.password-form {
  padding-bottom: 12px;
}
.password-write {
  display: inline-block;
  padding: 8px 0 11px;
  font-size: 14px;
  font-weight: 500;
  line-height: 19px;
  color: #333;
  text-align: center;
}
.input-form {
  position: relative;
  height: 48px;
  border-radius: 4px;
  border: 1px solid #ddd;
}
.input-password {
  width: 100%;
  height: 46px;
  padding: 0 11px 1px 15px;
  border: none;
  border-radius: 4px;
  font-weight: 400;
  font-size: 16px;
  line-height: 1.5;
  color: #333;
  outline: none;
}
.section-form1:not(:first-of-type) {
  margin-top: 8px;
}
.button-form {
  margin-top: 18px;
}
.button1:disabled {
  background-color: #ddd;
}
.button1 {
  display: block;
  padding: 0 10px;
  text-align: center;
  overflow: hidden;
  border-radius: 4px;
  color: #fff;
  border: 0 none;
  width: 320px;
  height: 63px;
}
.button-text {
  width: 100%;
  height: 60px;
  margin-top: 80px;
  text-align: center;
  border-radius: 5px;
  background-color: $main-color6;
  font-size: 16px;
  color: #fff;
}
.guide {
  font-weight: 700;
  margin: 0;
  margin-bottom: 5px;
  padding-left: 5px;
}
@media #{$mobile} {
  .content {
    margin: 0 auto;
    width: 100vw;
  }
  .find-password {
    font-size: 17px;
  }
  .button1 {
    // font-size: 10px;
    width: 90%;
    height: 46px;
    padding: 0 11px 1px 15px;
    font-size: 16px;
    line-height: 1.5;
  }
}
</style>
