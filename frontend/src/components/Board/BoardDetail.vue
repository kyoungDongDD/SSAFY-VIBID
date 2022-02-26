<template>
  <div id="boardDetail">
    <div id="container">
      <p class="main-title">
        상품상세
      </p>
      <div id="contents">
        <form @submit.prevent="registForm">
          <div class="row row-wrap">
            <div class="col col-8 thumbnail w-full h-full flex items-center">
              <img
                :src="image"
                alt="image" />
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
                  {{ boardData.startingPrice }} 원
                </div>
              </div>
              <div class="row d-flex">
                <div class="col">
                  호가단위
                </div>
                <div class="col col-6">
                  {{ boardData.bidding }} 원
                </div>
              </div>
              <div class="row date-contents">
                <div class="col col">
                  경매 시작 시간
                </div>
                <div class="col col-6 date-col">
                  <span>
                  </span><br />
                  <span>
                  </span>
                </div>
              </div>
            </div>
          </div>
          <div class="col col-12 hash-tag">
            <span
              v-for="(tag, idx) in hashTags"
              :key="`${idx}-${tag.value}`">
              # {{ tag.value }}
            </span>
          </div>
          <div class="col col-12 board-title">
            {{ title }}
          </div>

          <div class="row row-wrap quill-div">
            <QuillEditor
              :quill-content="content"
              :check-readonly="true"
              :check-tool="false"
              @update-content="updateContent" />
          </div>
        </form>
      </div>
    </div>
  </div>
</template>
<script>
import QuillEditor from "../common/QuillEditor.vue";
import { getRequest } from "@/api/requests.js";
import { useRoute } from 'vue-router';
import { ref } from '@vue/reactivity';
import { computed, onMounted } from '@vue/runtime-core';
export default {
  components: { QuillEditor },
  setup() {
    const route = useRoute();
    const boardId = route.params.boardId
    const boardData = ref({})
    let bidStartTime = undefined
    getRequest('/api/board/' + boardId, '', localStorage.getItem('jwt'))
    .then(res => { boardData.value = res.data.response; console.log(boardData.value) })

    // onMounted(() => {
    //   bidStartTime = computed(() => {
    //     let tmp = boardData.value.bidStartTime.split('T')
    //     const date = tmp[0].split('-')
    //     const time = tmp[1].split(':')
    //     return [`${date[0]}년 ${date[1]}월 ${date[2]}일1`, `${time > 12 ? '오후' : '오전'} ${time[0]}시 ${time[1]}분`]
    //   })
    // })
    return {
      boardData,
      bidStartTime
    }
  }
};
</script>

<style lang="scss" scoped>
#boardDetail {
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
      .quill-div {
        width: auto;
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
      .write-button {
        padding: 2%;
        border: 1px solid #ccc;
        border-radius: 3px;
        margin-left: 1%;
      }
      .hash-tag {
        text-align: left;
        margin-bottom: 2%;
        span {
          margin-left: 1%;
          margin-right: 1%;
          background: #983e72;
          color: #fff;
          padding: 0.8% 2% 0.8% 2%;
          border-radius: 50px;
        }
      }
    }
  }
}
div {
  padding: 0;
}
@media #{$tablet} {
  #boardDetail {
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
  #boardDetail {
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
