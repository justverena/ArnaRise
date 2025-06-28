import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '@/views/Login.vue'
import ChartPage from '@/views/ChartPage.vue'
import Users from '@/views/Users.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
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

export default router
