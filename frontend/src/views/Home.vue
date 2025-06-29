<template>
  <div class="home">
    <h1>Home Page</h1>
    <p>Добро пожаловать!</p>
    <router-link to="/chart">Graph</router-link>
    <router-link to="/users">Users table</router-link>
  </div>
</template>

<style scoped>
.home {
  color: #222; /* Тёмный текст */
  padding: 2rem;
  font-family: sans-serif;
}
</style>
