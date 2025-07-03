<template>
  <div class="chart-page">
    <div class="card-container">
      <router-link to="" class="action-card">
        <div class="icon-placeholder"></div>
        <span>Проверить новые отчеты</span>
      </router-link>

      <router-link to="" class="action-card">
        <div class="icon-placeholder"></div>
        <span>Управление индикаторами</span>
      </router-link>

      <router-link to="" class="action-card">
        <div class="icon-placeholder"></div>
        <span>Шаблон Отчетов</span>
      </router-link>
    </div>

    <div class="logout-container">
      <button @click="logout" class="logout-btn">Выйти</button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

function logout() {
  localStorage.removeItem('token')
  router.push('/login')
}
</script>

<style scoped>
.chart-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem;
}

/* Контейнер с кнопками */
.card-container {
  display: flex;
  gap: 2rem;
  flex-wrap: wrap;
  justify-content: center;
  margin-bottom: 2rem;
}

/* Стиль одного блока-кнопки */
.action-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 240px;
  height: 160px;
  padding: 1rem;
  background-color: #f9f9f9;
  border: 2px solid #ccc;
  border-radius: 12px;
  text-align: center;
  text-decoration: none;
  color: #333;
  transition: all 0.2s ease-in-out;
  cursor: pointer;
}

.action-card:hover {
  background-color: #e8f5e9;
  border-color: #42b983;
  transform: scale(1.03);
}

.icon-placeholder {
  width: 100px;
  height: 100px;
  background-color: #ddd;
  border-radius: 8px;
  margin-bottom: 1rem;
}

/* Контейнер кнопки логаута выравнивается вправо */
.logout-container {
  width: 100%;
  display: flex;
  justify-content: flex-end;
  padding-right: 2rem;
}

/* Сама кнопка */
.logout-btn {
  padding: 0.6rem 1.2rem;
  background-color: #999;
  color: white;
  font-size: 1rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.logout-btn:hover {
  background-color: #e53935;
}
</style>
