<template>
  <div class="container">
    <header class="header">
      <h1 class="logo">Посмотреть Отчеты</h1>
      <button class="top-button" @click="goBack">Назад</button>
    </header>

    <div class="main-content">
      <!-- Левая панель -->
      <nav class="sidebar">
        <ul>
          <li 
            :class="{ active: selected === 'marriage' }" 
            @click="selected = 'marriage'"
          >
            Браки и разводы
          </li>
          <li 
            :class="{ active: selected === 'violence' }" 
            @click="selected = 'violence'"
          >
            Гендерное насилие
          </li>
        </ul>
      </nav>

      <!-- Правая область -->
      <div class="content">
        <ReportMarriageTable v-if="selected === 'marriage'" @close="selected = ''" />
        <ReportViolenceTable v-if="selected === 'violence'" @close="selected = ''" />

        <div v-if="!selected" class="placeholder">
          <p>Выберите категорию отчётов в меню слева</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import ReportViolenceTable from '@/features/reports/components/TableGenderViolenceReport.vue'
import ReportMarriageTable from '@/features/reports/components/TableMarriageDivorceReport.vue'

const router = useRouter()
const goBack = () => router.back()
const selected = ref('')
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  font-family: 'Inter', sans-serif;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background: white;
  border-bottom: 1px solid #e5e7eb;
}

.logo {
  font-weight: 700;
  font-size: 1.2rem;
}

.top-button {
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 0.5rem;
  cursor: pointer;
  font-size: 0.9rem;
}

/* Основная двухколоночная структура */
.main-content {
  display: flex;
  flex: 1;
}

/* Левая панель */
.sidebar {
  width: 250px;
  background: #f3f4f6;
  padding: 1rem 0;
  border-right: 1px solid #e5e7eb;
}

.sidebar ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.sidebar li {
  padding: 0.8rem 1.2rem;
  cursor: pointer;
  font-weight: 500;
  color: #333;
  transition: background 0.2s;
}

.sidebar li:hover {
  background: #e1e1e1;
}

.sidebar li.active {
  background: #4caf50;
  color: white;
}

/* Контент справа */
.content {
  flex: 1;
  padding: 1.5rem;
}

.placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  color: #777;
  font-size: 1rem;
  
}
</style>
