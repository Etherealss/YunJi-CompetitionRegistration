import axios from 'axios'
async function routerLoginInterceptor(to, form, next, myVuex) {

    await isLoginBackstage(myVuex);
    console.log("用户数据：" + JSON.stringify(myVuex.getters.getUserDetails))
    const jsonData = JSON.parse(myVuex.getters.getUserDetails);
    let sessionId = null;
    if (jsonData != null) {
        sessionId = jsonData.sessionId.toString();
    }
    //获取sessionId
    if (sessionId) {//如果已登录
        //判断是否是进入登录页面
        if (to.name == "login") {
            next("homePage");
        } else {
            next(true);
        }
    } else {
        next(true)
    }
}

async function isLoginBackstage(that, myVuex) {
    await axios({
        method: "post",
        // TODO
        url: process.env.VUE_APP_BASE_API + "/users/isLogin",
    }).then((response) => {
        if (response.data != "") {
            console.log("已登录：", JSON.stringify(response.data.username));
            //登录成功后将sessionId 作为登录成功的标识
            let userDetails = JSON.stringify(response.data);
            myVuex.commit("setUserDetails", userDetails);
            //将userDetails存入vuex 中
            console.log("  ++" + JSON.parse(myVuex.getters.getUserDetails).sessionId);
        }
    }).catch((error) => {
        console.log("服务器异常" + error);
    })
}


export default {
    routerLoginInterceptor
}