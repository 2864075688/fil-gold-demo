import Vue from 'vue'  
import App from './App'
import store from './store'
import VueI18n from 'vue-i18n' 

// 全局引入UI库 uView 
import uView from "uview-ui";
Vue.use(uView);

// 全局挂载VueX
Vue.prototype.$store = store

// #ifndef VUE3
Vue.use(VueI18n)
Vue.config.productuinTip = false

let lang = uni.getStorageSync('language')
if (lang !== 'zh-HK' && lang !== 'en') {
  lang = 'zh-HK'
}
const i18n = new VueI18n({
  locale: lang || 'zh-HK',
  messages: { 
		'zh-HK': require('@/lang/zh_HK.js').lang,
		'en': require('@/lang/en.js').lang
	}
})
Vue.prototype._i18n = i18n
uni.setStorageSync('language', i18n.locale)
App.mpType = 'app'
const app = new Vue({
  i18n,
  ...App
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif