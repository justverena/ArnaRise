<template>
  <div class="app">
    <header class="header">
      <div class="header-buttons">
        <router-link to="/new-report" class="green-btn">Создать Отчет</router-link>
        <!--<button class="green-btn" @click="showHistory = true">История отчётов</button> -->
        <button class="green-btn" @click="showOrganizations = true">Организации</button>
        <button @click="logout" class="logout">Выйти</button>
      </div>
    </header>

    <main class="main">
      <section class="content">
        <ReportHistoryView v-if="showHistory" @close="showHistory = false" />
        
        <Organizations v-if="showOrganizations" @close="showOrganizations = false" />
      </section>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import ReportHistoryView from '@/features/reports/views/ReportHistoryView.vue'
import NewReport from '@/features/reports/views/NewReportView.vue'
import Organizations from '@/features/organizations/Organizations.vue'


const router = useRouter()
const showHistory = ref(true)
//const showNewReport = ref(false)
const showOrganizations = ref(false)

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

button {
  background-color: #999;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.95rem;
}

.green-btn {
  background-color: #4caf50;
}

.logout:hover {
  background-color: #E2383F;
}

router-link.green-btn,
a.green-btn {
  display: inline-block;
  background-color: #4caf50;
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  font-size: 0.95rem;
  text-decoration: none;
  cursor: pointer;
  text-align: center;
  border: none;
}

router-link.green-btn:hover,
a.green-btn:hover {
  background-color: #009B67;
}


.green-btn:hover {
  background-color: #009B67;
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
