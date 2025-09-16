<template>
  <div>
    <h1>{{ $t('report.marriageDivorce') }}</h1>

    <BaseTable
      :columns="columns"
      :rows="reports"
    >
      <template #actions="{ row }">
        <BaseButton @click="openReport(row.id)" variant="secondary" size="sm" shape="square">{{ $t('buttons.read') }}</BaseButton>
        <BaseButton @click="approve(row.id)" size="sm" shape="square">{{ $t('report.approve') }}</BaseButton>
      </template>
    </BaseTable>

    <ShowFormMarriageDivorceReport
      v-if="selectedReportId"
      :report-id="selectedReportId"
      @close="selectedReportId = null"
      @updated="fetchReports"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useI18n } from 'vue-i18n'
import { getAnalystMarriageDivorceReports } from '@/services/reports/marriageDivorce.service'
import BaseTable from '@/components/common/BaseTable.vue'
import BaseButton from '@/components/common/BaseButton.vue'
import ShowFormMarriageDivorceReport from './ShowFormMarriageDivorceReport.vue'
import { approveMarriageDivorceReport } from '@/services/reports/marriageDivorce.service'

const reports = ref([])
const selectedReportId = ref(null)

const {t} = useI18n()

const columns = [
  { label: 'ID', key: 'id' },
  {label: t('report.senderId'), key:'value'}
]

async function approve(id) {
  try {
    await approveMarriageDivorceReport(id)
    await loadReports() // обновляем таблицу после одобрения
  } catch (error) {
    console.error('Ошибка при одобрении отчёта:', error)
  }
}

async function fetchReports() {
  try {
    const res = await getAnalystMarriageDivorceReports()
    reports.value = res.data
  } catch (error) {
    console.error('', error)
    alert('')
  }
}

function openReport(id) {
  selectedReportId.value = id
}

onMounted(fetchReports)
</script>

<style scoped>
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
