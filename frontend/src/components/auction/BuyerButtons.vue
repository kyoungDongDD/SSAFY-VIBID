<template>
  <div id="buyerButtons">
    <div
      :class="{'vertical':isVertical}"
      class="camera-on">
      <button
        class="auction-end button"
        @click="$emit('joinSession')">
        입찰하기
      </button>
    </div>
  </div>
</template>

<script>
export default {
	props: {
		isVertical: {
			type: Boolean,
			default: false
		},
	}
}
</script>

<style lang="scss" scoped>
#buyerButtons {
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