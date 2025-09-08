<template>
  <GenderViolenceReport
    v-if="report"
    mode="show"
    :report-id="reportId"
    :initial-data="report"
    :show-approve-reject="true"
    @close="handleClose"
    @approve="approve"
    @reject="reject"
  />
</template>


<script setup>
import { ref, onMounted } from 'vue'
import GenderViolenceReport from './GenderViolenceReport.vue'
import {
  getAnalystGenderViolenceReportById,
  approveAnalystGenderViolenceReport,
  rejectAnalystGenderViolenceReport
} from '@/services/reports/genderViolence.service'

const props = defineProps({
  reportId: { type: String, required: true }
})
const emit = defineEmits(['close', 'updated'])

const report = ref(null)

function handleClose() {
  emit('close')
}

async function loadReport() {
  try {
    const res = await getAnalystGenderViolenceReportById(props.reportId)
    report.value = res.data
  } catch (err) {
    console.error('Ошибка загрузки отчёта:', err)
    alert('Не удалось загрузить отчет')
    emit('close')
  }
}

async function approve() {
  await approveAnalystGenderViolenceReport(report.value.id)
  emit('updated')
  emit('close')
}

async function reject() {
  const reason = prompt('Укажите причину отклонения:')
  if (reason) {
    await rejectAnalystGenderViolenceReport(report.value.id, reason)
    emit('updated')
    emit('close')
  }
}

onMounted(loadReport)
</script>
