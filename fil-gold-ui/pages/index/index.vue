<template>
	<view class="container">
		<view class="wrap" :style="{ height: windowHeight+'px' }">
			<!-- navigation bar -->
			<navbar>
				<navbar-content :showLeftPopup="showLeftPopup" @click="showLeftPopup=!showLeftPopup" @change="change"></navbar-content>
			</navbar>

			<!-- content -->
			<view class="content">

				<view class="top-logo">
					<image src="/static/common/logo.png" mode=""></image>
				</view>

				<view class="announcement">
					<view class="left-icon">
						<image src="/static/icon/announcement.png" mode=""></image>
					</view>
					<view class="ann-text one-line-text">
						<text>{{ current.title }}</text>
					</view>
					<view class="right" @click="showAnnouncementPopup = true">
						<text>{{ i18n.announcementBtn }}</text>
						<image src="/static/icon/arrow-right.png" mode=""></image>
					</view>
				</view>

				<view class="promotional-video">
					<video src="/static/common/promotional-video.mp4" controls></video>
				</view>

				<view class="promotional-text">
					<view class="item">
						<text>{{ i18n.promotional[0] }}</text>
					</view>
					<view class="item">
						<text>{{ i18n.promotional[1] }}</text>
					</view>
				</view>

				<view class="push-btn" @click="toInvestPage">
					<text>{{ i18n.btn }}</text>
				</view>

				<module-title :title="i18n.title[0].text"></module-title>

				<view class="statistics">
					<view class="item">
						<image src="/static/index/icon1.png" mode=""></image>
						<text class="label">{{ i18n.statistics[0] }}</text>
						<text class="num">0</text>
					</view>
					<view class="item">
						<image src="/static/index/icon2.png" mode=""></image>
						<text class="label">{{ i18n.statistics[1] }}</text>
						<text class="num">0</text>
					</view>
				</view>

				<module-title :title="i18n.title[1].text"></module-title>

				<view class="characteristic">
					<block v-for="(item, index) in i18n.characteristic" :key="index">
						<view class="item">
							<view class="icon">
								<image src="/static/index/img2.png" mode=""></image>
							</view>
							<view class="right">
								<view class="text">
									<text class="title">{{ item.title }}</text>
									<text class="info">{{ item.info }}</text>
								</view>
							</view>
						</view>
					</block>

				</view>

				<module-title :title="i18n.title[2].text"></module-title>

				<view class="debut">
					<view class="item">
						<image src="/static/index/img5.png" mode=""></image>
					</view>
					<view class="item">
						<image src="/static/index/img6.png" mode=""></image>
					</view>
					<view class="item">
						<image src="/static/index/img7.png" mode=""></image>
					</view>
					<view class="item">
						<image src="/static/index/img8.png" mode=""></image>
					</view>
					<view class="item">
						<image src="/static/index/img9.png" mode=""></image>
					</view>
					<view class="item">
						<image src="/static/index/img10.png" mode=""></image>
					</view>
				</view>
			</view>
		</view>

		<left-menu :show="showLeftPopup"></left-menu>

		<u-popup mode="center" v-model="showAnnouncementPopup" width="654rpx" height="65%" border-radius="20">
			<view class="p-wrap">
				<view class="title">
					<module-title :title="i18n.announcement.title" :customStyle="{ marginTop: '0' }"></module-title>
				</view>
				<scroll-view scroll-y="true" style="height: 85%;">
					<view class="info">
						<view class="middle-title">
							<text v-html="current.content "></text>
						</view>
						<block v-for="(item, index) in i18n.announcement.list" :key="index">
							<view class="paragraph">
								<view v-for="(item2, index2) in item" :key="index2">
									<text>{{ item2 }}</text>
								</view>
							</view>
						</block>

					</view>
				</scroll-view>
			</view>
		</u-popup>

	</view>
</template>

