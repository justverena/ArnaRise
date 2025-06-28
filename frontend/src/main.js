import './assets/main.css'
import router from './router'

import { createApp } from 'vue'
import App from './App.vue'

createApp(App).use(router).mount('#app')

//const app = createApp(App)
//app.use(router)
//app.mount('#app')
