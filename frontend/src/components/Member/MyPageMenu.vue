<template>
  <div class="row">
    <div
      v-for="btn in menuButtons"
      :key="btn.content"
      class="col">
      <button @click="selectMenu(btn.content); getList(btn.requestUrl);">
        <img
          :src="btn.src"
          alt="" />
      </button>
      <p>{{ btn.content }}</p>
    </div>
  </div>
</template>

<script>
import { useStore } from 'vuex';
import updateItemList from '@/functions/updateItemList.js'

export default {
	setup() {
		const menuButtons = [
			{
				src: require('@/assets/bill.png'),
				content: '구매 내역',
				requestUrl: 'api/mypage/buy'
			},
			{
				src: require('@/assets/bag.png'),
				content: '판매 내역',
				requestUrl: 'api/mypage/goods'
			},
			{
				src: require('@/assets/color_heart.png'),
				content: '찜 목록',
				requestUrl: 'api/mypage/wish'
			},
		]
		const store = useStore();
		const selectMenu = (menu) => store.dispatch('selectMenu', menu)
		const getList = async (requestUrl) => {
			await store.dispatch('changePage', 1)
			updateItemList(requestUrl ,store)
		}
		selectMenu('찜 목록')
		return {
			menuButtons,
			selectMenu,
			getList
		}
	}
}
</script>

<style lang="scss" scoped>
	.buttons {
		position: relative;
		text-align: center;
		margin: 20px auto;
		button {
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			width: 70px;
			height: 70px;
			margin: 0 auto;
			border-radius: 50%;
			background-color: #fff;
			overflow: hidden;
			img {
				width: 40px;
			}
		}
		p {
			font-size: 14px;
			color: $main-color5;						
		}
	}
</style>