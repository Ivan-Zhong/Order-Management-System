import { createRouter, createWebHistory } from 'vue-router'
import login from '../views/login.vue'
import staff from '../views/staff.vue'

const routes = [
  {
    path: '/',
    name: 'login',
    component: login
  },
  // {
  //   path: '/about',
  //   name: 'About',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  // },
  {
    path: '/home',
    name: 'home',
    component: () => import('../views/home.vue')
  },
  {
    path: '/staff',
    name: 'staff',
    component: () => import('../views/staff.vue')
  },
  {
    path: '/client',
    name: 'client',
    component: () => import('../views/client.vue')
  },
  {
    path: '/clientinfo',
    name: 'clientinfo',
    component: () => import('../views/clientinfo.vue')
  },
  {
    path: '/order',
    name: 'order',
    component: () => import('../views/order.vue')
  },
  {
    path: '/myinfo',
    name: 'myinfo',
    component: () => import('../views/myinfo.vue')
  },
  {
    path: '/createuser',
    name: 'createuser',
    component: () => import('../views/createuser.vue')
  },
  {
    path: '/createclient',
    name: 'createclient',
    component: () => import('../views/createclient.vue')
  },
  {
    path: '/userinfo',
    name: 'userinfo',
    component: () => import('../views/userinfo.vue')
  },
  {
    path: '/createorder',
    name: 'createorder',
    component: () => import('../views/createorder.vue')
  },
  {
    path: '/orderinfo',
    name: 'orderinfo',
    component: () => import('../views/orderinfo.vue')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router