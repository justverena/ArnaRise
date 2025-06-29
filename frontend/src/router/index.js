import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '@/views/Login.vue'
import ChartPage from '@/views/ChartPage.vue'
import Users from '@/views/Users.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    meta: { requiresAuth: true}
  },

  {
    path: '/login',
    name: 'Login',
    component: Login
  },

  {
    path: '/chart',
    name: 'ChartPage',
    component: ChartPage
  },
  {
    path: '/users',
    name: 'Users',
    component: Users
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes


})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const isProtected = to.meta.requiresAuth

if (isProtected && !token) {
  next('/login')
} else{
  next()
}
})

export default router
