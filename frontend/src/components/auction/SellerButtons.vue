<template>
  <div id="sellerButtons">
    <div
      v-if="!isPublished"
      class="camera-off"
      :class="{'vertical':isVertical}">
      <button
        class="button"
        @click="$emit('cameraOn')">
        카메라 켜기
      </button>
    </div>
    <div
      v-else
      :class="{'vertical':isVertical}">
      <button
        v-if="!isStart"	
        class="auction-start button"	
        @click="$emit('auctionStart')">
        경매 시작하기
      </button>
      <button
        v-if="isStart"
        class="mic-on button">
        {{ audioActive ? '마이크 켜기' : '마이크 끄기 ' }}
      </button>
      <button
        v-if="isStart"
        class="camera-off button"
        @click="$emit('cameraOff');">
        카메라 끄기
      </button>
      <button
        v-if="isStart"
        class="auction-end button"
        @click="$emit('leaveSession')">
        X
      </button>
    </div>
  </div>
</template>

<script>
export default {
	props: {
		isPublished: {
			type: Boolean,
			default: false
		},
		isStart: {
			type: Boolean,
			default: false
		},
		isVertical: {
			type: Boolean,
			default: false
		},
		audioActive: {
			type: Boolean,
			default: false
		},
	}
}
</script>

<style lang="scss" scoped>
#sellerButtons {
	width: 100%;
	.button {
		height: 60px;
		margin: 0 20px;
		padding: 0 18px;
		font-size: 30px;
		text-align: center;
		color: $main-color1;
		border: 4px solid $main-color1;
		border-radius: 30px 30px 30px 30px;
		box-sizing: border-box;

		&:hover {
			color: #fff;
			border: 4px solid #fff;
			background-color: $main-color1;
		}
	}
	.auction-start,
	.auction-end {
		color: $main-color7;
		border: 4px solid $main-color7;	
		&:hover {
			color: #fff;
			border: 4px solid #fff;
			background-color: $main-color7;
		}
	}
	@media #{$mobile} {
		.vertical {
			&>button {
				height: 40px;
				margin: 0 10px;
				padding: 0 10px;
				font-size: 20px;
			}
		}
	}
}
</style>