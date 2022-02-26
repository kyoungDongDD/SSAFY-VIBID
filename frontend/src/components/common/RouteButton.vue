<template>
  <button @click="routing(data.params)">
    <slot></slot>
    {{ data.content }}
  </button>
</template>

<script>
import { useRoute, useRouter } from 'vue-router'
import { useStore } from 'vuex'
import updateItemList from '@/functions/updateItemList.js'

export default {
	props: {
		requestUrl: {
			type: String,
			default: ''
		},
		data:  {
			type: Object,
			default: () => {
				return {
					name: 'Main',
					content: '',
					params: {
						tags: '',
						live: false,
						sort: 'DATE',
						order: 'DESC',
						page: 1,
					}
				}
			}
		}
	},
	setup(props) {
		const router = useRouter()
		const route = useRoute()
		const store = useStore()
		const dispatching = async (params) => {
			await store.dispatch('changeTags', params.tags)
			await store.dispatch('changeLive', params.live)
			await store.dispatch('changeSort', params.sort)
			await store.dispatch('changeOrder', params.order)
			await store.dispatch('changePage', params.page)
		}
		const routing = async (params) => {
			if(props.data.content === '로그아웃') {
				await store.dispatch('logout')
				localStorage.removeItem('jwt')
			}
			if(route.name === 'SearchPage' && props.data.name === 'SearchPage') {
				dispatching({
					tags: '',
					live: false,
					sort: 'DATE',
					order: 'DESC',
					page: 1,
				})
				updateItemList('api/board/goods', store)
			}
			else {
				dispatching(params)
				router.push({ name: props.data.name, params: {requestUrl: props.requestUrl}})
			}
		}

		return { routing }
	}
}
</script>