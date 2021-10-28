'use strict'
const path = require('path');

function resolve(dir) {
  return path.join(__dirname, dir);
}

module.exports = {
  // 部署生产环境和开发环境下的URL。
  // 默认情况下，Vue CLI 会假设你的应用是被部署在一个域名的根路径上
  publicPath: process.env.NODE_ENV === "production" ? "/" : "/",
  // 在npm run build 或 yarn build 时 ，生成文件的目录名称（要和baseUrl的生产环境路径一致）（默认dist）
  outputDir: 'dist',
  // 用于放置生成的静态资源 (js、css、img、fonts) 的；（项目打包之后，静态资源会放在这个文件夹下）
  assetsDir: 'static',

  devServer: {
    /*
      配置代理： 
      https://www.bilibili.com/video/BV1Zy4y1K7SH?p=97
      https://blog.csdn.net/sinat_41622641/article/details/81636713
    */
    // proxy: 'http://localhost:8081'
    proxy: {
      // 关于Vue 项目中配置全局属性 process.env 的配置:https://www.cnblogs.com/budingbuding/p/13276891.html
      [process.env.VUE_APP_BASE_API]: {
        target: 'http://localhost:8081',
        // 定义正则匹配路径并重写
        pathRewrite: { ['^' + process.env.VUE_APP_BASE_API]: '' },
        // 用于支持WebSocket
        ws: true,
        // 是否修改代理服务器的来源信息
        changeOrigin: true
      }
    },
  },

  configureWebpack: {
    resolve: {
      alias: {
        '@': resolve('src')
      }
    }
  }

};