export const LoginData = {
  state: () => ({
		isLogin: false,
		userEmail: "",
		profileImageUrl: "",
	}),
	mutations: {
		LOGIN: (state) => { state.isLogin = true; console.log(state.isLogin); },
		LOGOUT: (state) => { state.isLogin = false },
		CHANGE_USER_EMAIL: (state, userEmail) => { state.userEmail = userEmail },
		CHANGE_IMAGE_URL: (state, profileImageUrl) => { state.profileImageUrl = profileImageUrl }
	},
	actions: {
		login: ({commit}) => {commit('LOGIN')},
		logout: ({commit}) => {commit('LOGOUT')},
		changeUserEmail: ({commit}, userEmail) => {commit('CHANGE_USER_EMAIL', userEmail)},
		changeImageUrl: ({commit}, profileImageUrl) => {commit('CHANGE_IMAGE_URL', profileImageUrl)}
	}
}