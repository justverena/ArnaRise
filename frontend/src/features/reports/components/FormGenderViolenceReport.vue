<template>
  <GenderViolenceReport
    mode="create"
    @submit="submit"
    @close="$emit('close')"
  />
</template>

<script setup>
import GenderViolenceReport from './GenderViolenceReport.vue'
import { submitPartnerGenderViolenceReport } from '@/services/reports/genderViolence.service'

const emit = defineEmits(['reportSubmitted', 'close'])

async function submit(form) {
  try {
    await submitPartnerGenderViolenceReport({
      ...form,
      rejectionReason: '',
      status: 'PENDING'
    })
    alert('Отчет успешно отправлен!')
    emit('reportSubmitted')
    emit('close')
  } catch (error) {
    console.error('Ошибка отправки:', error)
    alert('Не удалось отправить отчет')
  }
}
</script>

<style scoped>

</style>