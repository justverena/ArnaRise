import { createRouter, createWebHistory } from 'vue-router'

import Home from '@/views/Home.vue'
import Login from '@/features/auth/Login.vue'
import ChartPage from '@/features/analytics/views/ChartPage.vue'
import Users from '@/features/users/views/Users.vue'
import Profile from '@/features/profile/views/Profile.vue'
import AdminRegister from '@/features/users/views/AdminRegister.vue'
import ReportTemplates from '@/features/templates/views/ReportTemplates.vue'
import PartnerProfile from '@/features/profile/components/PartnerProfile.vue'
import AnalystProfile from '@/features/profile/components/AnalystProfile.vue'
import AdminUserList from '@/components/admin/AdminUserList.vue'
import NewReport from '@/features/reports/views/NewReport.vue'
import AnalystReports from '@/features/analytics/views/AnalystReports.vue'

// layouts
import AdminLayout from '@/layouts/AdminLayout.vue'
import PartnerLayout from '@/layouts/PartnerLayout.vue'
import AnalystLayout from '@/layouts/AnalystLayout.vue'

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

  // Админ-маршруты
  {
    path: '/admin',
    component: AdminLayout,
    meta: { requiresAuth: true, role: 'admin' },
    children: [
      {
        path: 'users',
        name: 'AdminUsers',
        component: Users
      },
      {
        path: 'profile',
        name: 'AdminProfile',
        component: Profile
      },
      {
        path: 'register',
        name: 'AdminRegister',
        component: AdminRegister
      },
      {
        path: 'userlist',
        name: 'AdminUserList',
        component: AdminUserList
      }
    ]
  },

  // --------------------- PARTNER ---------------------
  {
    path: '/partner',
    component: PartnerLayout,
    meta: { requiresAuth: true, role: 'partner' },
    children: [
      {
        path: 'profile',
        name: 'PartnerProfile',
        component: PartnerProfile
      },
      {
        path: 'new-report',
        name: 'NewReport',
        component: NewReport
      }
    ]
  },

  // --------------------- ANALYST ---------------------
  {
    path: '/analyst',
    component: AnalystLayout,
    meta: { requiresAuth: true, role: 'analyst' },
    children: [
      {
        path: 'profile',
        name: 'AnalystProfile',
        component: AnalystProfile
      },
      {
        path: 'reports',
        name: 'AnalystReports',
        component: AnalystReports
      },
      {
        path: 'templates',
        name: 'ReportTemplates',
        component: ReportTemplates
      },
      {
        path: 'chart',
        name: 'ChartPage',
        component: ChartPage
      }
    ]
  },

  // --------------------- SHORTCUT PATHS (по роли) ---------------------

  {
    path: '/profile',
    redirect: () => {
      const role = localStorage.getItem('role')
      return `/${role}/profile`
    }
  },
  {
    path: '/chart',
    redirect: () => {
      const role = localStorage.getItem('role')
      return `/${role}/chart`
    }
  },
  {
    path: '/templates',
    redirect: () => {
      const role = localStorage.getItem('role')
      return `/${role}/templates`
    }
  },
  {
    path: '/reports',
    redirect: () => {
      const role = localStorage.getItem('role')
      return `/${role}/reports`
    }
  },
  {
    path: '/new-report',
    redirect: () => {
      const role = localStorage.getItem('role')
      return `/${role}/new-report`
    }
  },
  {
    path: '/users',
    redirect: () => {
      const role = localStorage.getItem('role')
      return `/${role}/users`
    }
  },
]


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

// Глобальный guard для requiresAuth и защиты по ролям
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const userRole = localStorage.getItem('role')
  const isProtected = to.meta.requiresAuth
  const expectedRole = to.meta.role

  if (isProtected && !token) {
    return next({ path: '/login', query: { redirect: to.fullPath } })
  }

  if (to.path === '/login' && token) {
    return next('/')
  }

  if (expectedRole && expectedRole !== userRole) {
    return next('/') // или страница 403
  }

  next()
})

export default router
