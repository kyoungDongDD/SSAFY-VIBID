<template>
  <div id="auction">
    <div
      :id="[isVertical ? 'vertical' : 'horizontal']"
      class="auction-inner">
      <div
        ref="contents"
        class="contents"
        :class="{'row':!isVertical}">
        <div
          class="video content"
          :class="{'col-9':!isVertical}">
          <broad-cast
            :is-vertical="isVertical"
            :person="person" />
        </div>
        <div
          class="content"
          :style="{height: chatHeight}"
          :class="{'col-3':!isVertical}">
          <chat class="chat" />
          <chat-input class="chat-input" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BroadCast from '@/components/auction/BroadCast.vue'
import Chat from '@/components/auction/Chat.vue'
import ChatInput from '@/components/auction/ChatInput.vue'
import { useStore } from 'vuex'
import { computed } from '@vue/runtime-core'

export default {
  components: {
    BroadCast,
    Chat,
    ChatInput
  },
  setup () {
    const store = useStore();
    const person = computed(()=> store.state.Search.tags)
    const isVertical = computed(() => {
      if(store.state.Window.windowWidth > store.state.Window.windowHeight) return false;
      else return true;
    })

    return {
      isVertical,
      person
    }
  },
  data () {
		return {
			chatHeight: '',
			throttle: 0
		}
	},
	mounted() {
		this.setChatHeight();
		window.addEventListener('resize', ()=>this.setChatHeight());
	},
  beforeUnMount() {
    window.removeEventListener('resize', ()=>{});
  },
	methods: {
		setChatHeight () {
			if(this.throttle) return;

			this.throttle = setTimeout(() => {
        if(this.isVertical){
          this.chatHeight = `${this.$refs.contents.clientHeight - this.$refs.contents.childNodes[0].clientHeight}px`
        }
        else {
          this.chatHeight = '100%'
        }
				this.throttle = null
			}, 200);
		}
	}
}
</script>

<style lang="scss" scoped>
#auction {
  height: 100%;
  background-color: rgba($color: #000000, $alpha: .85);
  #horizontal{
    width: 85vw;
    height: 100%;
    margin: 0 auto;
    @media screen and (max-width: 1600px) {
      width: 100vw;
      height: 100%;
    }
    .contents {
      height: 100%;
      .content {
        position: relative;
        height: 100%;
        padding: 0;
      }
    }
    .chat {
      height: 80%;
    }
    .chat-input {
      height: 20%;
    }
  }
  #vertical {
    height: 100%;
    padding-bottom: 20px;
    @media #{$mobile} {
      padding-bottom: 70px;
    }
    .contents{
      height: 100%;
      display: flex;
      flex-direction: column;
      height: 100%;
      .content{
        position: relative;
        display: flex;
        flex-direction: column;
      }
    }
  }
}
</style>