<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <h2>Отчет: Гендерное насилие</h2>
      <form @submit.prevent="submit">
        <!-- Район -->
        <label>Район:
          <select v-model="form.district" required>
            <option disabled value="">Выберите</option>
            <option 
              v-for="district in Districts" 
              :key="district" 
              :value="district">
              {{ $t(`enums.District.${district}`) }}
            </option>
          </select>
        </label>

        <!-- Дата -->
        <label>Дата инцидента:
          <input v-model="form.date" type="date" required />
        </label>

        <!-- Пол -->
        <label>Пол:
          <select v-model="form.gender" required>
            <option disabled value="">Выберите</option>
            <option 
              v-for="gender in Genders" 
              :key="gender" 
              :value="gender">
              {{ $t(`enums.Gender.${gender}`) }}
            </option>
          </select>
        </label>

        <!-- Возраст -->
        <label>Возраст:
          <input v-model="form.age" type="number" min="0" required />
        </label>

        <!-- Тип насилия -->
        <label>Тип насилия:
          <select v-model="form.violenceType" required>
            <option disabled value="">Выберите</option>
            <option 
              v-for="type in ViolenceTypes" 
              :key="type" 
              :value="type">
              {{ $t(`enums.ViolenceType.${type}`) }}
            </option>
          </select>
        </label>

        <!-- Место происшествия -->
        <label>Место происшествия:
          <select v-model="form.location" required>
            <option disabled value="">Выберите</option>
            <option 
              v-for="loc in LocationTypes" 
              :key="loc" 
              :value="loc">
              {{ $t(`enums.LocationType.${loc}`) }}
            </option>
          </select>
        </label>

        <!-- Время суток -->
        <label>Время суток:
          <select v-model="form.timeOfDay" required>
            <option disabled value="">Выберите</option>
            <option 
              v-for="time in TimeOfDay" 
              :key="time" 
              :value="time">
              {{ $t(`enums.TimeOfDay.${time}`) }}
            </option>
          </select>
        </label>

        <!-- Социальный статус -->
        <label>Социальный статус:
          <select v-model="form.socialStatus" required>
            <option disabled value="">Выберите</option>
            <option 
              v-for="status in SocialStatuses" 
              :key="status" 
              :value="status">
              {{ $t(`enums.SocialStatus.${status}`) }}
            </option>
          </select>
        </label>

        <!-- Отношения с агрессором -->
        <label>Отношения с агрессором:
          <select v-model="form.aggressorRelation" required>
            <option disabled value="">Выберите</option>
            <option 
              v-for="relation in AggressorRelations" 
              :key="relation" 
              :value="relation">
              {{ $t(`enums.AggressorRelation.${relation}`) }}
            </option>
          </select>
        </label>

        <!-- Описание -->
        <label>Описание случая:
          <textarea v-model="form.caseDescription" rows="3" />
        </label>

        <!-- Орган -->
        <label>Орган, принявший меры:
          <select v-model="form.authority">
            <option disabled value="">Выберите</option>
            <option 
              v-for="auth in Authorities" 
              :key="auth" 
              :value="auth">
              {{ $t(`enums.Authority.${auth}`) }}
            </option>
          </select>
        </label>

        <!-- Меры -->
        <label>Принятые меры:
          <select v-model="form.action" required>
            <option disabled value="">Выберите</option>
            <option 
              v-for="act in ActionTaken" 
              :key="act" 
              :value="act">
              {{ $t(`enums.ActionTaken.${act}`) }}
            </option>
          </select>
        </label>

        <div class="form-actions">
          <button class="submit-btn" type="submit">Отправить</button>
          <button class="cancel-btn" type="button" @click="$emit('close')">Закрыть</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive, computed } from 'vue'
import { useI18n } from 'vue-i18n'
import { submitPartnerGenderViolenceReport } from '@/services/api'
import { Genders, ViolenceTypes, LocationTypes, Districts, TimeOfDay, SocialStatuses, AggressorRelations, Authorities, ActionTaken } from '@/constants/enums'

const emit = defineEmits(['reportSubmitted', 'close'])

// 🎯 Получаем функцию $t
const { t } = useI18n()

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
  action: ''
})

// Опции enum'ов из i18n
const districtOptions = computed(() => Object.keys(t('enums.District')))
const genderOptions = computed(() => Object.keys(t('enums.Gender')))
const violenceTypeOptions = computed(() => Object.keys(t('enums.ViolenceType')))
const locationOptions = computed(() => Object.keys(t('enums.LocationType')))
const timeOfDayOptions = computed(() => Object.keys(t('enums.TimeOfDay')))
const socialStatusOptions = computed(() => Object.keys(t('enums.SocialStatus')))
const aggressorRelationOptions = computed(() => Object.keys(t('enums.AggressorRelation')))
const authorityOptions = computed(() => Object.keys(t('enums.Authority')))
const actionOptions = computed(() => Object.keys(t('enums.ActionTaken')))


const submit = async () => {
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

