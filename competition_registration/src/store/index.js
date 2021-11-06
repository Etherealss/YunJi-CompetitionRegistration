//引入Vue核心库
import Vue from 'vue'
//引入Vuex
import Vuex from 'vuex'
import { getByStorage, setToStorage } from '@/utils/Auth.js'
//应用Vuex插件
Vue.use(Vuex)

//准备actions对象——响应组件中用户的动作
const actions = {}
//准备mutations对象——修改state中的数据
const mutations = {
    //存储token方法
    //设置token等于外部传递进来的值
    setUserDetails(state, userDetails) {
        // console.log('setUserDetails: ',userDetails);
        state.userDetails = userDetails;
        if (userDetails != undefined) {
            setToStorage("userDetails", JSON.stringify(userDetails))
        }
    },
    setTokenDetails(state, tokenDetails) {
        // console.log('setUserDetails: ',tokenDetails);
        state.tokenDetails = tokenDetails;
        if (tokenDetails != undefined) {
            setToStorage("tokenDetails", JSON.stringify(tokenDetails))
        }
    }
}
//准备state对象——保存具体的数据
const state = {
    // 用户信息
    userDetails: null,
    tokenDetails: null,
}

const getters = {
    //获取token方法
    //判断是否有token,如果没有重新赋值，返回给state的token
    getUserDetails: (state) => {
        if (state.userDetails == null) {
            // 若vuex中无token就去sessionStorage中查找
            let userDetails = getByStorage("userDetails");
            if (userDetails != null) {
                state.userDetails = JSON.parse(userDetails);
            }
        }
        return state.userDetails;
    },
    getToken: (state) => {
        if (state.tokenDetails == null) {
            let tokenDetails = getByStorage("tokenDetails");
            if (tokenDetails != null) {
                state.tokenDetails = JSON.parse(tokenDetails);
            }
        }
        if (state.tokenDetails == null || state.tokenDetails == undefined) {
            console.error("获取不到userDetails！！！")
            return '';
        }
        return state.tokenDetails.token_type + " " + state.tokenDetails.access_token;
    },
    isOfficial: (state) => {
        return state.userDetails != null && state.userDetails.userRole == "official";
    },
}

//创建并暴露store
export default new Vuex.Store({
    actions,
    mutations,
    state,
    getters
})