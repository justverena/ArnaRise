<template>
  <div>
    <h1>Браки и Разводы</h1>

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

    <ShowFormMarriageDivorceReport
      v-if="selectedReportId"
      :report-id="selectedReportId"
      @close="selectedReportId = null"
      @updated="loadReports"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAnalystMarriageDivorceReports } from '@/services/marriageDivorce.service'
import ShowFormMarriageDivorceReport from './ShowFormMarriageDivorceReport.vue'
const reports = ref([])
const selectedReportId = ref(null)

async function loadReports() {
  const res = await getAnalystMarriageDivorceReports()
  reports.value = res.data
}

function openReport(id) {
  selectedReportId.value = id
}

onMounted(loadReports)
</script>

<style scoped>
.report-table {
  width: 100%;
  border-collapse: collapse;
}

.report-table th, .report-table td {
  border: 1px solid #ddd;
  padding: 8px;
}

.report-table th {
  background-color: #f4f4f4;
}
</style>
