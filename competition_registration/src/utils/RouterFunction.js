import { Notification } from 'element-ui';

async function routerLoginInterceptor(router, to, from, next, myVuex) {
    if (to.path == "/login" || to.path == "/index" || to.path == "/register") {
        next(true);
    } else {
        let userDetails = myVuex.getters.getUserDetails;
        // 判断是否已登录
        if (userDetails == undefined) {
            console.log('未登录！userDetails为null');
            Notification({
                title: "错误",
                message: "请先登录！",
                type: 'warning'
            })
            // 前往登录页面
            router.push("/login")
        } else {
            next()
        }
    }
}

export default {
    routerLoginInterceptor
}