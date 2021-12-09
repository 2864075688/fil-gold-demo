<template>
  <view>
    <u-popup v-model="show" z-index="999" width="100%">
      <view class="menu">
        <block v-for="(item, index) in i18n" :key="index">
          <view class="item" @click="toPersonalInfoPage(item.url)">
            <view class="icon">
              <image :src="item.icon" mode=""></image>
            </view>
            <view class="title">
              <text>{{ item.title }}</text>
            </view>
            <view class="right-icon">
              <image src="/static/icon/arrow-right.png" mode=""></image>
            </view>
          </view>
        </block>
      </view>
    </u-popup>
  </view>
</template>

<script>
  export default {
    name:"left-Menu",
    props: {
      show: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        
      };
    },
    computed: {
      i18n() {
        return this.$t('menu')
      }
    },
    methods: {
      toPersonalInfoPage(url) {
        uni.navigateTo({
          url
        })
      },
      toSubscriptionNodePage(url) {
        uni.navigateTo({
          url
        })
      },
      toRecommendPage(url) {
        uni.navigateTo({
          url
        })
      },
      toRulePage(url) {
        uni.navigateTo({
          url
        })
      }
    }
  }
</script>

<style lang="scss" scoped>
  .menu {
    padding-top: 50px;
    .item {
      display: flex;
      align-items: center;
      padding: 40rpx 32rpx 32rpx;
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
      }
      .right-icon {
        width: 44rpx;
        height: 44rpx;
        margin-left: auto;
        image {
          width: 100%;
          height: 100%;
        }
      }
    }
  }
</style>
