<template>
   <div>
    <h1>Гендерное насилие</h1>

    <table class="report-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Отправил (ID пользователя)</th>
          <th>Действия</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="report in reports" :key="report.id">
          <td>{{ report.id }}</td>
          <td>{{ report.submittedBy }}</td>
          <td>
            <button @click="openReport(report.id)">Просмотр</button>
          </td>
        </tr>
      </tbody>
    </table>
    
    <ShowFormGenderViolenceReport
      v-if="selectedReportId"
      :report-id="selectedReportId"
      @close="selectedReportId = null"
      @updated="fetchReports"
    />
  </div>

</template>

<script setup>
import { ref, onMounted } from 'vue'
import {
  getAnalystGenderViolenceReports,
  approveAnalystGenderViolenceReport,
  rejectAnalystGenderViolenceReport} from '@/services/genderViolence.service'
import ShowFormGenderViolenceReport from './ShowFormGenderViolenceReport.vue'

const reports = ref([])
const selectedReportId = ref(null)

async function fetchReports() {
  try {
    const response = await getAnalystGenderViolenceReports()
    reports.value = response.data
  } catch (error) {
    console.error('Ошибка загрузки отчетов:', error)
    alert('Не удалось загрузить отчеты')
  }
}

function openReport(id) {
  selectedReportId.value = id
}

onMounted(fetchReports)
</script>


<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 1rem;
  max-width: 700px;
  width: 100%;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  font-family: 'Inter', sans-serif;
  overflow-x: auto;
}

h2 {
  margin-bottom: 1.5rem;
  font-size: 1.4rem;
}

table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
}

th, td {
  padding: 0.75rem;
  text-align: left;
  border: 1px solid #d1d5db;
}

button {
  padding: 6px 10px;
  border-radius: 4px;
  cursor: pointer;
}

.approve-btn {
  background-color: #4caf50;
  color: white;
  border: none;
}

.approve-btn:hover {
  background-color: #43a047;
}

.reject-btn {
  background-color: #e53935;
  color: white;
  border: none;
}

.reject-btn:hover {
  background-color: #c62828;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 1.5rem;
}

.cancel-btn {
  background-color: #ccc;
  color: #333;
  padding: 0.8rem 1.2rem;
  border: none;
  border-radius: 2rem;
  font-size: 1rem;
  cursor: pointer;
}

.cancel-btn:hover {
  background-color: #b0b0b0;
}
</style>
