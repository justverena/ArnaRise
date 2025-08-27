<template>
  <div>
    <h1>Браки и Разводы</h1>

    <BaseTable
      :columns="columns"
      :rows="reports"
    >
      <template #actions="{ row }">
        <BaseButton @click="openReport(row.id)" variant="secondary" size="sm" shape="square">Просмотр</BaseButton>
      </template>
    </BaseTable>

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
import BaseTable from '@/components/common/BaseTable.vue'
import BaseButton from '@/components/common/BaseButton.vue'
import ShowFormMarriageDivorceReport from './ShowFormMarriageDivorceReport.vue'

const reports = ref([])
const selectedReportId = ref(null)

const columns = [
  { label: 'ID', key: 'id' },
  { label: 'Отправил (ID пользователя)', key: 'submittedBy' }
]

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
