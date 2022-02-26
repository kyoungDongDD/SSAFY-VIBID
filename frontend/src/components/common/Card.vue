<template>
  <button
    :id="idProperty"
    @click="clickItem">
    <h3 class="heading">
      {{ item.title }}
    </h3>
    <div class="item-image">
      <div class="image">
        <img
          :src="imgSrc"
          alt="" />
      </div>
      <button
        v-if="idProperty === 'CardY'"
        class="like-button"
        @click="clickLikeButton">
        <img
          v-if="isLiked"
          src="@/assets/on_like.png"
          alt="" />
        <img
          v-else
          src="@/assets/off_like.png"
          alt="" />
      </button>
    </div>
    <div class="contents">
      <p class="title">
        {{ item.title }}
      </p>
      <p class="price">
        {{ price }} 원
      </p>
      <p class="author">
        {{ item.authorNickname }}
      </p>
    </div>
  </button>
</template>

<script>
import { ref } from '@vue/reactivity';
import { computed } from '@vue/runtime-core';
import { useRouter } from 'vue-router';

export default {
	props: {
		idProperty: {
			type: String,
			default: 'CardY'
		},
		item: {
			type: Object,
			default: function () {
				return {
					authorNickname: "알 수 없음",
					id: 1,
					registTime: "0000-00-00 00:00:00",
					startingPrice: 30000,
					thumbnailImageUrl: "",
					title: "제목 없음",
					viewCount: 0,
					wish: false,
					wishCount: 0
				}
			}
		}
	},
	setup(props) {
		const isLiked = ref(false);
		const clickLikeButton = () => { 
			isLiked.value = !isLiked.value 
		}
		const imgSrc = computed(() => `https://i6b207.p.ssafy.io:8080/api/image/${props.item.thumbnailImageUrl}`)
		const price = computed(() => props.item.startingPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','))

		const router = useRouter();
		const clickItem = () => {
			router.push({name: 'BoardDetail', params: {boardId: props.item.id}})
		}
		return{
			isLiked,
			clickLikeButton,
			imgSrc,
			price,
			clickItem
		}
	},
}
</script>

<style lang="scss" scoped>
$contents-color: rgb(51, 51, 51);
#CardX {
	display: flex;
	align-items: center;
	padding: 10px 0;
	cursor: pointer;
	.item-image {
		position: relative;
		width: 25%;
		.image {
			position: relative;
			width: 100%;
			background-color: $main-color9;
			&:before {
				content: "";
				display: block;
				padding-top: 100%;
			}
			img {
				width: 100%;
				position: absolute;
				top: 50%;
				left: 50%;
				transform: translate(-50%, -50%);
			}
		}
	}
	.contents {
		width: 70%;
		padding-left: 30px;
		color: $contents-color;
		.title {
			height: 70px;
			font-size: 24px;
			margin-bottom: 25px;
			@media #{$tablet} {
				height: 48px;
				font-size: 16px;
				margin-bottom: 14px;					
			}
			line-height: 1.45;
			font-weight: 400;
			overflow: hidden;
			text-overflow: ellipsis;
			text-align: left;
			display: -webkit-box;
			-webkit-line-clamp: 2;
			-webkit-box-orient: vertical;
			letter-spacing: normal;
			word-break: break-all;
			overflow-wrap: break-word;
		}
		.price {
			font-size: 30px;
			@media #{$tablet} {
				font-size: 18px;
			}
			text-align: left;
		}
		.author {
			font-size: 16px;
			@media #{$tablet} {
				font-size: 12px;
			}
			opacity: .6;
			text-align: left;
		}
	}
}
#CardY {
	cursor: pointer;
	.item-image {
		position: relative;
		width: 100%;
		margin-bottom: 20px;
		overflow: hidden;
		.image {
			position: relative;
			width: 100%;
			background-color: $main-color9;
			&:before {
				content: "";
				display: block;
				padding-top: 100%;
			}
			img {
				width: 100%;
				position: absolute;
				top: 50%;
				left: 50%;
				transform: translate(-50%, -50%);
			}
			&:hover {
				transform: scale(1.05);
			}
		}
		button:active {
			transform: scale(.9);
		}
		.like-button{
			position: absolute;
			display: flex;
			justify-content: center;
			align-items: center;
			width: 20%;
			height: 20%;
			bottom: 5%;
			right: 5%;
			border-radius: 50%;
			overflow: hidden;
			box-shadow: 2px 1px 5px gray;
			img {
				width: 100%;
			}
		}
	}
	.contents {
		color: $contents-color;
		margin-bottom: 8px;
		padding: 0 5px;
		.title {
			height: 55px;
			font-size: 20px;
			margin-bottom: 16px;
			@media #{$mobile} {
				height: 48px;
				font-size: 16px;
				margin-bottom: 14px;					
			}
			line-height: 1.45;
			font-weight: 400;
			overflow: hidden;
			text-overflow: ellipsis;
			text-align: left;
			display: -webkit-box;
			-webkit-line-clamp: 2;
			-webkit-box-orient: vertical;
			letter-spacing: normal;
			word-break: break-all;
			overflow-wrap: break-word;
		}
		.price {
			font-size: 25px;
			@media #{$mobile} {
				font-size: 18px;
			}
			text-align: left;
		}
		.author {
			font-size: 14px;
			@media #{$mobile} {
				font-size: 12px;
			}
			opacity: .6;
			text-align: left;
		}
	}
}
</style>