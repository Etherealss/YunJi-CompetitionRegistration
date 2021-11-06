/**
 * 更新验证码图片
 */
var changeCaptcha = function (captchaImg) {
    // 再次访问
    captchaImg.src = process.env.VUE_APP_BASE_API + "/captcha?" + new Date().getTime();
}

import router from '@/router';
var doRoute = function (path) {
    router.push(path);    
}

export default {
    changeCaptcha, doRoute
}