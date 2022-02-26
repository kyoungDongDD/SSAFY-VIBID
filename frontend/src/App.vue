<template>
  <div id="app">
    <Header />
    <div id="view">
      <router-view />
    </div>
    <user-menu v-if="$store.state.Window.windowWidth < 769" />
  </div>
</template>

<script>
import Header from '@/components/header-footer/Header.vue'
import UserMenu from '@/components/header-footer/UserMenu.vue'

import { onMounted, onBeforeUnmount } from 'vue'
import { useStore } from 'vuex'
export default {
  components: {
    Header,
    UserMenu
  },
  setup() {
    const store = useStore();
    const handleResize = () => { store.dispatch('resizeWidth', window.innerWidth); store.dispatch('resizeHeight', window.innerHeight);}
    onMounted(() => {
      window.addEventListener('resize', handleResize)
      if(localStorage.getItem('jwt')) store.dispatch('login')
      else store.dispatch('logout')
    })
    onBeforeUnmount(() => window.removeEventListener('resize', handleResize))
    return {}
  },
}
</script>

<style lang="scss" scoped>
#view {
  height: 100vh;
  padding-top: 157px;
  @media #{$tablet} {
    padding-top: 144px;    
  }
  @media #{$mobile} {
    padding-top: 50px;    
  }
}
</style>