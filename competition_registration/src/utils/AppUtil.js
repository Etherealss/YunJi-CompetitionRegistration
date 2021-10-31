/**
 * 更新验证码图片
 */
var changeCaptcha = function (captchaImg) {
    // 再次访问
    captchaImg.src = process.env.VUE_APP_BASE_API + "/captcha?" + new Date().getTime();
}

export default {
    changeCaptcha
}