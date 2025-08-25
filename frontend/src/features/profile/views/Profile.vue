<template>
  <div class="app">
    <header class="header">
      <div class="header-buttons">
        <BaseButton to="/admin/register" shape="square">Регистрация Пользователя</BaseButton>
        <BaseButton @click="logout" variant="danger" shape="square">Выйти</BaseButton>
      </div>
    </header>

    <main class="main">
      <section class="content">
        <UserListView />
      </section>
    </main>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import UserListView from '@/features/users/components/UserListTable.vue'
import BaseButton from '@/components/common/BaseButton.vue'

const router = useRouter()

function logout() {
  localStorage.removeItem('token')
  localStorage.removeItem('role')
  router.push('/login')
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap');

* {
  box-sizing: border-box;
}

body {
  margin: 0;
  font-family: 'Inter', sans-serif;
}

.app {
  display: flex;
  flex-direction: column;
  min-height: 50vh;
  background: #ffffff;
  width: 1000px;
  max-width: 1000px;
  height: 400px;
  margin: auto;
}

.header {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  padding: 1rem 2rem;
  background-color: white;
  border-bottom: 1px solid #e5e7eb;
}

.header-buttons {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.nav-button {
  display: inline-block;
  background-color: #4caf50;
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  text-decoration: none;
  font-size: 0.95rem;
  border: none;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.nav-button:hover {
  background-color: #43a047;
}

.logout {
  background-color: #999;
}

.logout:hover {
  background-color: #e53935;
}

.main {
  display: flex;
  padding: 2rem;
  gap: 2rem;
}

.content {
  flex: 1 1 100%;
  background: #f3f4f6;
  padding: 2rem;
  border-radius: 0.5rem;
  box-shadow: 0 0 5px rgba(0,0,0,0.05);
}
</style>
