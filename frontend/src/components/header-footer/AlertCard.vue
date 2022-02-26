<template>
  <div
    id="alertCard"    
    @click="check">
    <div
      class="top"
      :class="{ checked: isChecked }">
      <p class="content">
        {{ data.content }}
      </p>
    </div>
    <div
      class="bottom"
      :class="{ checked: isChecked }">
      <p class="time">
        {{ data.time }}
      </p>
      <button>
        삭제
      </button>
    </div>
  </div>
</template>

<script>
import { ref } from '@vue/reactivity'
export default {
	props: {
		data: {
			type: Object,
			default() {
				return {
					content: '',
					time: '0000 00 00'
				}
			}
		},
	},
	setup() {
		const isChecked = ref(false);
		const check = () => isChecked.value = true
		return {
			isChecked,
			check
		}
	},
}
</script>

<style lang="scss" scoped>
	#alertCard {
		width: 100%;
		background-color: $main-color9;
		margin-bottom: 5px;
		padding: 5px 10px;
		font-size: 14px;
		cursor: pointer;
		.bottom {
			display: flex;
			justify-content: space-between;
		}
		.time {
			margin-top: 5px;
			font-size: 12px;
			color: gray;
		}
	}
	button {
		opacity: 1 !important;
	}
	.checked {
		* {
			opacity: .5;
		}
	}
</style>