<template>
  <div :id="device">
    <div id="eventBanner"></div>
    <div id="contents">
      <h1 class="heading">
        메인 화면
      </h1>
      <div 
        v-for="item in swiperList"
        :key="item.content"
        class="content">
        <h2 class="heading">
          {{ item.content }}
        </h2>
        <p class="main-catagory">
          {{ item.content }}
        </p>
        <item-slide
          v-if="item.list.length !== 0 && !isLoading"
          :items="item.list"
          :el="item.element"
          :option="swiperOption(item.element)" />
        <empty-list v-else-if="!isLoading" />
        <loader
          v-if="isLoading"
          :height="'400px'"
          :size="'40px'" />
      </div>
    </div>
  </div>
</template>

<script>
import ItemSlide from '@/components/common/ItemSlide.vue'
import EmptyList from '@/components/main/EmptyList.vue'
import { computed, ref } from '@vue/runtime-core'
import { useStore } from 'vuex'
import getItemList from '@/functions/getItemList'
import Loader from '@/components/common/Loader.vue'
export default {
  components: {
    ItemSlide,
    EmptyList,
    Loader
  },
  setup () {
    const isLoading = ref(true);
    const swiperList = [
      {
        element: "vivid-pick", 
        content: "VIBID's PICK", 
        list: []
      },
      {
        element: "popular", 
        content: "관심 집중", 
        list: []
      },
      {
        element: "live", 
        content: "LIVE", 
        list: []
      },
    ]
    
		getItemList('api/board/main')
    .then(data => {
      swiperList[0].list = data.response.pick
      swiperList[1].list = data.response.popularity
      swiperList[2].list = data.response.live
    })
    .then(() => isLoading.value = false)
    const store = useStore();
    const device = computed(() => {
      const size = store.state.Window.windowWidth < 769 ? 'mobile' : 'normal'
      return size
    });
    const swiperOption = (el) => {
      if(store.state.Window.windowWidth < 769){
        return {
            slidesPerView: 3,
            spaceBetween:30,
            freeMode:true,
            slidesPerGroup:3,
            navigation:false
          }
      }
      else {
        return {
          slidesPerView: 4,
          spaceBetween:10,
          freeMode:false,
          slidesPerGroup:4,
          navigation:{
            nextEl: `.${el}-next`,
            prevEl: `.${el}-prev`,
          }
        }
      }
    }
    

    return {
      swiperOption,
      swiperList,
      device,
      isLoading
    }
  }
}
</script>

<style lang="scss" scoped>
#normal {
  #eventBanner {
    width: 100vw;
    height: 300px;
    max-width: 1920px;
    margin: 0 auto 100px;
    background: rosybrown;
  }
  #contents {
    width: 1050px;
    @media #{$tablet} {
      width: 100vw;
    }
    margin: 0 auto;
    .content {
      position: relative;
      margin-bottom: 300px;
      @media #{$tablet} {
        padding : 0 10px;
      }
    }
    .main-catagory {
      font-size: 30px;
      text-align: center;
      margin-bottom: 20px;
    }
  }
}
#mobile {
  width: 100vw;
  height: 1500px;
  #eventBanner {
    width: 100vw;
    height: 300px;
    background-color: orange;
  }
  #contents {
    padding: 0 5px;
    .content {
      margin-bottom: 80px;

      .main-catagory {
        text-align: center;
        font-size: 24px;
        margin: 10px 0;
      }
    }
  }
}
</style>