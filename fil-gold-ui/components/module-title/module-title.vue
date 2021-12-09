<template>
  <view>
    <view class="module-title" :style="customStyle">
      <text>{{ title }}</text>
    </view>
  </view>
</template>

<script>
  export default {
    name:"module-title",
    props: {
      title: {
        type: String
      },
      customStyle: {
        type: Object
      }
    },
    data() {
      return {
        
      };
    }
  }
</script>

<style lang="scss" scoped>
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
</style>
