<template>
  <BaseModal
    :model-value="true"
    @update:modelValue="$emit('close')"
    @close="$emit('close')"
  >
    <template #header>
      Отчёт: Гендерное насилие
    </template>

    <div v-if="loading">Загрузка...</div>

    <div v-else-if="report && report.id">
      <table class="details-table">
        <tbody>
          <tr><td>ID</td><td>{{ report.id }}</td></tr>
          <tr><td>Дата инцидента</td><td>{{ report.date }}</td></tr>
          <tr><td>Пол</td><td>{{ getEnumValue(Genders, report.gender) }}</td></tr>
          <tr><td>Возраст</td><td>{{ report.age }}</td></tr>
          <tr><td>Вид насилия</td><td>{{ getEnumValue(ViolenceTypes, report.violenceType) }}</td></tr>
          <tr><td>Место происшествия</td><td>{{ getEnumValue(Locations, report.location) }}</td></tr>
          <tr><td>Время суток</td><td>{{ getEnumValue(TimesOfDay, report.timeOfDay) }}</td></tr>
          <tr><td>Социальный статус</td><td>{{ getEnumValue(SocialStatuses, report.socialStatus) }}</td></tr>
          <tr><td>Отношения с агрессором</td><td>{{ getEnumValue(AggressorRelations, report.aggressorRelation) }}</td></tr>
          <tr><td>Описание случая</td><td>{{ report.caseDescription }}</td></tr>
          <tr><td>Орган принявший меры</td><td>{{ getEnumValue(Authorities, report.authority) }}</td></tr>
          <tr>
            <td>Принятые меры</td>
            <td>
              <ul>
                <li v-for="(action, index) in getEnumValues(Actions, report.actions)" :key="index">
                  {{ action }}
                </li>
              </ul>
            </td>
          </tr>
          <tr><td>Отправил</td><td>{{ report.submittedBy }}</td></tr>
          <tr><td>Статус</td><td>{{ getEnumValue(ReportStatuses, report.status) }}</td></tr>
          <tr v-if="report.rejectionReason"><td>Причина отклонения</td><td>{{ report.rejectionReason }}</td></tr>
        </tbody>
      </table>

    </div>

    <template #footer>
      <BaseButton @click="approve" size="sm" shape="square">Одобрить</BaseButton>
      <BaseButton @click="reject" variant="danger" size="sm" shape="square">Отклонить</BaseButton>
    </template>
  </BaseModal>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import BaseModal from '@/components/common/BaseModal.vue'
import BaseButton from '@/components/common/BaseButton.vue'
import {
  getAnalystGenderViolenceReportById,
  approveAnalystGenderViolenceReport,
  rejectAnalystGenderViolenceReport
} from '@/services/reports/genderViolence.service'
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
const ReportStatuses = ref([])

function getEnumValue(list, key) {
  if (!Array.isArray(list) || !key) return key
  return list.find(item => item.key === key)?.value || key
}

function getEnumValues(list, keys) {
  if (!Array.isArray(list) || !Array.isArray(keys)) return []
  return keys.map(key => list.find(item => item.key === key)?.value || key)
}

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
    ReportStatuses.value = await getEnum('report-status')

    await loadReport()
  } catch (error) {
    console.error('Ошибка загрузки enum-ов:', error)
  }
})
</script>

<style scoped>
.details-table {
  width: 100%;
  border-collapse: collapse;
}
.details-table td {
  border: 1px solid #ddd;
  padding: 6px;
}
</style>
