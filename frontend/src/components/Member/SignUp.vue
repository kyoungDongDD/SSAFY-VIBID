<template>
  <div id="signUpNormal">
    <div id="contents">
      <p class="main-title">회원가입</p>
      <p class="page_sub"><span class="ico">*</span>필수입력사항</p>
      <form class="form" @submit.prevent="submitForm">
        <div class="form-contants">
          <div class="row">
            <label class="col-3" for="email"
              >이메일<span class="ico">*</span></label
            >
            <div class="col-6">
              <input
                id="email"
                v-model="email"
                name="email"
                type="email"
                placeholder="6자 이상의 영문 혹은 영문과 숫자를 조합"
                @blur="checkDuplicate"
              /><span
                v-if="!isEmailValid"
                class="badge badge-danger mt-1"
                value=""
                >이메일 형식이 다릅니다.</span
              ><span class="badge badge-danger mt-1 availabele-email">{{
                availableEmail
              }}</span>
            </div>
            <div class="col-2 div-btn">
              <button type="button" class="btn" @click="checkEmail">
                중복확인
              </button>
            </div>
          </div>
          <div class="row">
            <label class="col-3" for="password"
              >비밀번호<span class="ico">*</span></label
            >
            <div class="col-6">
              <input
                id="password"
                v-model="password"
                type="password"
                placeholder="비밀번호를 입력해주세요"
              /><span v-if="!isPasswordValid" class="badge badge-danger mt-1">
                비밀번호 형식이 다릅니다.</span
              >
            </div>
            <div class="col-2"></div>
          </div>
          <div class="row">
            <label class="col-3" for="passwordConfirm"
              >비밀번호 확인<span class="ico">*</span></label
            >

            <div class="col-6">
              <input
                id="passwordConfirm"
                v-model="passwordConfirm"
                type="password"
                placeholder="비밀번호를 한번 더 입력해주세요"
              /><span v-if="!passwordCheck" class="badge badge-danger mt-1">
                비밀번호 일치하지 않습니다.</span
              >
            </div>
            <div class="col-2"></div>
          </div>
          <div class="row">
            <label class="col-3" for="nickName"
              >닉네임<span class="ico">*</span></label
            >
            <div class="col-6">
              <input id="nickName" v-model="nickName" type="text" />
            </div>
            <div class="col-2 div-btn">
              <button type="button" class="btn" @click="getNickname">
                자동 생성
              </button>
            </div>
          </div>
        </div>
        <button type="submit" class="btn btn-submit">가입하기</button>
      </form>
    </div>
  </div>
</template>

<script>
import { postRequest, getRequest } from "@/api/requests.js";
import axios from "axios";
import { validateEmail, validatePassword } from "@/assets/js/validation.js";
export default {
  name: "SignupForm",
  data() {
    return {
      email: "",
      password: "",
      passwordConfirm: "",
      nickName: "",
      availableEmail: "",
    };
  },
  computed: {
    isEmailValid() {
      if (this.email == "") {
        return true;
      }
      return validateEmail(this.email);
    },
    isPasswordValid() {
      if (this.password == "") {
        return true;
      }
      return validatePassword(this.password);
    },
    passwordCheck() {
      if (this.passwordConfirm == "") {
        //빈칸체크
        return true;
      }
      if (this.password == this.passwordConfirm) {
        return true;
      } else {
        return false;
      }
    },
  },
  mounted() {
    this.getNickname();
  },
  methods: {
    async submitForm() {
      //validation 확인
      if (this.email == "" || this.isEmailValid == false) {
        alert("이메일을 확인해주세요.");
        return 0;
      } else if (this.availableEmail != "") {
        alert("이메일을 중복확인해주세요");
        return 0;
      } else if (this.password == "" || this.isPasswordValid == false) {
        alert("비밀번호을 확인해주세요.");
        return 0;
      } else if (this.passwordCheck == false) {
        alert("비밀번호 확인을 확인해주세요.");
        return 0;
      }
      const userData = {
        email: this.email,
        password: this.password,
        passwordConfirm: this.passwordConfirm,
        nickname: this.nickName,
      };
      console.log(userData);

      const response = await postRequest("/api/user", userData); //url추가 필요
      if (response.status == 200) {
        this.$router.push("/login");
      } else {
        alert(response.data);
      }
    },

    getNickname: function () {
      axios
        .get("https://nickname.hwanmoo.kr/?format=json&count=1")
        .then((res) => {
          console.log(res);
          this.nickName = res.data.words[0];
          console.log(this.nickName);
        });
    },

    async checkEmail() {
      if (this.email == "") {
        alert("이메일을 입력해주세요");
        return false;
      }
      const { data } = await getRequest("/api/user/exist", { id: this.email });
      if (data.response == false) {
        this.availableEmail = "";
        console.log("DB 존재 X 사용가능한 이메일 입니다.");
        alert("사용가능한 이메일입니다.");
        return true;
      } else {
        this.availableEmail = "중복된 이메일입니다.";
        console.log("DB에 존재 사용 불가능");
        return false;
      }
    },
  },
};
</script>

<style lang="scss" scoped>
#signUpNormal {
  span {
    color: #ee6a7b;
  }
  #contents {
    padding-bottom: 60px;
    width: 700px;
    text-align: center;
    margin: 0 auto;
    .main-title {
      font-weight: bold;
      font-size: 28px;
      text-align: center;
      padding: 50px 0 51px;
    }
    .page_sub {
      text-align: right;
      padding: 0 10px 5px;
    }
    form {
      width: 100%;
      font-size: 16px;
      .form-contants {
        border-top: 3px solid #333;
        border-bottom: 2px solid #333;
        @media #{$mobile} {
          margin-right: 2%;
          margin-left: 2%;
        }
        .div-btn {
          margin-left: 3%;
        }
        div {
          align-items: center;
          padding: 10px 0;
          input {
            padding: 0px 14px;
            border: 1px solid $main-color8;
            border-radius: 3px;
            width: -webkit-fill-available;
            height: 44px;
          }
          input::placeholder {
            font-size: 14px;
            color: $main-color8;
          }
          button {
            margin: 0 0 0 5px;
            font-size: 16px;
            color: #98327e;
            border: 1px solid #98327e;
            height: 44px;
            width: 100%;
            border-radius: 3px;
            box-sizing: border-box;
          }
          label {
          }
        }
      }
      .btn-submit {
        margin-top: 40px;
        font-size: 16px;
        color: #ffff;
        border: 1px solid #98327e;
        background: #98327e;
        height: 56px;
        width: 30%;
        border-radius: 3px;
      }
    }
  }
}

@media #{$tablet} {
  #signUpNormal {
    span {
    }
    #contents {
      .main-title {
      }
      .page_sub {
      }
      form {
        font-size: 14px;
        font-size: 16px;
        .form-contants {
          margin-right: 2%;
          margin-left: 2%;

          div {
            input {
              height: 35px;
            }
            input::placeholder {
              font-size: 10px;
            }
            button {
              font-size: 10px;
              height: 35px;
            }
            label {
            }
          }
        }
        .btn-submit {
          font-size: 12px;
          height: 36px;
        }
      }
    }
  }
}
@media #{$mobile} {
  #signUpNormal {
    span {
    }
    #contents {
      width: 100vw;
      .main-title {
      }
      .page_sub {
      }
      form {
        .form-contants {
          margin-right: 2%;
          margin-left: 2%;
          div {
            input {
            }
            input::placeholder {
            }
            button {
            }
            label {
            }
          }
        }
        .btn-submit {
        }
      }
    }
  }
}
</style>
