<template>
  <view>
    <view class="navbar">
      <view class="nav-logo" @click="toIndex">
        <image src="/static/common/nav-logo.png" mode=""></image>
      </view>
      <view class="lang-btn" @data-lang="en" @click="langChange">
        <view class="context">
          <text :class="_i18n.locale === 'zh-HK'?'action':''">中</text>
          <text>{{' / '}}</text>
          <text :class="_i18n.locale === 'en'?'action':''">EN</text>
        </view>
      </view>
      <view class="right-icon" @click="click">
        <image v-if="!showLeftPopup" src="/static/icon/menu.png" mode=""></image>
        <image v-else src="/static/icon/close.png" mode=""></image>
      </view>
    </view>
  </view>
</template>

<script>
  export default {
    name:"navbar-content",
    props: {
      showLeftPopup: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return { }
    },
    computed: {
      i18n() {
        return this.$t('tabBar')
      }
    },
    methods: {
      langChange(e) {
        const lang = uni.getStorageSync('language')
        this._i18n.locale = lang === 'en'?'zh-HK':'en'
        uni.setStorageSync('language', this._i18n.locale)
		this.$emit("change", lang)
      },
      click() {
        this.$emit('click')
      },
      toIndex() {
        uni.reLaunch({
          url: '/pages/index/index'
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  // navigation bar
  .navbar {
    height: 50px;
    display: flex;
    align-items: center;
    .nav-logo {
      height: 54rpx;
      width: 220rpx;
      margin: 0 24rpx 0 36rpx;
      image {
        width: 100%;
        height: 100%;
      }
    }
    .lang-btn {
      background-color: #fff;
      width: 130rpx;
      height: 46rpx;
      border-radius: 46rpx;
      display: flex;
      justify-content: center;
      align-items: center;
      .context {
        font-size: 26rpx;
        .action {
          color: #47b07f;
        }
      }
    }
    // right menu button
    .right-icon {
      width: 48rpx;
      height: 48rpx;
      margin: 0 32rpx 0 auto;
      image {
        width: 100%;
        height: 100%;
      }
    }
  }
</style>
