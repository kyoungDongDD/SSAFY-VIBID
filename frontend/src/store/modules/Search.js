export const Search = {
  state: () => ({
		tags: '',
    live: false,
		sort: 'DATE',
		order: 'DESC',
		page: 1,
    itemList: null,
    isLoading: true
	}),
  mutations: {
    CHANGE_TAGS: (state, tags) => state.tags = tags,
    CHANGE_LIVE: (state, live) => state.live = live,
    CHANGE_SORT: (state, sort) => state.sort = sort,
    CHANGE_ORDER: (state, order) => state.order = order,
    CHANGE_PAGE: (state, page) => state.page = page,
    CHANGE_LOADING: (state, isLoading) => state.isLoading = isLoading,   
    UPDATE_ITEM_LIST: (state, itemList) => state.itemList = itemList,   
  },
  actions: {
    changeTags: ({commit}, tags) => {commit('CHANGE_TAGS', tags)},
    changeLive: ({commit}, live) => {commit('CHANGE_LIVE', live)},
    changeSort: ({commit}, sort) => commit('CHANGE_SORT', sort),
    changeOrder: ({commit}, order) => commit('CHANGE_ORDER', order),
    changePage: ({commit}, page) => commit('CHANGE_PAGE', page),
    changeLoading: ({commit}, isLoading) => commit('CHANGE_LOADING', isLoading),
    updateItemList: ({commit}, itemList) => commit('UPDATE_ITEM_LIST', itemList),
  }
}