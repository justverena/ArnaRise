<template>

  <div id="app">

    <button v-if="!isLoginPage" class="hamburger-btn" @click="toggleSidebar">
      ☰
    </button>
    
    <Navbar v-if="!isLoginPage" />

    <Sidebar v-if="!isLoginPage && sidebarVisible" @close="sidebarVisible=false" />

    <main :class="{ 'with-sidebar': !isLoginPage, 'login-content': isLoginPage }">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router';
import Sidebar from '@/components/Sidebar.vue'
import { ref, computed } from 'vue';
import Navbar from './components/Navbar.vue';

const route = useRoute()

const isLoginPage = computed(() => route.path === '/login')
const sidebarVisible = ref(false)

const toggleSidebar = () => {
  sidebarVisible.value = !sidebarVisible.value
}
</script>

<style scoped>

main {
  padding: 1.5rem;
  margin-top: 0;
}

#app {
  display:flex;

}

.hamburger-btn {
  position: fixed;
  top: 5rem;
  left: 1rem;
  font-size: 24px;
  background: none;
  border: none;
  cursor: pointer;
  z-index: 1001;
}

.with-sidebar {
  margin-left: 200px;
  padding: 2rem;
}

.login-content {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
}



</style>
