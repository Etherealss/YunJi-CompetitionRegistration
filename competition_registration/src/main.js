import Vue from 'vue'
import VueRouter from 'vue-router'
import axios from 'axios'
import qs from 'qs'
import App from '@/App.vue'
import router from '@/router'
import store from './store'
import dayjs from "dayjs"


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
Vue.prototype.$axios = axios
Vue.prototype.qs = qs
Vue.prototype.dayjs = dayjs;

new Vue({
    el: '#app',
    render: h => h(App),
    router,
    store,
})