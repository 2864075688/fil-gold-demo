<script>
	import {
		mapMutations,
		mapState
	} from 'vuex'
	import constants from "utils/constants.js";
	import Web3 from "web3"
	import API from "./api/api.js"
	import Base64 from "utils/base64.js"
	import toBuy from "utils/toBuy.js"

	export default {
		onLaunch: function(e) {
			this.getDeviceInfo();
			let {parentCode} = e.query;
			if(parentCode !== undefined) {
				parentCode = Base64.decode(parentCode);
			}
			this.init(parentCode);
		},
		onShow: function() {

		},
		onHide: function() {

		},
		methods: {
			...mapMutations({
				getDeviceInfo: 'getDeviceInfo',
				setAccount: "SET_ACCOUNT",
				setWeb3: "SET_WEB3",
				setUserInfo: "SET_USER_INFO"
			}),
			/**
			 * 初始化
			 * @param {String} parentCode 父级addr
			 */
			async init(parentCode) {
				if (window.ethereum) {
					try {
						await window.ethereum.enable();
						this.setWeb3(new Web3(window.ethereum))
						let chainId = await this.web3.eth.getChainId()
						if(constants.chainID !== chainId) {
							uni.showModal({
								title: "不支持的网络",
								content: "仅支持HECO，请切换至HECO网络~",
								showCancel: false,
								success: (e) => {
									this.init(parentCode);
								}
							})
							return;
						}
						let accounts = await this.web3.eth.getAccounts()
						this.setAccount(accounts[0])
						let userInfo = await API.createAndGetUserInfo(accounts[0], parentCode);
						this.setUserInfo(userInfo.data);

						// 用户账户未激活  一直跳转投入页
						toBuy(userInfo.data);
					} catch (e) {
						console.warn("未授权访问账户信息", e);
						uni.showModal({
							title: "账户未授权",
							content: "未授权无法获取账户信息！请点击确认重新授权！",
							success: (e) => {
								if(e.confirm) {
									this.init();
								}
							},
							showCancel: false
						})
					}
				} else {
					uni.showModal({
						title: "不支持浏览器",
						content: "请在DApp浏览器中访问",
						showCancel: false,
						success: (e) => {
							this.init(parentCode);
						}
					})
				}
			}
		},
		computed: {
			...mapState({
				web3: state => state.web3
			})
		}
	}
</script>

<style lang="scss">
	/* 引入uView基础样式，必须写在第一行 */
	@import "uview-ui/index.scss";
	@import "common/common.css";
</style>
