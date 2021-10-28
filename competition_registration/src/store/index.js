//引入Vue核心库
import Vue from 'vue'
//引入Vuex
import Vuex from 'vuex'
//应用Vuex插件
Vue.use(Vuex)

//准备actions对象——响应组件中用户的动作
const actions = {}
//准备mutations对象——修改state中的数据
const mutations = {
    //存储token方法
    //设置token等于外部传递进来的值
    setUserDetails(state, userDetails) {
        state.userDetails = userDetails;
        //同步存储token至sessionstorage
        /**
         * vuex存储在内存
         * localstorage（本地存储）则以文件的方式存储在本地,永久保存
         * sessionstorage( 会话存储 ) ,临时保存。localStorage和sessionStorage只能存储字符串类型
         */
        sessionStorage.setItem("userDetails", userDetails)
    }
}
//准备state对象——保存具体的数据
const state = {
    // 初始化token
    userDetails: null
}

const getters = {
    //获取token方法
    //判断是否有token,如果没有重新赋值，返回给state的token
    getUserDetails: (state) => {
        if (!state.userDetails) {
            // 若vuex中无token就去sessionStorage中查找
            state.userDetails = sessionStorage.getItem("userDetails");
        }
        return state.userDetails;
    }
}

//创建并暴露store
export default new Vuex.Store({
    actions,
    mutations,
    state,
    getters
})