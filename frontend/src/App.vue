<template>

  <div id="app">

    <!--<button v-if="!isLoginPage" class="hamburger-btn" @click="toggleSidebar">
      ☰
    </button>-->
    
    <Navbar v-if="!isLoginPage" />

    <!--<Sidebar v-if="!isLoginPage && sidebarVisible" @close="sidebarVisible=false" />-->

    <main :class="{ 'with-sidebar': !isLoginPage, 'login-content': isLoginPage }">
      <div class="page-container">
        <router-view />
      </div>
      
    </main>
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router';
//import Sidebar from '@/components/Sidebar.vue'
import { ref, computed } from 'vue';
import Navbar from './components/layout/Navbar.vue';

const route = useRoute()

const isLoginPage = computed(() => route.path === '/login')
//const sidebarVisible = ref(false)

/*
const toggleSidebar = () => {
  sidebarVisible.value = !sidebarVisible.value
}
  */
</script>

<style scoped>

.page-container {
  width: 100%;
  max-width: 100%;
  margin: 0 ;
  padding: 0;
}


main {
  padding: 1.5rem;
  margin-top: 0;
}

#app {
  display:flex;
  flex-direction: column;
  align-items: center;

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
  margin-left: 0px;
  padding: 2rem;
  display: flex;
  justify-content: center;
}

.login-content {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
}



</style>
