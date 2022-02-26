<template>
  <div id="searchBar">
    <div class="container">
      <div class="row my-5 mx-1">
        <div class="search-bar">
          <div class="col-2">
            <p>검색 조건</p>
          </div>
          <div class="col d-flex justify-content-center align-items-center">
            <user-input />
          </div>
          <div class="col-2 d-flex justify-content-center align-items-center">
            <button @click="searching">
              검색하기
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import UserInput from '@/components/common/UserInput.vue'
import { useStore } from 'vuex'
import updateItemList from '@/functions/updateItemList.js'

export default {
	components: {
		UserInput,
	},
	setup() {
		const store = useStore();
    const searching = () => updateItemList('api/board/goods' ,store) 

		return {
			searching
		}
	}
}
</script>

<style lang="scss" scoped>
	#searchBar {
		.search-bar {
			display: flex;
			height: 100px;
			@media #{$mobile} {
				height: 60px;
			}
			border-top: 1px solid #000;
			border-bottom: 1px solid #000;
			p {
				width: 100%;
				float: left;
				font-size: 16px;
				line-height: 100px;
				@media #{$mobile} {
					font-size: 12px;
					line-height: 60px;
				}
				text-align: center;
			}
			input {
				width: 90%;
				height: 47px;
				margin-right: 16px;
				padding-left: 20px;
				@media #{$mobile} {
					height: 30px;
					font-size: 12px;
					margin-right: 12px;
					padding-left: 16px;
				}
				border: 1px solid #ccc;
				border-radius: 3px;
				font-size: 14px;
				color: #333;
				letter-spacing: -1px;
			}
			button {
				width: 100%;
				height: 47px;
				text-align: center;
				font-size: 16px;
				@media #{$mobile} {
					height: 30px;
					font-size: 12px;
				}
				color: white;
				background-color: $main-color1;
			}
		}
	}
</style>