<template>
  <MarriageDivorceReport
    mode="create"
    @submit="submit"
    @close="$emit('close')"
  />
</template> 

<script setup>
import MarriageDivorceReport from './MarriageDivorceReport.vue'
import { submitMarriageDivorceReport } from '@/services/reports/marriageDivorce.service'

const emit = defineEmits(['reportSubmitted', 'close'])

async function submit(form) {
  try {
    await submitMarriageDivorceReport({
      ...form,
      rejectionReason: '',
      status: 'PENDING'
    })
    alert('Отчет успешно отправлен')
    emit('reportSubmitted')
    emit('close')
  } catch (error) {
    console.error('', error)
    alert('')
  }
  
}

</script>

<style scoped>

</style>