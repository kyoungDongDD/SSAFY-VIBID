export const Window = {
  state: () => ({
		windowWidth: window.innerWidth,
		windowHeight: window.innerHeight,
	}),
  mutations: {
    RESIZE_WIDTH: function (state, size) {
      state.windowWidth = size
    },
    RESIZE_HEIGHT: function (state, size) {
      state.windowHeight = size
    }
  },
  actions: {
    resizeWidth: function ({commit}, size) {
      commit('RESIZE_WIDTH', size)
    },
    resizeHeight: function ({commit}, size) {
      commit('RESIZE_HEIGHT', size)
    }
  }
}