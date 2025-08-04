<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <h2>Изменение отчета</h2>
      <form @submit.prevent="submitUpdate">
        <label>Район:
          <select v-model="form.district" required>
            <option disabled value="">Выберите</option>
            <option 
              v-for="district in Districts" 
              :key="district" 
              :value="district">
              <!-- {{ $t(`enums.District.${district}`) }} -->
              {{ district }}
            </option>
          </select>
        </label>

        <label>Дата инцидента:
          <input v-model="form.date" type="date" required />
        </label>

        <label>Пол:
          <select v-model="form.gender" required>
            <option disabled value="">Выберите</option>
            <option 
              v-for="gender in Genders" 
              :key="gender" 
              :value="gender">
              <!-- {{ $t(`enums.Gender.${gender}`) }} -->
              {{ gender }}
            </option>
          </select>
        </label>

        <label>Возраст:
          <input v-model="form.age" type="number" min="0" required />
        </label>

        <label>Тип насилия:
          <select v-model="form.violenceType" required>
            <option disabled value="">Выберите</option>
            <option 
              v-for="type in ViolenceTypes" 
              :key="type" 
              :value="type">
              <!-- {{ $t(`enums.ViolenceType.${type}`) }} -->
              {{ type }}
            </option>
          </select>
        </label>

        <label>Место происшествия:
          <select v-model="form.location" required>
            <option disabled value="">Выберите</option>
            <option 
              v-for="loc in LocationTypes" 
              :key="loc" 
              :value="loc">
              <!-- {{ $t(`enums.LocationType.${loc}`) }} -->
              {{ loc }}
            </option>
          </select>
        </label>

        <label>Время суток:
          <select v-model="form.timeOfDay" required>
            <option disabled value="">Выберите</option>
            <option 
              v-for="time in TimeOfDay" 
              :key="time" 
              :value="time">
              <!-- {{ $t(`enums.TimeOfDay.${time}`) }} -->
              {{ time }}
            </option>
          </select>
        </label>

        <label>Социальный статус:
          <select v-model="form.socialStatus" required>
            <option disabled value="">Выберите</option>
            <option 
              v-for="status in SocialStatuses" 
              :key="status" 
              :value="status">
              <!-- {{ $t(`enums.SocialStatus.${status}`) }} -->
              {{ status }}
            </option>
          </select>
        </label>

        <label>Отношения с агрессором:
          <select v-model="form.aggressorRelation" required>
            <option disabled value="">Выберите</option>
            <option 
              v-for="relation in AggressorRelations" 
              :key="relation" 
              :value="relation">
              <!-- {{ $t(`enums.AggressorRelation.${relation}`) }} -->
              {{ relation }}
            </option>
          </select>
        </label>

        <label>Описание случая:
          <textarea v-model="form.caseDescription" rows="3" />
        </label>

        <label>Орган, принявший меры:
          <select v-model="form.authority">
            <option disabled value="">Выберите</option>
            <option 
              v-for="auth in Authorities" 
              :key="auth" 
              :value="auth">
              <!-- {{ $t(`enums.Authority.${auth}`) }} -->
              {{ auth }}
            </option>
          </select>
        </label>

        <label>Принятые меры:
          <select v-model="form.action" required>
            <option disabled value="">Выберите</option>
            <option 
              v-for="act in ActionTaken" 
              :key="act" 
              :value="act">
              <!-- {{ $t(`enums.ActionTaken.${act}`) }} -->
              {{ act }}
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
import { onMounted, reactive } from 'vue'
import { editRejectedViolenceReport } from '@/services/api'
import api from '@/services/api'
import { 
  Genders, 
  ViolenceTypes, 
  LocationTypes, 
  Districts, 
  TimeOfDay, 
  SocialStatuses, 
  AggressorRelations, 
  Authorities, 
  ActionTaken 
} from '@/constants/enums'

const emit = defineEmits(['reportSubmitted', 'close'])

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
  action: ''
})

const fetchReports = async () => {
    try {
        const { data } = await api.get('/partner/reports/gender-violence/rejected')
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
        form.action = report.action
    } catch (error) {
        console.error('oshibka pri poluchenii otcheta', error)
        alert('ne udalos zatruzit otchet. check console')
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
        action: form.action,
    };



  try {
    await editRejectedViolenceReport(reportId, updatedData)
    alert('Отчет успешно отправлен!')
    emit('reportSubmitted')
    emit('close')
  } catch (error) {
    console.error('Ошибка отправки:', error)
    alert('Не удалось отправить отчет. Проверьте консоль.')
  }
}

onMounted(() => {
    fetchReports()
})
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

