<template>
  <div>
    <h1>{{ $t('report.genderViolence') }}</h1>

    <BaseTable
      :columns="columns"
      :rows="reports"
    >
      <template #actions="{ row }">
        <BaseButton @click="openReport(row.id)" variant="secondary" size="sm" shape="square">{{ $t('buttons.read') }}</BaseButton>
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
import { getAnalystGenderViolenceReports } from '@/services/reports/genderViolence.service'
import BaseTable from '@/components/common/BaseTable.vue'
import ShowFormGenderViolenceReport from './ShowFormGenderViolenceReport.vue'
import BaseButton from '@/components/common/BaseButton.vue'
import { useI18n } from 'vue-i18n'
import { approveAnalystGenderViolenceReport } from '@/services/reports/genderViolence.service'

const reports = ref([])
const selectedReportId = ref(null)

const {t} = useI18n()

const columns = [
  { label: 'ID', key: 'id' },
  {label: t('report.senderId'), key:'value'}
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
