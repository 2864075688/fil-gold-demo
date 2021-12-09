<template>
  <!-- 防止塌陷，盖住上部元素 -->
  <view class="navbar" :style="{ height: contentHeight + 'px !important'}">
    <view id="navbar-content" class="navbar-content" :style="{ background: background }">
      <!-- 插入显示的元素 -->
      <slot></slot>
    </view>
  </view>
</template>

<script>
  // 底部插入fixed定位元素
  export default {
    name:"footer-navigation",
    props: {
      safeAreaInsetBottom: {
        type: Boolean,
        default: true
      },
      background: {
        type: String,
        default: '#47b07f'
      }
    },
    data() {
      return {
        contentHeight: 0
      };
    },
    mounted() {
      // 获取插入显示的元素的高度，并赋值给父元素
      const query = uni.createSelectorQuery().in(this);
      query.select('#navbar-content').boundingClientRect(response => {
        this.contentHeight = response.height
      }).exec();
    }
  }
</script>

<style lang="scss" scoped>
  .navbar {
    width: 100%;
  }
  // 定位到屏幕最底部
  .navbar-content {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    z-index: 9999;
    overflow: hidden;
  }
</style>
