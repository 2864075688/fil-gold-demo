<template>
	<view class="container">
    <view class="wrap" :style="{ height: windowHeight+'px' }">
      <!-- navigation bar -->
      <navbar>
        <navbar-content :showLeftPopup="showLeftPopup" @click="showLeftPopup=!showLeftPopup"></navbar-content>
      </navbar>
      
      <!-- content -->
      <view class="content">
        
        <module-title :title="i18n.title" :customStyle="{ marginTop: 0 }"></module-title>
        
        <view class="rule">
          <block v-for="(item, index) in i18n.list" :key="index">
            
            <view class="one-title">
              <text>{{ item.title }}</text>
            </view>
            <view class="paragraph" v-for="(item2, index2) in item.lineList" :key="index2" :class="{ 'mar-top': item2.marginTop===1 }">
              <text :class="{ highlight: item3.highlight===1 }" v-for="(item3, index3) in item2.content" :key="index3">{{ item3.text }}</text>
            </view>
            
          </block>
        </view>
        
      </view>
    </view>
    
    <left-menu :show="showLeftPopup"></left-menu>    
	</view>
</template>

<script>
  import { mapState } from 'vuex'
	export default {
		data() {
			return {
        showLeftPopup: false
			}
		},
    computed: {
      ...mapState({
        windowHeight: 'windowHeight'
      }),
      i18n() {
        return this.$t('rule')
      }
    },
		methods: {
      langChange(e) {
        const lang = uni.getStorageSync('language')
        this._i18n.locale = lang === 'en'?'zh-HK':'en'
        uni.setStorageSync('language', this._i18n.locale)
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
    .rule {
      width: 670rpx;
      margin: 0 auto;
      color: #fff;
      .one-title {
        margin: 30rpx 0;
      }
      .paragraph {
        font-size: 26rpx;
        .highlight {
          color: #01d2d7;
        }
      }
      .mar-top {
        margin-top: 20rpx;
      }
    }
  }
</style>
