<template>
  <div id="filter">
    <div class="container">
      <div
        v-for="btn in menuButtons"
        :key="btn.id">
        <input
          :id="btn.id"
          v-model="picked"          
          type="radio"
          :value="btn.value" />
        <label
          :for="btn.id"
          :class="{ 'active': picked === btn.value }">
          {{ btn.id }}</label>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from '@vue/reactivity'
import { useStore } from 'vuex';
import updateItemList from '@/functions/updateItemList.js'
import { watch } from '@vue/runtime-core';

export default {
	components: {
	},
	setup() {
		const picked = ref('latest');
    const store = useStore();
    const menuButtons = [
      {
        id: "최신순",
        value: "latest",
        callback: () => getList('registDate', true)
      },
      {
        id: "관심순",
        value: "popular",
        callback: () => getList('wishCount', true)
      },
      {
        id: "낮은 시작가순",
        value: "lowest",
        callback: () => getList('price', false)
      },
      {
        id: "높은 시작가순",
        value: "highest",
        callback: () => getList('price', true)
      },
    ]
    watch(() => picked.value, (n, o) => {
      console.log(n)
      switch (n) {
        case 'latest':
          getList('registDate', true)
          break;
        case 'popular':
          getList('wishCount', true)
          break;
        case 'lowest':
          getList('price', false)
          break;
        case 'registhighestDate':
          getList('price', true)
          break;
      }
    })
    const getList = (sort, asc) => {
			store.dispatch('changeSort', sort);
			store.dispatch('changeOrder', asc);
			updateItemList(route.params.url ,store)
		}
		return {picked, menuButtons}
	}
}
</script>

<style lang="scss" scoped>
	#filter {
		font-size: 14px;
    font-weight: 500;
		margin-bottom: 10px;
		margin-left: 10px;
    .container {
      display: flex;
      >div::after {
				content: "";
				float: right;
				width: 1px;
				height: 14px;
				margin: 4px 5px 0;
				background-color: #d8d8d8;
			}
      >div:last-child::after {
				display: none;
			}
    }
		.active {
			color: $main-color6;
		}
	}
</style>