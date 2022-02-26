<template>
  <div id="chatInput">
    <textarea
      :value="TextValue"
      type="text"
      @keypress.enter="send"
      @input="userInput"></textarea>
    <button @click="send">
      <img
        src="@/assets/send.png"
        alt="" />
    </button>
  </div>
</template>

<script>
import { ref } from '@vue/reactivity'
import { useStore } from 'vuex'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  emits: ['addLog'],
  setup() {
    const store = useStore()
    const TextValue = ref("")
    const userInput = (event) => TextValue.value =  event.target.value
    const chating = (message) => {
      // if(TextValue.value === "") return;
      store.dispatch('addLog', message)
      TextValue.value = ""
    }
    return {TextValue, userInput, chating}
  },
  mounted() {
    this.connect()
  },
  methods: {
    send: function () {
      if(this.stompClient && this.stompClient.connected) {
        const msg = {
          id: "2",
          content: this.TextValue
        }
        if(msg.content !== "")
          this.stompClient.send("/ws/chat/10", JSON.stringify(msg),)
      }
    },
    connect: function () {
      let socket = new SockJS("https://i6b207.p.ssafy.io:8080/vibid")
      this.stompClient = Stomp.over(socket)
      this.stompClient.connect(
        {},
        () => {
          this.connected = true;
          this.stompClient.subscribe("/sub/chat/10", tick => {
            const receivedMsg = JSON.parse(tick.body)
            this.chating(receivedMsg.response)
          })
        },
      )
    }
  }
}
</script>

<style lang="scss" scoped>
#chatInput {
  padding: 10px;
  textarea {
    all:unset;
    width: 100%;
    height: 70%;
    margin-bottom: 10px;
    padding: 10px;
    font-size: 14px;
    color: rgba(#fff, .9);
    word-wrap: break-word;
    box-sizing: border-box;
    border-radius: 10px;
    background-color: rgba($color: #000000, $alpha: .9);
    &::-webkit-scrollbar {
    display: none;
}
  }
  button {
    display: flex;
    justify-content: center;
    width: 100%;
    height: 25%;
    padding: 5px;
    box-sizing: border-box;
    border-radius: 10px;
    background-color: $main-color1;
    img {
      max-width: 30px;
    }
  }
}
</style>