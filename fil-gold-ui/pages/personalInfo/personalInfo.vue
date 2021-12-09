<template>
	<view class="container">
		<view class="wrap" :style="{ height: windowHeight+'px' }">
			<!-- navigation bar -->
			<navbar>
				<navbar-content :showLeftPopup="showLeftPopup" @click="showLeftPopup=!showLeftPopup"></navbar-content>
			</navbar>

			<!-- content -->
			<view class="content">

				<view class="t-title">
					<view class="left">
						<text>{{ i18n.tip }}</text>
						<text>{{account.substr(2, 8)}}...{{account.substr(36)}}</text>
					</view>
					<view class="right">
						<text v-if="userInfo.status">{{ i18n.btnActive }}</text>
						<text v-else>{{ i18n.btnNoActive }}</text>
					</view>
				</view>

				<view class="list">
					<view class="item">
						<view class="icon">
							<image src="/static/icon/wallet.png" mode=""></image>
						</view>
						<view class="title">
							<text>{{ i18n.item1 }}</text>
						</view>
						<view class="right-text">
							<text>{{userInfo.totalInvest}} FIL</text>
						</view>
					</view>
					<view class="item">
						<view class="icon">
							<image src="/static/icon/wallet.png" mode=""></image>
						</view>
						<view class="title">
							<text>{{ i18n.item2 }}</text>
						</view>
						<view class="right-text">
							<text>{{userInfo.totalRevenue}} FIL</text>
						</view>
					</view>
					<view class="item">
						<view class="icon">
							<image src="/static/icon/wallet.png" mode=""></image>
						</view>
						<view class="title">
							<text>{{ i18n.item3 }}</text>
						</view>
						<view class="right-text">
							<text>{{userInfo.withdrawMoney}} FIL</text>
						</view>
					</view>
					<view class="item">
						<view class="icon">
							<image src="/static/icon/wallet3.png" mode=""></image>
						</view>
						<view class="title">
							<text>{{ i18n.item4 }}</text>
						</view>
						<view class="right-text">
							<text>{{userInfo.pastMoney}} FIL</text>
						</view>
					</view>
					<view class="item">
						<view class="icon">
							<image src="/static/icon/wallet2.png" mode=""></image>
						</view>
						<view class="title">
							<text>{{ i18n.item5 }}</text>
						</view>
						<view class="right-text">
							<text>{{userInfo.nodeRevenue}} FIL</text>
						</view>
					</view>
					<view class="item">
						<view class="icon">
							<image src="/static/icon/wallet2.png" mode=""></image>
						</view>
						<view class="title">
							<text>{{ i18n.item6 }}</text>
						</view>
						<view class="right-text">
							<text>{{userInfo.staticRevenue}} FIL</text>
						</view>
					</view>
					<view class="item">
						<view class="icon">
							<image src="/static/icon/wallet2.png" mode=""></image>
						</view>
						<view class="title">
							<text>{{ i18n.item7 }}</text>
						</view>
						<view class="right-text">
							<text>{{userInfo.dynamicRevenue}} FIL</text>
						</view>
					</view>
				</view>

				<view class="push-btn" @click="handleSubmit">
					<text>{{ i18n.btn }}</text>
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
	import toBuy from "../../utils/toBuy.js"
	import API from "../../api/api.js"
	
	export default {
		data() {
			return {
				showLeftPopup: false
			}
		},
		onLoad() {
			toBuy(this.userInfo)
		},
		computed: {
			...mapState({
				windowHeight: 'windowHeight',
				web3: state => state.web3,
				account: state => state.account,
				userInfo: state => state.userInfo
			}),
			i18n() {
				return this.$t('personalInfo')
			}
		},
		methods: {
			...mapMutations({
				setUserInfo: "SET_USER_INFO"
			}),
			handleSubmit() {
				uni.showModal({
					title: "输入提现金额",
					editable: true,
					placeholderText: "输入要提现的金额",
					success: async (e) => {
						if(e.confirm){
							let amount = 0;
							try{
								amount = Number(e.content);
							}catch(e){
								//TODO handle the exception
								console.log(e)
								uni.showToast({
									title: "请输入数字",
									icon: "none"
								})
								return;
							}
							uni.showLoading({
								title: "请稍后"
							})
							let res = await API.withdrawal(this.account, amount);
							uni.hideLoading();
							uni.showToast({
								title: res.msg,
								icon: "none"
							})
							let resUser = await API.getUserInfo(this.account);
							this.setUserInfo(resUser.data)
						}
					}
				})
			
			},

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

		.t-title {
			color: #fcfcfc;
			display: flex;
			justify-content: space-between;
			align-items: center;
			margin-bottom: 40rpx;

			.right {
				padding: 8rpx 10rpx;
				border-radius: 10rpx;
				font-size: 24rpx;
				background-color: #47b07f;
			}
		}

		.list {
			.item {
				display: flex;
				align-items: center;
				padding: 24rpx 32rpx;
				margin-top: 20rpx;
				border-radius: 10rpx;
				background-color: rgba(255, 255, 255, 0.13);
				color: #fff;

				.icon {
					width: 44rpx;
					height: 44rpx;
					margin-right: 20rpx;

					image {
						width: 100%;
						height: 100%;
					}
				}

				.title {
					color: #333;
					font-size: 30rpx;
					color: #fff;
				}

				.right-text {
					margin-left: auto;
					color: #47b07f;
					font-weight: bold;
					font-size: 32rpx;
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
