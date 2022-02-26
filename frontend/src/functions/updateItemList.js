import getItemList from './getItemList'

export default async function (url, store) {
	await store.dispatch('updateItemList', null)
	await store.dispatch('changeLoading', true)
	await getItemList(url, {
		tags: store.state.Search.tags,
		sort: store.state.Search.sort,
		order: store.state.Search.order,
		page: store.state.Search.page
	}, localStorage.getItem('jwt'))
	.then(async data => {
		await store.dispatch('changeLoading', null)
		await store.dispatch('updateItemList', data.response)
		console.log(store.state.Search.itemList)

		return data.response.length
	})
}