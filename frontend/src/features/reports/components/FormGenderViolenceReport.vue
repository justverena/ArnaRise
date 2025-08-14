<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <h2>Отчет: Гендерное насилие</h2>
      <form @submit.prevent="submit">
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

        <label>Возраст:
          <input v-model="form.age" type="number" min="0" required />
        </label>

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

        <label>Описание случая:
          <textarea v-model="form.caseDescription" rows="3" />
        </label>

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
          <button class="submit-btn" type="submit">Отправить</button>
          <button class="cancel-btn" type="button" @click="$emit('close')">Закрыть</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import { submitPartnerGenderViolenceReport } from '@/services/genderViolence.service'
import { getEnum } from '@/services/enumService'
import BaseSelect from '@/components/common/BaseSelect.vue'
import BaseMultiSelect from '@/components/common/BaseMultiSelect.vue'


const emit = defineEmits(['reportSubmitted', 'close'])

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
  } catch (error) {
    console.error('Ошибка загрузки enum-ов:', error)
  }
})

const submit = async () => {
  try {
    //console.log('Отправляемые данные:', { ...form, rejectionReason: '', status: 'PENDING' })
    //console.log('Выбранные меры:', form.actions)


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
    alert('Не удалось отправить отчет. Проверьте консоль.')
  }
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 1rem;
  max-width: 1000px;
  width: 800px;
  height: 800px;
  overflow-y: auto;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  font-family: 'Inter', sans-serif;
}

h2 {
  margin-bottom: 1.5rem;
  font-size: 1.6rem;
  font-weight: 600;
}

form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

label {
  display: flex;
  flex-direction: column;
  font-size: 0.95rem;
  font-weight: 500;
  color: #333;
}

input[type="text"],
input[type="number"],
input[type="date"],
select,
textarea {
  width: 100%;
  padding: 0.6rem 0.8rem;
  margin-top: 0.4rem;
  border: 1px solid #d1d5db;
  border-radius: 0.75rem;
  font-size: 1rem;
  background-color: #f9f9f9;
  font-family: inherit;
  resize: vertical;
}

select {
  appearance: none;
}

textarea {
  min-height: 80px;
  line-height: 1.5;
}

.form-action {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  margin-top: 1.5rem;
}

.submit-btn {
  flex: 1;
  background-color: #4caf50;
  color: white;
  padding: 0.8rem;
  border: none;
  border-radius: 2rem;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-btn:hover {
  background-color: #43a047;
}

.cancel-btn {
  flex: 1;
  background-color: #ccc;
  color: #333;
  padding: 0.8rem;
  border: none;
  border-radius: 2rem;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.cancel-btn:hover {
  background-color: #b0b0b0;
}
</style>

