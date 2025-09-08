<template>
  <GenderViolenceReport
    mode="edit"
    :reportId="reportId"
    :initialData="reportData"
    @close="handleClose"
    @submit="submitUpdate"
  />
</template>

<script setup>
import { onMounted, ref } from 'vue'
import GenderViolenceReport from './GenderViolenceReport.vue'
import { getRejectedGenderViolenceReports, editRejectedGenderViolenceReport } from '@/services/reports/genderViolence.service'

const props = defineProps({
  reportId: { type: String, required: true }
})

const emit = defineEmits(['close', 'reportSubmitted'])

const reportData = ref(null)

function handleClose() {
  emit('close')
}

async function fetchReport() {
  try {
    const { data } = await getRejectedGenderViolenceReports()
    const report = data.find(r => r.id === props.reportId)
    if (!report) throw new Error('Report not found')
    reportData.value = report
  } catch (err) {
    console.error('Ошибка загрузки отчёта:', err)
    alert('Не удалось загрузить отчет')
    emit('close')
  }
}

async function submitUpdate(updatedForm) {
  try {
    await editRejectedGenderViolenceReport(props.reportId, updatedForm)
    alert('Отчет успешно обновлён!')
    emit('reportSubmitted')
    emit('close')
  } catch (err) {
    console.error('Ошибка обновления:', err)
    alert('Не удалось обновить отчет')
  }
}

onMounted(fetchReport)
</script>
