import Vue from 'vue'
import VueRouter from 'vue-router'
import qs from 'qs'
import App from '@/App.vue'
import router from '@/router'
import store from './store'
import dayjs from "dayjs"
import Request from '@/utils/Request'
import axios from 'axios'

//完整引入
//引入ElementUI组件库
import ElementUI from 'element-ui';
//引入ElementUI全部样式
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);
// 路由
Vue.use(VueRouter)

// 关闭Vue生产提示
Vue.config.productionTip = false

//全局注册
// Vue.prototype.$notify = Notification;
Vue.prototype.axios = axios
Vue.prototype.$axios = Request
Vue.prototype.qs = qs
Vue.prototype.dayjs = dayjs;
// 调用路由
Vue.prototype.$doRoute = function (path) {
    router.push(path);
} 

new Vue({
    el: '#app',
    render: h => h(App),
    router,
    store,
})