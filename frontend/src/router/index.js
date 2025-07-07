import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '@/views/Login.vue'
import ChartPage from '@/views/ChartPage.vue'
import Users from '@/views/Users.vue'
import Profile from '@/views/Profile.vue'
import AdminRegister from '@/components/AdminRegister.vue'
import ReportTemplates from '@/views/ReportTemplates.vue'

const requireAdmin = (to, from, next) => {
  const role = localStorage.getItem('role')
  if (role === 'admin') {
    next()
  } else {
    next('/login')
  }
}

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    meta: { requiresAuth: true }
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/chart',
    name: 'ChartPage',
    component: ChartPage,
    meta: { requiresAuth: true }
  },
  {
    path: '/users',
    name: 'Users',
    component: Users,
    meta: { requiresAuth: true }
  },
  {
    path: '/profile',
    name: 'Profile',
    component: Profile,
    meta: { requiresAuth: true }
  },
  {
    path: '/admin/register',
    name: 'AdminRegister',
    component: AdminRegister,
    //beforeEnter: requireAdmin
  },
  {
    path: '/reports',
    name: ReportTemplates,
    component: ReportTemplates,
    meta: { requiresAuth: true}
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

//для защиты всех requiresAuth страниц
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const isProtected = to.meta.requiresAuth

  if (isProtected && !token) {
    return next({ path: '/login', query: { redirect: to.fullPath } })
  }

  if (to.path === '/login' && token) {
    return next('/')
  }

  next()
})

export default router
