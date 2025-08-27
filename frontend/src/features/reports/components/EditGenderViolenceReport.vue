<template>
  <BaseModal v-model="isOpen" @close="$emit('close')">
    <template #header>
      Изменение отчета: Гендерное насилие
    </template>

    <form @submit.prevent="submitUpdate">
      
      <BaseSelect
        v-model="form.district"
        :options="Districts"
        label="Район"
        placeholder="Выберите район"
        value-key="key"
        label-key="value"
        size="lg"
      />
        
      <label>
        Дата инцидента:
        <input v-model="form.date" type="date" required />
      </label>

      <BaseSelect
          v-model="form.gender"
          :options="Genders"
          label="Пол"
          value-key="key"
          label-key="value"
          size="sm"
        />

      <BaseInput
          v-model="form.age"
          label="Возраст:"
          type="number"
          size="small"
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
        size="lg"
      />

      <BaseSelect
        v-model="form.location"
        :options="Locations"
        label="Место происшесвтия"
        placeholder="Выберите место происшествия"
        value-key="key"
        label-key="value"
        size="lg"
      />

      <BaseSelect
        v-model="form.timeOfDay"
        :options="TimesOfDay"
        label="Время суток"
        placeholder="Выберите время суток"
        value-key="key"
        label-key="value"
        size="md"
      />

      <BaseSelect
        v-model="form.socialStatus"
        :options="SocialStatuses"
        label="Социальный статус"
        placeholder="Выберите социальный статус"
        value-key="key"
        label-key="value"
        size="md"
      />

      <BaseSelect
        v-model="form.aggressorRelation"
        :options="AggressorRelations"
        label="Отношения с агрессором"
        placeholder="Выберите отношения с агрессором"
        value-key="key"
        label-key="value"
        size="lg"
      />

      <BaseTextarea
        v-model="form.caseDescription"
        label="Описание случая:"
        size="large"
        required
        class="full-width"
      />

      <BaseSelect
        v-model="form.authority"
        :options="Authorities"
        label="Орган принявший меры"
        placeholder="Выберите орган принявший меры"
        value-key="key"
        label-key="value"
        size="md"
      />  

        <BaseMultiSelect
          v-model="form.actions"
          :label="'Принятые меры'"
          :options="Actions"
          size="md"
          border-size="sm"
        />

      <div class="form-actions">
        <BaseButton type="submit">Отправить</BaseButton>
        <BaseButton type="button" @click="$emit('close')" variant="secondary">Закрыть</BaseButton>
      </div>
    </form>
  </BaseModal>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { getRejectedGenderViolenceReports, editRejectedGenderViolenceReport } from '@/services/genderViolence.service'
import BaseSelect from '@/components/common/BaseSelect.vue'
import BaseMultiSelect from '@/components/common/BaseMultiSelect.vue'
import BaseButton from '@/components/common/BaseButton.vue'
import BaseModal from '@/components/common/BaseModal.vue'
import BaseInput from '@/components/common/BaseInput.vue'
import BaseTextarea from '@/components/common/BaseTextarea.vue'
import { getEnum } from '@/services/enumService'

const emit = defineEmits(['reportSubmitted', 'close'])

const isOpen = ref(true)

const props = defineProps({
    reportId: {
        type: String,
        required: true
    }
})

const reportId = props.reportId

const form = reactive({
  gender: '',
  date: '',
  district: '',
  age: null,
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

onMounted(async () => {
  try {
    Genders.value = await getEnum('gender')
    Districts.value = await getEnum('district')
    ViolenceTypes.value = await getEnum('violence-type')
    Locations.value = await getEnum('location-type')
    TimesOfDay.value = await getEnum('time-of-day')
    SocialStatuses.value = await getEnum('social-status')
    AggressorRelations .value = await getEnum('aggressor-relation')
    Authorities.value = await getEnum('authority')
    Actions.value = await getEnum('action-taken')
    await fetchReports()
  } catch (error) {
    console.error('Ошибка загрузки enum-ов:', error)
    alert('Не удалось загрузить данные. Проверьте консоль.')
    emit('close')
  }
})

const fetchReports = async () => {
    try {
        const { data } = await getRejectedGenderViolenceReports()
        const report = data.find(r => r.id === reportId)
        if (!report) throw new Error('No reports')

        form.district = report.district
        form.date = report.date
        form.gender = report.gender
        form.age = report.age
        form.violenceType = report.violenceType
        form.location = report.location
        form.timeOfDay = report.timeOfDay
        form.socialStatus = report.socialStatus
        form.aggressorRelation = report.aggressorRelation
        form.caseDescription = report.caseDescription
        form.authority = report.authority
        form.actions = report.actions || []
    } catch (error) {
        console.error('Ошибка при получении отчета.', error)
        alert('Неу удалось загрузить отчет. Проверьте консоль.')
        emit('close')
    }
}

const submitUpdate = async () => {
    const updatedData = {
        district: form.district,
        date: form.date,
        gender: form.gender,
        age: form.age,
        violenceType: form.violenceType,
        location: form.location,
        timeOfDay: form.timeOfDay,
        socialStatus: form.socialStatus,
        aggressorRelation: form.aggressorRelation,
        caseDescription: form.caseDescription,
        authority: form.authority,
        actions: form.actions,
    };

  try {
    await editRejectedGenderViolenceReport(reportId, updatedData)
    alert('Отчет успешно отправлен!')
    emit('reportSubmitted')
    emit('close')
  } catch (error) {
    console.error('Ошибка отправки:', error)
    alert('Не удалось отправить отчет. Проверьте консоль.')
  }
}
</script>

<style scoped>
form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
}

label {
  display: flex;
  flex-direction: column;
  font-size: 0.95rem;
  font-weight: 500;
  color: #333;
  gap: 0.3rem;
  width: 100%;
}

input[type="date"] {
  padding: 0.6rem 0.8rem;
  border: 1px solid #ccc;
  border-radius: 8px;
  font-size: 0.95rem;
  font-family: inherit;
  outline: none;
  transition: border-color 0.2s ease, box-shadow 0.2s ease;
}

input[type="date"]:focus {
  border-color: #3b82f6; 
  box-shadow: 0 0 0 2px rgba(59, 130, 246, 0.2);
}

.form-actions {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  margin-top: 1.5rem;
}
</style>