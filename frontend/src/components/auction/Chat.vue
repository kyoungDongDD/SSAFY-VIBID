<template>
  <div id="chat">
    <div class="chat-log">
      <chat-words
        v-for="data in $store.state.Chating.chatLog"
        :key="data"
        class="words"
        :data="data" />
    </div>
    <button
      class="go-bottom">
      <!-- <span>
        아래에 더 많은 채팅이 있어요
      </span> -->
    </button>
  </div>
</template>

<script>

import ChatWords from '@/components/auction/ChatWords.vue'
import { onMounted, ref, watch } from '@vue/runtime-core'
import { useStore } from 'vuex'

export default {
	components: {
    ChatWords,
  },
  setup() {
    const store = useStore()
    const container = ref(undefined)
    // const isBottom = ref(true)
    // let throttle = null;
    const scrollToBottom = () => container.value.scrollTop = store.state.Chating.scrollHeight
    onMounted(() => {
      container.value = document.querySelector('#chat')
      // container.value.addEventListener('scroll', () => {
      //   if(throttle) return;

      //   throttle = setTimeout(() => {
      //     const tmp = container.value.scrollHeight - container.value.scrollTop
      //     console.log('calc '+ tmp)
      //     console.log('scrolltmp '+ container.value.scrollTop)
      //     if(container.value.scrollTop < tmp) isBottom.value = false
      //     else isBottom.value = true
      //     console.log(isBottom.value)
      //     throttle = null
      //   }, 200)
      // })
      scrollToBottom()
    })
    watch(store.state.Chating.chatLog, async () => {
      await store.dispatch('changeScrollHeight', container.value.scrollHeight)
      scrollToBottom()
    })

    return { scrollToBottom }
  },
}
</script>

<style lang="scss" scoped>
#chat {
  height: 100%;
  overflow-y: scroll;
  overflow-x: none;
  &::-webkit-scrollbar {
    width: 5px;
  }
  &::-webkit-scrollbar-thumb {
    background-color: $main-color1;    
    border-radius: 5px;
  }
  &::-webkit-scrollbar-track {
    border-radius: 5px;
    box-shadow: inset 0px 0px 5px white;
  }
  // .go-bottom {
  //   position: absolute;
  //   bottom: 20%;
  //   width: 100%;
  //   text-align: center;
  //   opacity: 0;
  //   transition: opacity .3s;
  //   span {
  //     padding: 2px 7px;
  //     color: rgba($color: #fff, $alpha: .9);
  //     font-size: 14px;
  //     background-color: $main-color3;
  //   }
  // }
  // .active {
  //   opacity: 1;
  // }
}
</style>