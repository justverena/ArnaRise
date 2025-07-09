/*import './assets/main.css'
import router from './router'

import { createApp } from 'vue'
import App from './App.vue'

import { createPinia } from 'pinia'
const pinia = createPinia()
App.use(pinia)


createApp(App).use(router).mount('#app')


//const app = createApp(App)
//app.use(router)
//app.mount('#app') */

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
//import i18n from './i18n'

import './assets/main.css'

const app = createApp(App)

const pinia = createPinia()
app.use(pinia)
app.use(router)
//app.use(i18n)

app.mount('#app')
