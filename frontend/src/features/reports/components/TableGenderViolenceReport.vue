<template>
  <div>
    <h1>Гендерное насилие</h1>

    <BaseTable
      :columns="columns"
      :rows="reports"
    >
      <template #actions="{ row }">
        <BaseButton @click="openReport(row.id)" variant="secondary" size="sm" shape="square">Просмотр</BaseButton>
      </template>
    </BaseTable>

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
import { getAnalystGenderViolenceReports } from '@/services/genderViolence.service'
import BaseTable from '@/components/common/BaseTable.vue'
import ShowFormGenderViolenceReport from './ShowFormGenderViolenceReport.vue'
import BaseButton from '@/components/common/BaseButton.vue'

const reports = ref([])
const selectedReportId = ref(null)

const columns = [
  { label: 'ID', key: 'id' },
  { label: 'Отправил (ID пользователя)', key: 'submittedBy' }
]

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
/* Можно переиспользовать стили из BaseTable или дополнить */
button {
  padding: 6px 10px;
  border-radius: 4px;
  cursor: pointer;
  border: none;
}

button:hover {
  background-color: #f0f0f0;
}
</style>
