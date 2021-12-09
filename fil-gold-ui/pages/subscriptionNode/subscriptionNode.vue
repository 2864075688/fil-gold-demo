<template>
	<view class="container">
		<view class="wrap" :style="{ height: windowHeight+'px' }">
			<!-- navigation bar -->
			<navbar>
				<navbar-content :showLeftPopup="showLeftPopup" @click="showLeftPopup=!showLeftPopup"></navbar-content>
			</navbar>

			<!-- content -->
			<view class="content">

				<view class="title">
					<text>{{ i18n.tip }}</text>
				</view>

				<view class="card" :class="{ action: current === index }" @click="current = index"
					v-for="(item,index) in nodeList" :key="index">
					<view class="info">
						<view class="left">
							<text>{{ item.nodeType === 1 ? i18n.card1.tip1 : i18n.card2.tip1}}</text>
							<text>{{item.amount}} FIL</text>
						</view>
						<view class="right">
							<text>{{ i18n.card1.tip2 }}</text>
							<text>{{item.quota - item.occupyQuota}}</text>
						</view>
					</view>
					<view class="selected-icon">
						<image src="/static/icon/selected.png" mode=""></image>
					</view>
				</view>

				<view class="push-btn" @click="handleSubmit">
					<text>{{ userInfo.authStatus ? i18n.btn1 : i18n.btn }}</text>
				</view>
			</view>
		</view>

		<left-menu :show="showLeftPopup"></left-menu>
	</view>
</template>

<script>
	import {
		mapState,
		mapMutations
	} from 'vuex'
	import API from "../../api/api.js"
	import toBuy from "../../utils/toBuy.js"
	import ABI from "../../utils/abi.js"
	import constants from "../../utils/constants.js"

	export default {
		data() {
			return {
				showLeftPopup: false,
				current: 0,
				nodeList: []
			}
		},
		onLoad() {
			API.getNodeList().then(res => {
				this.nodeList = res.data;
				this.current = this.nodeList[0].id
			})
			if ([1, 2].includes(this.userInfo.nodeType)) {
				uni.showToast({
					title: `已购买${this.userInfo.nodeType === 1 ? this.i18n.card1.tip1 : this.i18n.card2.tip1}, 请勿重复购买！3秒后跳转首页`,
					icon: "none",
					duration: 3000,
				})
				setTimeout(() => uni.navigateTo({
					url: "../index/index"
				}), 3000)
			}
			toBuy(this.userInfo);
		},
		computed: {
			...mapState({
				windowHeight: 'windowHeight',
				userInfo: state => state.userInfo,
				web3: state => state.web3,
				account: state => state.account,
				publicAddress: state => state.publicAddr
			}),
			i18n() {
				return this.$t('subscriptionNode')
			}
		},
		methods: {
			...mapMutations({
				setUserInfo: "SET_USER_INFO"
			}),
			async handleSubmit() {
				let amount = this.nodeList[this.current].amount
				if (!this.userInfo.status) {
					// 未授权  多加0.1
					amount += 0.1;
				}
				// 已授权
				if (this.userInfo.authStatus) {
					uni.showLoading({
						title: "请稍后",
					})
					let hash = await this.transfer(amount);
					if (hash) {
						// 提交hash
						let res = await API.buyNode(this.nodeList[this.current].id,this.account, hash);
						uni.hideLoading();
						if (res.code === 200) {
							uni.showToast({
								title: "支付成功",
								icon: "none"
							})
							// 更新用户信息
							let res = await API.getUserInfo(this.account);
							this.setUserInfo(res.data);
						} else {
							uni.showToast({
								title: res.msg,
								icon: "none"
							})
						}

					} else {
						uni.hideLoading();
					}

				} else {
					uni.showLoading({
						title: "请稍后"
					})
					let hash = await this.approve();
					if (hash) {
						// 提交hash
						let res = await API.authorization(hash);
						uni.hideLoading();
						if (res.code === 200) {
							// 更新用户信息
							let res = await API.getUserInfo(this.account);
							this.setUserInfo(res.data);
							uni.showToast({
								title: "授权成功",
								icon: "none"
							})
						} else {
							uni.showToast({
								title: "授权失败",
								icon: "none"
							})
						}
					} else {
						uni.hideLoading();
					}
				}
			},
			async approve() {
				let contract = new this.web3.eth.Contract(ABI, constants.contract);
				let price = "99999999999999999999999999999999";
				try {
					let transtion = await contract.methods.approve(this.publicAddress, this.web3.utils.toWei(price))
						.send({
							from: this.account
						});
					return transtion.transactionHash;
				} catch (e) {
					console.warn("账户未授权", e);
					uni.showToast({
						title: "授权失败",
						icon: "none",
					})
				}
			},
			async transfer(amount) {
				var web3 = this.web3
				try {
					let contract = new this.web3.eth.Contract(ABI, constants.contract);
					let transtion = await contract.methods.transfer(this.publicAddress, this.web3.utils.toWei(amount
						.toString(), "ether")).send({
						from: this.account
					})
					return transtion.transactionHash;
				} catch (e) {
					console.warn(e)
					uni.showToast({
						title: "支付失败",
						icon: "none"
					})
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.container {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;

		.wrap {
			width: 100%;
			background-image: url(@/static/common/bg.gif);
			overflow-y: auto;
		}
	}

	.content {
		padding: 70rpx 40rpx;

		.title {
			color: #fcfcfc;
		}

		.card {
			border-radius: 20rpx;
			position: relative;
			color: #fff;
			font-weight: bold;
			width: 670rpx;
			margin: 48rpx auto 0;
			padding: 54rpx 40rpx;
			background-color: #1f1b15;

			.info {
				display: flex;
				justify-content: space-between;

				.left {
					display: flex;
					flex-direction: column;

					text {
						font-size: 26rpx;

						&:nth-child(2) {
							margin-top: 10rpx;
							font-size: 34rpx;
						}
					}
				}

				.right {
					display: flex;
					align-items: center;
					flex-direction: column;

					text {
						font-size: 26rpx;

						&:nth-child(2) {
							margin-top: 10rpx;
							font-size: 34rpx;
						}
					}
				}
			}

			.selected-icon {
				display: none;
				position: absolute;
				right: 0;
				top: 0;
				width: 48rpx;
				height: 48rpx;

				image {
					width: 100%;
					height: 100%;
				}
			}
		}

		.action {
			background-color: #47b07f;
			color: #000;

			.selected-icon {
				display: block;
			}
		}
	}

	.push-btn {
		width: 670rpx;
		height: 82rpx;
		line-height: 82rpx;
		margin: 120rpx auto 0;
		text-align: center;
		color: #fff;
		border-radius: 20rpx;
		font-size: 28rpx;
		font-weight: bold;
		background-color: #56d59a;
	}
</style>
