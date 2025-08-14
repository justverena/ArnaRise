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
            <tr><td>Дата инцидента</td><td>{{ report.date }}</td></tr>
            <tr><td>Пол</td><td>{{ Genders.find(g => g.key === report.gender)?.value || report.gender }}</td></tr>
            <tr><td>Возраст</td><td>{{ report.age }}</td></tr>
            <tr><td>Вид насилия</td><td>{{ ViolenceTypes.find(vt => vt.key === report.violenceType)?.value || report.violenceType }}</td></tr>
            <tr><td>Место происшествия</td><td>{{ Locations.find(l => l.key === report.location)?.value || report.location }}</td></tr>
            <tr><td>Время суток</td><td>{{ TimesOfDay.find(tof => tof.key === report.timeOfDay)?.value || report.timeOfDay }}</td></tr>
            <tr><td>Социальный статус</td><td>{{ SocialStatuses.find(ss => ss.key === report.socialStatus)?.value || report.socialStatus }}</td></tr>
            <tr><td>Отношения с агрессором</td><td>{{ AggressorRelations.find(ar => ar.key === report.aggressorRelation)?.value || report.aggressorRelation }}</td></tr>
            <tr><td>Описание случая</td><td>{{ report.caseDescription }}</td></tr>
            <tr><td>Оргна принявший меры</td><td>{{ Authorities.find(a => a.key === report.authority)?.value || report.authority }}</td></tr>
            <tr><td>Принятые меры</td><td>{{ Actions.find(at => at.key === report.actions)?.value || report.actions }}</td></tr>
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
import { getAnalystGenderViolenceReportById, approveAnalystGenderViolenceReport, rejectAnalystGenderViolenceReport } from '@/services/genderViolence.service'
import { getEnum } from '@/services/enumService'

const props = defineProps({
  reportId: { type: String, required: true }
})
const emit = defineEmits(['close', 'updated'])

const report = ref(null)
const loading = ref(true)
const Genders = ref([])
const Districts = ref([])
const ViolenceTypes = ref([])
const Locations = ref([])
const TimesOfDay = ref([])
const SocialStatuses = ref([])
const AggressorRelations = ref([])
const Authorities = ref([])
const Actions = ref([])


async function loadReport() {
  loading.value = true
  const res = await getAnalystGenderViolenceReportById(props.reportId)
  report.value = res.data
  loading.value = false
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

onMounted(async () => {
  try {
    Genders.value = await getEnum('gender')
    Districts.value = await getEnum('district')
    ViolenceTypes.value = await getEnum('violence-type')
    Locations.value = await getEnum('location-type')
    TimesOfDay.value = await getEnum('time-of-day')
    SocialStatuses.value = await getEnum('social-status')
    AggressorRelations.value = await getEnum('aggressor-relation')
    Authorities.value = await getEnum('authority')
    Actions.value = await getEnum('action-taken')
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
