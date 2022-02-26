export const Chating = {
	state: () => ({
		chatLog: [],
		scrollHeight: undefined,
	}),
	mutations: {
		ADD_LOG: function(state, chat) {
			state.chatLog.push(chat)
			console.log('added')
		},
		CHANGE_SCROLL_HEIGHT: function(state, height) {
			state.scrollHeight = height
		}
	},
	actions: {
		addLog: function ({commit}, chat) {
			commit('ADD_LOG', chat)
		},
		changeScrollHeight: function ({commit}, height) {
			commit('CHANGE_SCROLL_HEIGHT', height)
		}
	}
}