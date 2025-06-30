<template>
  <aside class="sidebar">
    <router-link to="/">Home</router-link>
    <router-link to="/profile">Profile</router-link>
    <router-link to="/chart">Chart</router-link>
    <router-link to="/users">Users</router-link>
    
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
