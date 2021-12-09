<template>
	<view class="container">
		<view class="wrap" :style="{ height: windowHeight+'px' }">
			<!-- navigation bar -->
			<navbar>
				<navbar-content :showLeftPopup="showLeftPopup" @click="showLeftPopup=!showLeftPopup"></navbar-content>
			</navbar>

			<!-- content -->
			<view class="content">

				<view class="box">
					<view class="title">
						<text>{{ i18n.total }}</text>
					</view>
					<view class="card">
						<view class="card-title">
							<text>{{ i18n.card1Title }}</text>
						</view>
						<view class="url one-line-text">
							<text>{{remoteLink}}</text>
						</view>
					</view>
					<view class="push-btn" @click="copy(remoteLink)">
						<text>{{ i18n.btn }}</text>
					</view>
					<view class="card">
						<view class="card-title">
							<text>{{ i18n.card2Title }} {{userInfo.parentCode ? userInfo.parentCode : "Null"}}</text>
						</view>
						<view class="url one-line-text">
							<text></text>
						</view>
					</view>
				</view>

			</view>
		</view>

		<left-menu :show="showLeftPopup"></left-menu>
	</view>
</template>

<script>
	import {
		mapState
	} from 'vuex'
	import Base64 from "../../utils/base64.js"
	import toBuy from "../../utils/toBuy.js"
	
	export default {
		data() {
			return {
				showLeftPopup: false,
			}
		},
		onLoad() {
			toBuy(this.userInfo)
		},
		computed: {
			...mapState({
				windowHeight: 'windowHeight',
				userInfo: state => state.userInfo
			}),
			i18n() {
				return this.$t('recommend')
			},
			remoteLink(){
				let url = location.host;
				return `${url}/#/?parentCode=${Base64.encode(this.userInfo.userCode)}`
			}
		},
		methods: {
			copy(value) {
				uni.setClipboardData({
					data: value, // 要被复制的内容
					success: () => { // 复制成功的回调函数
						uni.showToast({ // 提示
							title: this.i18n.copySuccess,
							icon: 'none'
						})
					}
				});
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
		padding: 70rpx 0;

		.box {
			width: 686rpx;
			padding: 80rpx 40rpx;
			margin: 0 auto;
			background-color: rgba(255, 255, 255, 0.10);
			border-radius: 20rpx;
			color: #fff;

			.title {
				margin-bottom: 70rpx;
				font-size: 30rpx;
				text-align: center;
			}

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
		}
	}

	.push-btn {
		width: 550rpx;
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
