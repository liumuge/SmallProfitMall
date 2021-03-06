import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import SvgIcon from './components/UtilsComponent/SvgIcon'
import './assets/iconfont/iconfont.js'
import './assets/iconfont/icon.css'
import './assets/reset.scss'
import './router/guard'
import 'vue2-animate/dist/vue2-animate.min.css'
import * as socketApi from './api/util/socket'
import * as Util from '../src/util/util'
import moment from 'moment'


Vue.prototype.moment=moment;
Vue.prototype.Util=Util;
//提升事件响应
import 'default-passive-events'
//放大镜组件
import VuePhotoZoomPro from "vue-photo-zoom-pro";
//视频播放组件
import VideoPlayer from 'vue-video-player'
import 'vue-video-player/src/custom-theme.css'
import 'video.js/dist/video-js.css'
//animated动画
import animated from 'animate.css'
const Header = () => import("./components/pages/Header"); //组件懒加载
const Footer = () => import("./components/pages/Footer");
import VueCropper from 'vue-cropper'
Vue.use(VueCropper)

Vue.component("Header",Header)
Vue.component("Footer",Footer)


Vue.use(animated)

Vue.use(VideoPlayer)

//引用websocket

Vue.prototype.socketApi = socketApi

//全局注册组件
Vue.component('SvgIcon', SvgIcon)
Vue.prototype.axios = axios;
Vue.use(ElementUI)//全局使用ElementUI
Vue.config.productionTip = false;
Vue.use(VuePhotoZoomPro);


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

