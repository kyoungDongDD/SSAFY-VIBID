<template>
  <div id="chatWords">
    <p class="nickname">
      <span>
        {{ data.nickname }}
      </span>
      <span>
        {{ computedTime }}
      </span>
    </p>
    <p class="content">
      {{ data.content }}
    </p>
  </div>
</template>

<script>
import { computed } from '@vue/runtime-core'
export default {
	props: {
		data: {
			type: Object,
			default: () => {
				return {
					nickname: {
						type: 'String',
						default: '닉네임'
					},
					content: {
						type: 'String',
						default: '제곧내'
					},
					time: {
						type: 'String',
						default: '오늘'
					},
				}
			}
		}
	},
	setup (props) {
		const computedTime = computed(() => {
			const tmp = props.data.time.slice(11,-1).split(':')
			return `${tmp[0] % 12}:${tmp[1]}:${tmp[2].slice(0, 2)} ${tmp[0] > 12 ? 'PM' : 'AM'}`
		})
		return {computedTime}
	}
}
</script>

<style lang="scss" scoped>
#chatWords {
	width: 100%;
	margin: 0 0 15px;
	padding: 5px 20px;
	font-size : 14px;
	box-sizing: border-box;

	p {
		margin: 0;
		word-wrap: break-word;
	}
	.nickname {
		display: flex;
		justify-content: space-between;
		margin-bottom: 5px;
		color: $main-color8;
	}
	.content {
    color: rgba(#fff, .9);
		padding-left: 20px;
	}
}
</style>