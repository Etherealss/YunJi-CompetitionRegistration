// 引入VueRouter
import VueRouter from 'vue-router'
import myStore from '@/store'
// 引入路由方法
import routerLoginInterceptor from '@/utils/RouterFunction'
// 引入组件
import Index from '@/views/Index'
// import Sign from '@/views/Sign'
import Login from '@/views/Login'
import Register from '@/views/Register'
import UserCenter from '@/views/user_center/UserCenter'
import UserProfile from '@/views/user_center/UserProfile'
import UserCompEntered from '@/views/user_center/UserCompEntered'
import UserCompWatched from '@/views/user_center/UserCompWatched'
import UserTeam from '@/views/user_center/UserTeam'
import Notify from '@/views/notify/Notify'
import NotifyTeam from '@/views/notify/NotifyTeam'
import NotifySystem from '@/views/notify/NotifySystem'

// https://blog.csdn.net/qq_29252021/article/details/109615753
// 缓存原型上的push函数
const originPush = VueRouter.prototype.push
const originReplace = VueRouter.prototype.replace
// 给原型对象上的push指定新函数函数
VueRouter.prototype.push = function (location, onComplete, onAbort) {
	// 判断如果没有指定回调函数, 通过call调用源函数并使用catch来处理错误
	if (onComplete === undefined && onAbort === undefined) {
		return originPush.call(this, location, onComplete, onAbort).catch(() => { })
	} else { // 如果有指定任意回调函数, 通过call调用源push函数处理
		originPush.call(this, location, onComplete, onAbort)
	}
}
// replace同理处理
VueRouter.prototype.replace = function (location, onComplete, onAbort) {
	if (onComplete === undefined && onAbort === undefined) {
		return originReplace.call(this, location, onComplete, onAbort).catch(() => { })
	} else {
		originReplace.call(this, location, onComplete, onAbort)
	}
}

//创建router实例对象，去管理一组一组的路由规则
const router = new VueRouter({
	routes: [
		{   // 初始化重定向首页，最开始加载后停留的页面
			path: '/',
			redirect: '/index'
		},
		{   // 首页
			path: '/index',
			name: 'index',
			component: Index
		},
		{
			path: '/login',
			name: 'login',
			component: Login
		},
		{
			path: '/register',
			name: 'register',
			component: Register
		},
		{
			// 嵌套路由：官网 https://router.vuejs.org/zh/guide/essentials/nested-routes.html
			path: '/users',
			component: UserCenter,
			children: [
				{
					path: "profile",
					component: UserProfile,
				},
				{
					path: "entered",
					component: UserCompEntered,
				},
				{
					path: "watched",
					component: UserCompWatched,
				},
				{
					path: "teams",
					component: UserTeam,
				}
			]
		},
		{
			path: '/notify',
			component: Notify,
			children: [
				{
					path: "team",
					component: NotifyTeam,
				},
				{
					path: "system",
					component: NotifySystem,
				},
			]
		},
	]
})

router.beforeEach((to, from, next) => {
	routerLoginInterceptor.routerLoginInterceptor(router, to, from, next, myStore);
})

//暴露router
export default router