<template>
  <div class="content">
    <div class="find-password">
      비밀번호 재발급
    </div>
    <div class="main">
      <div class="write-form">
        <p class="title">
          이메일 인증
        </p>
      </div>
      <div class="section-form">
        <div class="section-form1">
          <div class="email-form"> 
            <label
              for="email"
              class="email-write">이메일</label>
            <div class="input-form">
              <input 
                id="email"
                v-model="emailValue.address"
                name="email"
                placeholder="이메일을 입력해 주세요"
                type="email"
                class="input-email" />
            </div>
          </div>
        </div>
        <div class="button-form">
          <button
            class="btn"
            radius="4"
            @click="getResetToken">
            인증번호 발급받기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from '@vue/reactivity';
import { useRouter } from 'vue-router';
import { getRequest } from '@/api/requests.js'
export default {
  setup() {
    const router = useRouter();
    const emailValue = ref({ address: ''})
    const CheckEmail = (str) => (/^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/).test(str)? false : true             
    const getResetToken = () => {
      if (CheckEmail(emailValue.value.address)) alert('잘못된 이메일 형식입니다.')
      else {
        getRequest('https://i6b207.p.ssafy.io:8080/api/user/changePassword', emailValue.value)
        router.push({name: 'ModifyPassword'})
      }
    }
    return { emailValue, getResetToken }
  }
};
</script>

<style lang="scss" scoped>
.content {
  padding: 50px 0;
  background-color: white;

  @media #{$tablet} {
    width: 100vw;
    margin: 0 auto;
    width: auto;
  }
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
  margin: auto;
  position: relative;
  background-color: white;
}
.write-form {
  background-color: white;
  padding: 0 15px 0 15px;
  margin: 0;
  display: flex;
  flex-wrap: nowrap;
  text-align: center;
}
.title {
  display: block;
  flex-grow: 1;
  position: relative;
  height: 30px;
  font-weight: 500;
  font-size: 16px;
  color: #98327e;
  line-height: 16px;
  box-shadow: inset 0px -2px 0px 0px $main-color6;
}
.section-form {
  padding: 24px 20px;
  margin-bottom: 20%;
}
.email-form {
  padding-bottom: 12px;
}
.email-write {
  display: inline-block;
  padding: 8px 0 11px;
  font-size: 14px;
  font-weight: 500;
  line-height: 19px;
  color: #333;
}
.input-form {
  position: relative;
  height: 48px;
  border-radius: 4px;
  border: 1px solid #ddd;
}
.input-email {
  width: 100%;
  height: 46px;
  padding: 0 11px 1px 15px;
  border: none;
  border-radius: 4px;
  font-weight: 400;
  font-size: 16px;
  box-sizing: border-box;
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
.btn {
  display: block;
  width: 320px;
  height: 63px;
  padding: 0 10px;
  text-align: center;
  overflow: hidden;
  border-radius: 4px;
  color: #fff;
  background-color: $main-color6;
  border: 0 none;
  font-size: 16px;
}
@media #{$mobile} {
  .content {
    margin: 0 auto;
    width: 50vw;
  }
  .find-password {
    font-size: 17px;
  }
  .main {
    margin: 0;
  }
  .btn {
    width: 90%;
    height: 46px;
    padding: 0 11px 1px 15px;
    font-size: 16px;
    line-height: 1.5;
  }
}
</style>
