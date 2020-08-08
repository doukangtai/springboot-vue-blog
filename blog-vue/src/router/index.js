import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../components/admin/Login'),
    meta: {
      requireAuth: false,
    }
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import('../components/admin/Home'),
    meta: {
      requireAuth: true,
    },
    children: [
      {
        path: '/writeArticle',
        name: '写文章',
        component: () => import('../components/admin/WriteArticle'),
        meta: {
          requireAuth: true,
        }
      },
      {
        path: '/articleList',
        name: '文章列表',
        component: () => import('../components/admin/ArticleList'),
        meta: {
          requireAuth: true,
        }
      },
      {
        path: '/updateInfo',
        name: '更新信息',
        component: () => import('../components/admin/UpdateInfo'),
        meta: {
          requireAuth: true,
        }
      }
    ]
  },
  {
    path: '/',
    name: '首页',
    component: () => import('../components/display/Display'),
    meta: {
      requireAuth: false,
    },
    children: [
      {
        path: '/',
        name: '博客首页',
        component: () => import('../components/display/DisplayArticle'),
        meta: {
          requireAuth: false,
        }
      },
      {
        path: '/articleDetail',
        name: '文章详情',
        component: () => import('../components/display/ArticleDetail'),
        meta: {
          requireAuth: false,
        }
      },
      {
        path: '/archive',
        name: '归档',
        component: () => import('../components/display/Archive'),
        meta: {
          requireAuth: false,
        }
      },
      {
        path: '/displayCategory',
        name: '分类',
        component: () => import('../components/display/DisplayCategory'),
        meta: {
          requireAuth: false,
        }
      },
      {
        path: '/displayTag',
        name: '标签',
        component: () => import('../components/display/DisplayTag'),
        meta: {
          requireAuth: false,
        }
      },
      {
        path: '/about',
        name: '关于',
        component: () => import('../components/display/About'),
        meta: {
          requireAuth: false,
        }
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
