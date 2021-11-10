import axios from 'axios'
import errorCode from '@/utils/ErrorCode'
import { Notification } from 'element-ui';
import { getToken } from '@/utils/Auth.js'

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 创建axios实例
const service = axios.create({
    // axios中请求配置有baseURL选项，表示请求URL公共部分
    baseURL: process.env.VUE_APP_BASE_API,
    // 超时
    timeout: 10000
})
// 不知道为啥，想用Notification需要先定义才能用，不然会报错：_Notification is not define
const notify = Notification;
// request拦截器
service.interceptors.request.use(
    config => {
        if (getToken()) {
            // 让每个请求携带自定义token 请根据实际情况自行修改
            config.headers.Authorization = getToken();
        }
        console.log('interceptors:config::', config);
        return config
    }, error => {
        console.log(error)
        Promise.reject(error)
    })

// 响应拦截器
service.interceptors.response.use(
    res => {
        console.log('interceptors:response::', res);
        // 未设置状态码则默认成功状态
        const code = res.code;
        // 获取错误信息
        const msg = errorCode[code] || res.data.msg
        console.log("code = ", code);
        if (code === 401) {
            notify.error({
                title: '错误',
                message: '登录状态已过期',
                duration: 2000
            })
            return Promise.reject('error')
        } else if (code === 500) {
            notify({
                title: '错误',
                message: msg,
                type: 'error',
                duration: 2000
            })
            return Promise.reject(new Error(msg))
        } else {
            return res.data
        }
    },
    error => {
        // console.log('Request:err:', error)
        let { message } = error;
        if (message == "Network Error") {
            message = "后端接口连接异常";
        } else if (message.includes("timeout")) {
            message = "系统接口请求超时";
        } else if (message.includes("Request failed with status code")) {
            let code = Number(message.substr(message.length - 3));
            message = "系统接口" + code + "异常";
            console.log(message);
            if (code == 400) {
                return Promise.reject(error);
            } else if (code ==403) {
                message = "没有访问权限！"
            }
        }
        notify({
            title: '错误',
            message: message,
            type: 'error',
            duration: 2000
        })

        return Promise.reject(error)
    }
)

export default service