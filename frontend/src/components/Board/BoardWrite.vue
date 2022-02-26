<template>
  <div id="boardWrite">
    <div id="container">
      <p class="main-title">
        상품등록
      </p>
      <div id="contents">
        <form @submit.prevent="registForm">
          <div class="row row-wrap">
            <div
              v-if="image"
              class="col col-8 thumbnail w-full h-full flex items-center">
              <img
                :src="image"
                alt="image" />
            </div> 
            <div
              v-else
              class="col col-8 thumbnail w-full h-full flex items-center justify-center cursor-pointer hover:bg-pink-100"
              @click="clickInputTag()">
              <input
                id="input"
                ref="image"
                type="file"
                name="image"
                accept="image/*"
                multiple="multiple"
                class="hidden"
                @change="uploadImage()" />
              <svg
                class="w-8 h-8"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor">
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M12 9v3m0 0v3m0-3h3m-3 0H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
            </div>
            <div class="col right-contents">
              <div class="row d-flex">
                <div class="col">
                  경매시작가
                </div>
                <div class="col col-6">
                  <input
                    v-model="startingPrice"
                    class=""
                    type="number" />
                </div>
              </div>
              <div class="row d-flex">
                <div class="col">
                  호가단위
                </div>
                <div class="col col-6">
                  <input
                    v-model="bidding"
                    class=""
                    type="number" />
                </div>
              </div>
              <div class="row date-contents">
                <div class="col col">
                  경매 시작 시간
                </div>
                <div class="col col-6 date-col">
                  <Datepicker
                    v-model="bidStartTime"
                    class="date-picker"
                    :time-picker-component="timePicker" />
                </div>
              </div>
            </div>
          </div>
          <hashtags @update-tag="updateTag" />
          <div class="col col-12 board-title">
            <input
              v-model="title"
              class=""
              type="text"
              placeholder="제목을 입력해주세요" />
          </div>

          <div class="row row-wrap quill-div">
            <QuillEditor @update-content="updateContent" />
          </div>
          <div class="col col-6">
            <button
              type="submit"
              class="write-button">
              상품 등록
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import Datepicker from "vue3-date-time-picker";
import "vue3-date-time-picker/dist/main.css";
import Hashtags from "../common/Hashtags.vue";
import QuillEditor from "../common/QuillEditor.vue";
import { postRequest } from "@/api/requests.js";
import axios from "axios";
export default {
  name: "RegistForm",
  components: { Datepicker, Hashtags, QuillEditor },

  data() {
    return {
      image: "",
      title: "",
      content: "",
      startingPrice: "",
      bidding: "",
      bidStartTime: "",
      hashTags: [],
    };
  },
  mounted() {},
  methods: {
    async registForm() {
      console.log("물품 등록");
      const boardData = {
        title: this.title,
        content: this.content,
        thumbnailImageUrl: this.image,
        startingPrice: this.startingPrice,
        bidding: this.bidding,
        bidStartTime: this.bidStartTime,
        tags: this.hashTags.map((tag) => tag.value),
        contentImageUrl: [],
      };
      console.log(localStorage.getItem("jwt"));
      console.log(boardData);
      const response = await postRequest(
        "api/board",
        boardData,
        localStorage.getItem("jwt")
      ); //url추가 필요

      console.log(response);
      if (response.status == 200) {
        alert("등록이완료되었습니다.");
        this.$router.push("/boarddetail");
      } else {
        alert(response.data);
      }
    },
    uploadImage: function () {
      let form = new FormData();
      let image = this.$refs["image"].files[0];
      form.append("file", image);
      axios
        .post("https://i6b207.p.ssafy.io:8080/api/image", form, {
          header: { "Content-Type": "multipart/form-data" },
        })
        .then(({ data }) => {
          this.image =
            "https://i6b207.p.ssafy.io:8080/api/image/" + data.response;
        })
        .catch((err) => console.log(err));
    },
    clickInputTag: function () {
      this.$refs["image"].click();
    },
    updateTag: function (tags) {
      this.hashTags = tags;
    },
    updateContent: function (content) {
      this.content = content;
    },
  },
};
</script>

<style lang="scss" scoped>
#boardWrite {
  #container {
    padding-bottom: 60px;
    width: 1050px;
    text-align: center;
    margin: 0 auto;
    font-size: 16px;
    .main-title {
      font-weight: bold;
      font-size: 28px;
      text-align: center;
      padding: 50px 0 51px;
    }
    #contents {
      margin-left: 3%;
      margin-right: 3%;
      padding: 0;
      text-align: -webkit-center;
      .row-wrap {
        width: 100%;
        .right-contents {
          .col {
            text-align: left;
            margin-bottom: 5%;
            position: relative;
            padding: 5px 10px;
            border-radius: 4px;
            min-height: 40px;
            margin: 1% auto;
            text-align: left;
            box-sizing: border-box;
            input {
              width: 70%;
              border: 1px solid #ccc;
              border-radius: 3px;
            }
            .date-picker {
              width: fit-content;
            }
          }
        }

        .thumbnail {
          width: 30%;
          margin-right: 10%;
          margin-bottom: 5%;
        }

        .editor-page {
          padding: 0;
        }
      }

      .board-title {
        position: relative;
        width: 100%;
        padding: 5px 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
        min-height: 40px;
        margin: 1% auto;
        text-align: left;
        box-sizing: border-box;
        input {
          width: -webkit-fill-available;

          margin-left: 3%;
          margin-right: 3%;
          text-align: left;
        }
        input::placeholder {
          text-align: center;
          color: #ccc;
        }
      }
      .quill-div {
        width: auto !important;
        margin-bottom: 2%;
      }
      .write-button {
        padding: 2%;
        border: 1px solid #ccc;
        border-radius: 3px;
        margin-left: 1%;
      }
    }
  }
}
div {
  padding: 0;
}
@media #{$tablet} {
  #boardWrite {
    #container {
      width: 100%;
      .main-title {
      }
      #contents {
        .row-wrap {
          .right-contents {
            .col {
              input {
                width: 100%;
              }
              .date-picker {
                width: auto;
              }
            }
          }

          .thumbnail {
          }

          .board-title {
            input {
            }
            input::placeholder {
            }
          }
        }
      }
    }
  }
}
@media #{$mobile} {
  #boardWrite {
    #container {
      width: 100% !important;
      .main-title {
      }
      #contents {
        .row-wrap {
          .right-contents {
            .date-contents {
              display: block !important;
              .date-col {
                float: right;
                .date-picker {
                  margin-left: 0;
                  width: max-content;
                  float: right;
                }
              }
            }
            .col {
              input {
              }
            }
          }

          .thumbnail {
          }

          .board-title {
            input {
            }
            input::placeholder {
            }
          }
        }
      }
    }
  }
}
</style>
