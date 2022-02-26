<template>
  <div id="list">
    <div class="container">
      <h2
        class="heading">
        리스트
      </h2>
      <loader v-if="$store.state.Search.isLoading" />
      <div
        class="row">
        <no-list v-if="isEmpty" />
        <div
          v-for="item in $store.state.Search.itemList"
          :key="item.id"
          class="p-0"
          :class="[numPerRow === 1 ? 'col-12' : 'col-4']">
          <div
            ref="card"
            class="card m-2">
            <card
              :id="direction === 'x' ? 'CardX' : 'CardY'"
              :img-height="imgHeight"
              :item="item" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useStore } from 'vuex';
import updateItemList from '@/functions/updateItemList.js'
import { computed, onMounted } from '@vue/runtime-core';
import { useRoute } from 'vue-router';

import Card from '@/components/common/Card.vue'
import NoList from '@/components/common/NoList.vue'
import Loader from '@/components/common/Loader.vue'

export default {
	components: {
		Card,
		NoList,
		Loader,
	},
  props: {
    numPerRow: {
      type: Number,
      default: 1
    },
    direction: {
      type: String,
      default: 'y'
    }
  },
	setup() {
		const store = useStore();
		const route = useRoute();
    const isEmpty = computed(() => JSON.stringify(store.state.Search.itemList).length > 2
                                   ? false
                                   : true)
		onMounted(async () => await updateItemList(route.params.requestUrl, store))
		return { isEmpty }
	}
}
</script>

<style lisEmptyng="scss" scoped>
</style>