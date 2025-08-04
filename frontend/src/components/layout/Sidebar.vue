<script setup>
import { ref, onMounted } from 'vue'

const isAdmin = ref(false)

onMounted(() => {
  const role = localStorage.getItem('role')
  isAdmin.value = role === 'admin'
})
</script>

<template>
  <aside class="sidebar">
    <router-link to="/">Главная</router-link>
    <router-link to="/profile">Личный кабинет</router-link>
    <router-link to="/chart">Графики</router-link>
    <router-link v-if="isAdmin" to="/admin/register" class="nav-link">Регистрация пользователя</router-link>
    
  </aside>
</template>

<style scoped>
.sidebar {
  position: fixed;
  top: 50px;
  left: 0;
  width: 200px;
  height: 100vh;
  background-color: #f3f3f3;
  padding: 5rem;
  box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  gap: 1rem;
}


</style>
