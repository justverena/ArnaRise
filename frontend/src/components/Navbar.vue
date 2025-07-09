<script setup>
import { ref, onMounted } from 'vue'

const isAdmin = ref(false)
const isAnalyst = ref(false)

onMounted(() => {
  const role = localStorage.getItem('role')
  isAdmin.value = role === 'admin'
  isAnalyst.value = role === 'analyst'
})
</script>

<template>
  <nav class="navbar">
    <div class="nav-links">
      <router-link to="/" class="nav-link">Главная</router-link>
      <router-link v-if="isAnalyst" to="/chart" class="nav-link">Графики</router-link>
      <router-link to="/profile" class="nav-link">Личный кабинет</router-link>
      <router-link v-if="isAdmin" to="/admin/register" class="nav-link">Регистрация пользователя</router-link>
    </div>
  </nav>
</template>

<style scoped>
.navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  width: 100%;
  height: 50px;
  background-color: #4caf50;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 2rem 2rem;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  z-index: 999;
}

.nav-links {
  display: flex;
  gap: 1.5rem;
}

.nav-link {
  color: white;
  text-decoration: none;
  font-weight: bold;
}

.nav-link:hover {
  text-decoration: underline;
  background-color: #009b6700;
}
</style>
