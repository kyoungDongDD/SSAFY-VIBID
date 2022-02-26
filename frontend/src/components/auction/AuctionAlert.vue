<template>
  <div id="auctionAlert">
    <div class="content">
      <p>경매를 시작하면 다른 사람들이 경매장에 입장할 수 있게 됩니다</p>
      <p>영상에 나오면 안되는 부분이 있는지 다시 한 번 확인해주세요</p>
    </div>
    <div class="buttons">
      <button
        class="start"
        @click="$emit('start')">
        시작하기
      </button>
      <button
        class="cancel"
        @click="$emit('cancel')">
        취소하기
      </button>
    </div>
  </div>
</template>

<style lang="scss" scoped>
#auctionAlert {
	max-width: 800px;
	position: absolute;
	display: flex;
	flex-direction: column;
	justify-content: space-evenly;
	align-items: center;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	width: 80%;
	height: 50%;
	border-radius: 10px;
	background-color: black;
	z-index: 100;
	p {
		font-size: 22px;
		text-align: center;
		color: $main-color7;
		@media #{$tablet} {
			font-size: 18px;
		}
		@media #{$mobile} {
			font-size: 12px;
		}
	}
	.start,
	.cancel {
		width: 80px;
		height: 24px;
		text-align: center;
		font-size: 18px;
		line-height: 18px;
		margin: 0 10px;
		color: #fff;
		border: 2px solid #fff;
		border-radius: 30px;
		@media #{$tablet} {
			font-size: 16px;
		}
		@media #{$mobile} {
			font-size: 12px;
		}
		&:hover {
			color: #000;
			border: 2px solid #000;
			background-color: #fff;
		} 
	}
}
</style>