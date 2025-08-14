<template>
  <div class="modal-overlay" @click.self="$emit('close')">
    <div class="modal-content">
      <button class="close-btn" @click="$emit('close')">✕</button>
      <h2>Отчёт: Браки и Разводы</h2>

      <div v-if="loading">Загрузка...</div>

      <div v-else-if="report && report.id">
        <table class="details-table">
          <tbody>
            <tr><td>ID</td><td>{{ report.id }}</td></tr>
            <tr><td>Год</td><td>{{ report.reportYear.replace('Y', '') }}</td></tr>
            <tr><td>Район</td><td>{{ districts.find(d => d.key === report.district)?.value || report.district }}</td></tr>
            <tr><td>Количество браков</td><td>{{ report.marriageCount }}</td></tr>
            <tr><td>Количество разводов</td><td>{{ report.divorceCount }}</td></tr>
            <tr><td>Процент разводов к бракам</td><td>{{ report.ratioDivorcesToMarriagePercent }}</td></tr>
            <tr><td>Средний возраст</td><td>{{ report.averageAge }}</td></tr>
            <tr><td>Источник</td><td>{{ sources.find(s => s.key === report.source)?.value || report.source }}</td></tr>
            <tr><td>Отправил</td><td>{{ report.submittedBy }}</td></tr>
            <tr><td>Статус</td><td>{{ report.status }}</td></tr>
            <tr v-if="report.rejectionReason"><td>Причина отклонения</td><td>{{ report.rejectionReason }}</td></tr>
          </tbody>
        </table>

        <div class="actions">
          <button class="approve-btn" @click="approve">Одобрить</button>
          <button class="reject-btn" @click="reject">Отклонить</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAnalystMarriageDivorceReportById, approveMarriageDivorceReport, rejectMarriageDivorceReport } from '@/services/marriageDivorce.service'
import { getEnum } from '@/services/enumService'

const props = defineProps({
  reportId: { type: String, required: true }
})
const emit = defineEmits(['close', 'updated'])

const report = ref(null)
const loading = ref(true)
const districts = ref([])
const sources = ref([])

async function loadReport() {
  loading.value = true
  const res = await getAnalystMarriageDivorceReportById(props.reportId)
  report.value = res.data
  loading.value = false
}

async function approve() {
  await approveMarriageDivorceReport(report.value.id)
  emit('updated')
  emit('close')
}

async function reject() {
  const reason = prompt('Укажите причину отклонения:')
  if (reason) {
    await rejectMarriageDivorceReport(report.value.id, reason)
    emit('updated')
    emit('close')
  }
}

onMounted(async () => {
  try {
    districts.value = await getEnum('district')
    sources.value = await getEnum('source')
    await loadReport()
  } catch (error) {
    console.error('Ошибка загрузки enum-ов:', error)
  }
})
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0,0,0,0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}
.modal-content {
  background: white;
  padding: 1.5rem;
  border-radius: 10px;
  max-width: 600px;
  width: 100%;
  position: relative;
}
.close-btn {
  position: absolute;
  top: 10px;
  right: 15px;
  border: none;
  background: none;
  font-size: 20px;
  cursor: pointer;
}
.details-table {
  width: 100%;
  border-collapse: collapse;
}
.details-table td {
  border: 1px solid #ddd;
  padding: 6px;
}
.actions {
  margin-top: 1rem;
  display: flex;
  gap: 10px;
}
.approve-btn {
  background-color: #4CAF50;
  color: white;
  padding: 0.6rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.reject-btn {
  background-color: #f44336;
  color: white;
  padding: 0.6rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
</style>
