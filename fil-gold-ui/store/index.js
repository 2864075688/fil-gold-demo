import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = () => ({
	deviceInfo: null, // 设置信息
	windowHeight: null, // 屏幕总高度
	account: "",
	publicAddr: "0x*****",
	web3: null,
	userInfo: {}
})

const getters = {

}

const actions = {

}

const mutations = {
	/**
	 * 获取手机设备信息，可用高度等
	 */
	getDeviceInfo(state) {
		uni.getSystemInfo({
			success: (response) => {
				state.deviceInfo = response
				state.windowHeight = response.screenHeight
				console.debug('总高度：' + state.windowHeight)
			}
		})
	},
	SET_ACCOUNT(state, account) {
		state.account = account;
	},
	SET_WEB3(state, web3){
		state.web3 = web3;
	},
	SET_USER_INFO(state, info){
		state.userInfo = info;
	}
}

const store = new Vuex.Store({
	state,
	getters,
	actions,
	mutations
})

export default store
