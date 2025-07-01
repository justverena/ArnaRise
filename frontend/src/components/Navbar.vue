<script setup>
import { ref, onMounted } from 'vue'

const isAdmin = ref(false)

onMounted(() => {
  const role = localStorage.getItem('role')
  isAdmin.value = role === 'admin'
})
</script>

<template>
  <nav class="navbar">
    <div class="nav-links">
      <router-link to="/" class="nav-link">Home</router-link>
      <router-link to="/chart" class="nav-link">Chart</router-link>
      <router-link to="/users" class="nav-link">Users</router-link>
      <router-link to="/profile" class="nav-link">Profile</router-link>
      <router-link v-if="isAdmin" to="/admin/register" class="nav-link">Register user</router-link>
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
  background-color: #42b983;
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
}
</style>
