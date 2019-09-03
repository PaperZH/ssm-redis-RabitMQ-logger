// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import AxiosServie from './service/AxiosService'
import Blob from './vendor/Blob'
import Export2Excel from './vendor/Export2Excel'

//启动不产生提示
Vue.config.productionTip = true
Vue.use(ElementUI)
Vue.use(AxiosServie)

//判断是否有token,没有则登陆
// router.beforeEach((to, from, next) => {
//   if (to.path != '/login') {
//     var token = sessionStorage.getItem('token');
//     if (token == null) {
//        next({path:'/login'});
//     } else {
//       //根据后台返回的路由进行判断是否合法
//       var routers = sessionStorage.getItem('routers');
//       var flag = false;
//       routers.split("_").forEach( v => {
//         if(v == to.path) {
//           flag = true;
//         }
//       });

//       if(flag) {
//        next();
//       } else {
//          next({path:'/home'});
//       }
//     }
//   } else {
//     next();
//   }
// })
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
