export const MyPage = {
	state: () => ({
		menu: '찜 목록'
	}),
	mutations: {
		SELECT_MENU: function(state, menu) {
			state.menu = menu
		}
	},
	actions: {
		selectMenu: function ({commit}, menu) {
			commit('SELECT_MENU', menu)
		},	
	}
}