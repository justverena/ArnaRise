<template>
  <BaseModal v-model="isOpen" @close="$emit('close')">
  <template #header>
    <span v-if="mode === 'create'">Отчет: Гендерное насилие</span>
    <span v-else-if="mode === 'edit'">Изменение отчета: Гендерное насилие</span>
    <span v-else>Просмотр отчета: Гендерное насилие</span>
  </template>

  <div v-if="loading">Загрузка...</div>

  <form v-else-if="mode!== 'show'" @submit.prevent="handleSubmit">
    <BaseSelect
        v-model="form.district"
        :options="Districts"
        label="Район"
        placeholder="Выберите район"
        value-key="key"
        label-key="value"
        />
        
        <label>Дата инцидента:
          <input v-model="form.date" type="date" required />
        </label>


        <BaseSelect
        v-model="form.gender"
        :options="Genders"
        label="Пол"
        placeholder="Выберите пол"
        value-key="key"
        label-key="value"
        />

        <BaseInput
        v-model="form.age"
        label="Возраст:"
        type="number"
        min="1"
        required
        />

        <BaseSelect
        v-model="form.violenceType"
        :options="ViolenceTypes"
        label="Вид насилия"
        placeholder="Выберите вид насилия"
        value-key="key"
        label-key="value"
        />

        <BaseSelect
        v-model="form.location"
        :options="Locations"
        label="Место происшествия"
        placeholder="Выберите место происшествия"
        value-key="key"
        label-key="value"
        />

        <BaseSelect
        v-model="form.timeOfDay"
        :options="TimesOfDay"
        label="Время суток"
        placeholder="Выберите время суток"
        value-key="key"
        label-key="value"
        />

        <BaseSelect
        v-model="form.socialStatus"
        :options="SocialStatuses"
        label="Социальный статус"
        placeholder="Выберите социальный статус"
        value-key="key"
        label-key="value"
        />

        <BaseSelect
        v-model="form.aggressorRelation"
        :options="AggressorRelations"
        label="Отношения с агрессором"
        placeholder="Выберите отношения с агрессором"
        value-key="key"
        label-key="value"
        />

        <BaseTextarea
        v-model="form.caseDescription"
        label="Описание случая:"
        multiline
        required
        size="large"
        />

        <BaseSelect
        v-model="form.authority"
        :options="Authorities"
        label="Орган принявший меры"
        placeholder="Выберите орган принявший меры"
        value-key="key"
        label-key="value"
        />

        <BaseMultiSelect
        v-model="form.actions"
        :label="'Принятые меры'"
        :options="Actions"
        />


        <div class="form-actions">
          <BaseButton type="submit" >Отправить</BaseButton>
          <BaseButton @click="$emit('close')" type="button" variant="secondary">Закрыть</BaseButton>
        </div>
      </form>

      <div v-else>
        
        <div v-if="!report">Загрузка отчёта...</div>
        <div v-else>
            <BaseTable
                :columns="columns"
                :rows="rows"
                :id-key="'field'"
                />

        <div class="form-actions">
        <BaseButton v-if="showApproveReject" @click="$emit('approve')" size="sm">Одобрить</BaseButton>
        <BaseButton v-if="showApproveReject" @click="$emit('reject')" variant="danger" size="sm">Отклонить</BaseButton>
        <BaseButton type="button" @click="$emit('close')" variant="secondary">Закрыть</BaseButton>
      </div>
        </div>
        
      </div>
  </BaseModal>
</template>

<script setup>
import { reactive, ref, onMounted, computed} from 'vue'
import { getEnum } from '@/services/enumService'
import { watch } from 'vue'
import BaseSelect from '@/components/common/BaseSelect.vue'
import BaseMultiSelect from '@/components/common/BaseMultiSelect.vue'
import BaseButton from '@/components/common/BaseButton.vue'
import BaseModal from '@/components/common/BaseModal.vue'
import BaseInput from '@/components/common/BaseInput.vue'
import BaseTextarea from '@/components/common/BaseTextarea.vue'
import BaseTable from '@/components/common/BaseTable.vue'

const props = defineProps({
    mode: { type: String, default: 'show' },
    reportId: { type: String, default: null },
    showApproveReject: { type: Boolean, default: false },
    initialData: {type: Object, default: null }
})

const emit = defineEmits([ 'submit', 'close', 'approve', 'reject'])

const columns = [
    { label: 'Поле', key: 'field'},
    { label: 'Значение', key: 'value'}, 
]

const rows = computed(() => {
    if (!report.value) return []

    return [
    { field: 'ID', value: report.value.id },
    { field: 'Дата инцидента', value: report.value.date },
    { field: 'Пол', value: getEnumValue(Genders.value, report.value.gender) },
    { field: 'Возраст', value: report.value.age },
    { field: 'Вид насилия', value: getEnumValue(ViolenceTypes.value, report.value.violenceType) },
    { field: 'Место происшествия', value: getEnumValue(Locations.value, report.value.location) },
    { field: 'Время суток', value: getEnumValue(TimesOfDay.value, report.value.timeOfDay) },
    { field: 'Социальный статус', value: getEnumValue(SocialStatuses.value, report.value.socialStatus) },
    { field: 'Отношения с агрессором', value: getEnumValue(AggressorRelations.value, report.value.aggressorRelation) },
    { field: 'Описание случая', value: report.value.caseDescription },
    { field: 'Орган принявший меры', value: getEnumValue(Authorities.value, report.value.authority) },
    {
      field: 'Принятые меры',
      value: getEnumValues(Actions.value, report.value.actions).join(', ')
    },
    { field: 'Отправил', value: report.value.submittedBy },
    { field: 'Статус', value: getEnumValue(ReportStatuses.value, report.value.status) },
    report.value.rejectionReason
      ? { field: 'Причина отклонения', value: report.value.rejectionReason }
      : null
  ].filter(Boolean)
})

function handleSubmit() {
    emit('submit', { ...form })
}

const isOpen = ref(true)
const report = ref(null)
const loading = ref(false)

const form = reactive({
  gender: '',
  date: '',
  district: '',
  age: '',
  violenceType: '',
  location: '',
  timeOfDay: '',
  socialStatus: '',
  aggressorRelation: '',
  caseDescription: '',
  authority: '',
  actions: []
})

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

    watch(
        () => props.initialData,
        (newVal) => {
            if (newVal) {
                Object.assign(form, newVal)
                report.value = newVal
            }
        },
        { immediate: true }
    )
  } catch (error) {
    console.error('Ошибка загрузки enum-ов:', error)
    emit('close')
  } finally {
    loading.value = false
  }
})

</script>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
label {
  display: flex;
  flex-direction: column;
  font-weight: 500;
  gap: 0.3rem;
}
input[type="date"] {
  padding: 0.6rem 0.8rem;
  border: 1px solid #ccc;
  border-radius: 8px;
}
.details-table {
  width: 100%;
  border-collapse: collapse;
}
.details-table td {
  border: 1px solid #ddd;
  padding: 6px;
}
.form-actions {
  display: flex;
  justify-content: flex-start;
  gap: 1rem;
  margin-top: 1.5rem;
}
</style>