<script>
	import {
		mapState
	} from 'vuex'
	import toBuy from "../../utils/toBuy.js"
	import API from "../../api/api.js"

	export default {
		data() {
			return {
				showLeftPopup: false,
				showAnnouncementPopup: false,
				type: 'left',
				notice: {},
				current: {}
			}
		},
		onLoad() {
			// toBuy(this.userInfo);
			API.getNotice().then(res => {
				this.notice = {
					en: {
						title: res.data.englishTitle,
						content: res.data.englishContent
					},
					zh_hk: {
						title: res.data.noticeTitle,
						content: res.data.noticeContent
					}
				}
				let lang = uni.getStorageSync('language');
				this.current = lang === "en" ? this.notice.en : this.notice.zh_hk;
			});
		},
		computed: {
			...mapState({
				windowHeight: 'windowHeight',
				userInfo: state => state.userInfo
			}),
			i18n() {
				return this.$t('index')
			}
		},
		methods: {
			toInvestPage() {
				uni.navigateTo({
					url: '/pages/invest/invest'
				})
			},
			change(e) {
				if(e === "en") {
					this.current = this.notice.zh_hk;
				} else {
					this.current = this.notice.en
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
		padding: 70rpx 0;

		.top-logo {
			width: 270rpx;
			height: 200rpx;
			margin: 0 auto;

			image {
				width: 100%;
				height: 100%;
			}
		}

		.announcement {
			width: 558rpx;
			padding: 24rpx 22rpx;
			box-sizing: border-box;
			margin: 70rpx auto 0;
			background: rgba(15, 40, 28, 0.7);
			display: flex;
			justify-content: space-between;
			align-items: center;
			font-size: 24rpx;
			color: #d1d1cf;
			border-radius: 10rpx;

			.left-icon {
				width: 42rpx;
				height: 42rpx;
				display: flex;
				justify-content: center;

				image {
					width: 100%;
					height: 100%;
				}
			}

			.ann-text {
				width: 300rpx;
				overflow: hidden;
			}

			.right {
				display: flex;
				justify-content: space-between;
				align-items: center;
				width: 132rpx;

				image {
					width: 32rpx;
					height: 32rpx;
				}
			}
		}

		.promotional-video {
			width: 600rpx;
			height: 360rpx;
			margin: 60rpx auto 0;

			video {
				height: 360rpx;
			}
		}

		.promotional-text {
			width: 500rpx;
			margin: 0 auto;
			color: #fff;
			font-size: 30rpx;

			.item {
				text-indent: 2em;
				margin-top: 64rpx;
			}
		}

		.push-btn {
			width: 550rpx;
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

		.module-title {
			position: relative;
			margin-top: 150rpx;
			height: 82rpx;
			margin-bottom: 58rpx;
			color: #46ad7d;
			font-weight: 600;
			font-size: 36rpx;
			text-align: center;

			&::before {
				content: '';
				width: 56rpx;
				height: 6rpx;
				background-color: #46ad7d;
				position: absolute;
				bottom: 0;
				left: 50%;
				transform: translateX(-50%);
			}
		}

		.statistics {
			display: flex;
			justify-content: space-between;
			width: 670rpx;
			margin: 0 auto;

			.item {
				flex: 1;
				display: flex;
				align-items: center;
				flex-direction: column;
				justify-content: center;

				image {
					width: 72rpx;
					height: 72rpx;
				}

				.label {
					color: #cfcecd;
					margin: 12rpx 0;
				}

				.num {
					color: #fff;
					font-size: 40rpx;
					font-weight: 500;
				}
			}
		}

		.characteristic {
			.item {
				display: flex;
				align-items: center;
				width: 670rpx;
				min-height: 224rpx;
				margin: 48rpx auto 0;

				.icon {
					flex: 1;

					image {
						width: 326rpx;
						height: 224rpx;
					}
				}

				.right {
					flex: 1;
					display: flex;
					align-items: center;

					.text {
						display: flex;
						flex-direction: column;
						font-size: 26rpx;
						min-height: 118rpx;
						padding-left: 30rpx;

						.title {
							color: #fff;
							margin-bottom: 16rpx;
						}

						.info {
							color: #888782;
						}
					}
				}

				&:nth-child(even) {
					flex-direction: row-reverse;
				}
			}
		}

		.debut {
			width: 670rpx;
			margin: 0 auto;
			display: flex;
			justify-content: space-between;
			flex-wrap: wrap;

			.item {
				width: 322rpx;
				height: 104rpx;

				image {
					width: 100%;
					height: 100%;
				}
			}
		}
	}

	.p-wrap {
		height: 100%;
		background-color: #a2a2a2;
		padding: 40rpx 32rpx;

		.info {
			color: #cecdcc;

			.middle-title {
				text-align: center;
			}

			.paragraph {
				margin-top: 36rpx;
			}
		}
	}
</style>
