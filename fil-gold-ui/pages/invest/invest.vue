<template>
	<view class="container">
		<view class="wrap" :style="{ height: windowHeight+'px' }">
			<!-- navigation bar -->
			<navbar>
				<navbar-content :showLeftPopup="showLeftPopup" @click="showLeftPopup=!showLeftPopup"></navbar-content>
			</navbar>

			<!-- content -->
			<view class="content">

				<view class="card">
					<view class="card-title">
						<text>{{ i18n.card1Title }} {{userInfo.parentCode ? userInfo.parentCode : "Null"}}</text>
					</view>
					<view class="url one-line-text">
						<text></text>
					</view>
				</view>

				<view class="invest-box">
					<view class="title">
						<text>{{ i18n.card2Title }}</text>
					</view>

					<view class="list">
						<block v-for="(item, index) in investList" :key="index">
							<view class="item" :class="{ action: current===index, big: item.id===5 }"
								@click="current=index">
								<text>{{ item.value }}FIL</text>
							</view>
						</block>
					</view>
					<view class="tip">
						<text>{{ i18n.card3Title }}</text>
					</view>
					<view class="push-btn" @click="handleSubmit">
						<text>{{userInfo.authStatus ? i18n.btn1 : i18n.btn }}</text>
					</view>
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
	import ABI from "../../utils/abi.js"
	import constants from "../../utils/constants.js"
	import API from "../../api/api.js"

	export default {
		data() {
			return {
				showLeftPopup: false,
				investList: [{
						id: 1,
						value: 1
					},
					{
						id: 2,
						value: 5
					},
					{
						id: 3,
						value: 10
					},
					{
						id: 4,
						value: 30
					},
					{
						id: 5,
						value: 50
					},
					{
						id: 6,
						value: 100
					},
					{
						id: 7,
						value: 200
					}
				],
				current: 0
			}
		},
		onLoad() {

		},
		computed: {
			...mapState({
				windowHeight: 'windowHeight',
				web3: state => state.web3,
				publicAddress: state => state.publicAddr,
				account: state => state.account,
				userInfo: state => state.userInfo
			}),
			i18n() {
				return this.$t('invest')
			}
		},
		methods: {
			...mapMutations({
				setUserInfo: "SET_USER_INFO",
			}),
			// button click
			async handleSubmit() {
				let amount = this.investList[this.current].value
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
						let res = await API.inputInvest(this.account, hash);
						uni.hideLoading();
						if(res.code === 200) {
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
					let transtion = await contract.methods.transfer(this.publicAddress, this.web3.utils.toWei(amount.toString(), "ether")).send({
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

		.card {
			padding: 24rpx;
			background-color: rgba(255, 255, 255, 0.12);
			border-radius: 10rpx;

			.card-title {
				height: 38rpx;
				line-height: 38rpx;
				color: #c4c3c1;
			}

			.url {
				margin-top: 20rpx;
			}
		}

		.title {
			margin-top: 48rpx;
			color: #fcfcfc;
		}

		.invest-box {
			.list {
				display: flex;
				flex-wrap: wrap;
				margin-top: 48rpx;

				.item {
					color: #fff;
					background-color: rgba(255, 255, 255, 0.12);
					margin: 8rpx;
					padding: 26rpx 39rpx;
					border-radius: 10rpx;
				}

				.big {
					// padding: 26rpx 115rpx;
				}

				.action {
					background-color: #56d59a;
				}
			}
		}

		.tip {
			margin-top: 32rpx;
			color: #fcfcfc;
		}
	}

	.push-btn {
		width: 670rpx;
		height: 82rpx;
		line-height: 82rpx;
		margin: 80rpx auto 60rpx;
		text-align: center;
		color: #fff;
		border-radius: 20rpx;
		font-size: 28rpx;
		font-weight: bold;
		background-color: #56d59a;
	}
</style>
