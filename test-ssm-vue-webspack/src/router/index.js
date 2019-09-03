import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  mode:'history',
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      component: resolve => require(['../components/pages/user/Login.vue'], resolve)
    },
    {
      path: '/register',
      component: resolve => require(['../components/pages/user/Register.vue'], resolve)
    },    
    {
      path: '/home',
      component: resolve => require(['../components/pages/Home.vue'], resolve),
      children:[
        {
          path: '/',
          component: resolve => require(['../components/pages/Index.vue'], resolve)
        },
        {
          path: '/users',
          component: resolve => require(['../components/pages/user/Users.vue'], resolve)
        },
        {
          path: '/user/info',
          component: resolve => require(['../components/pages/user/UserInfo.vue'], resolve)
        }
      ]
    }
  
  ]
})
