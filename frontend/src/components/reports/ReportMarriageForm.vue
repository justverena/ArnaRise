<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <h2>Отчет: Браки и разводы</h2>
      <form @submit.prevent="submit">

        <label>Отчетный год:
          <select v-model="form.reportYear" required>
            <option disabled value="">Выберите</option>
            <option v-for="year in reportYears" :key="year" :value="year">
              {{ year }}
            </option>
          </select>
        </label>

        <label>Район:
          <select v-model="form.district" required>
            <option disabled value="">Выберите</option>
            <option v-for="d in districts" :key="d" :value="d">{{ districtMap[d] }}</option>
          </select>
        </label>

        <label>Количество зарегистрированных браков:
          <input v-model.number="form.marriageCount" type="number" min="1" @input="calculateRate" required />
        </label>

        <label>Количество разводов:
          <input v-model.number="form.divorceCount" type="number" min="0" @input="calculateRate" required />
        </label>

        <label>Отношение разводов к бракам (%):
          <input :value="form.ratioDivorcesToMarriagePercent.toFixed(2)" type="number" disabled />
        </label>

        <label>Средний возраст вступающих в брак:
          <input v-model.number="form.averageAge" type="number" step="0.1" min="0" required />
        </label>

        <label>Источник:
          <select v-model="form.source" required>
            <option disabled value="">Выберите</option>
            <option v-for="s in sources" :key="s" :value="s">{{ sourceMap[s] }}</option>
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
import { reactive } from 'vue'
import api from '@/services/api'

const reportYears = [
  'Y2015', 'Y2016', 'Y2017', 'Y2018', 'Y2019',
  'Y2020', 'Y2021', 'Y2022', 'Y2023', 'Y2024', 'Y2025'
]

const districts = [
  'ALATAU', 'ALMALY', 'AUEZOV', 'BOSTANDYK',
  'ZHETISU', 'MEDEU', 'NAURYZBAY', 'TURKSIB'
]

const sources = [
  'NATIONAL_STATISTICS',
  'LOCAL_ADMINISTRATION',
  'MINISTRY_OF_JUSTICE',
  'CIVIL_REGISTRY',
  'OTHER'
]

const districtMap = {
  ALATAU: 'Алатау',
  ALMALY: 'Алмалы',
  AUEZOV: 'Ауэзов',
  BOSTANDYK: 'Бостандык',
  ZHETISU: 'Жетысу',
  MEDEU: 'Медеу',
  NAURYZBAY: 'Наурызбай',
  TURKSIB: 'Турксиб'
}

const sourceMap = {
  NATIONAL_STATISTICS: 'Нац. статистика',
  LOCAL_ADMINISTRATION: 'Акимат',
  MINISTRY_OF_JUSTICE: 'Минюст',
  CIVIL_REGISTRY: 'ЗАГС',
  OTHER: 'Другое'
}

const form = reactive({
  reportYear: '',
  district: '',
  marriageCount: null,
  divorceCount: null,
  ratioDivorcesToMarriagePercent: 0,
  averageAge: null,
  source: ''
})

const calculateRate = () => {
  const m = form.marriageCount
  const d = form.divorceCount

  if (m && d && m > 0) {
    form.ratioDivorcesToMarriagePercent = (d / m) * 100
  } else {
    form.ratioDivorcesToMarriagePercent = 0
  }
}

const submit = async () => {
  try {
    const payload = {
      reportYear: form.reportYear,
      district: form.district,
      marriageCount: form.marriageCount,
      divorceCount: form.divorceCount,
      ratioDivorcesToMarriagePercent: parseFloat(form.ratioDivorcesToMarriagePercent.toFixed(2)),
      averageAge: form.averageAge,
      source: form.source
    }

    await api.post('/partner/reports/marriage-divorce', payload)
    alert('Отчет по бракам и разводам успешно отправлен!')
  } catch (error) {
    console.error('Ошибка при отправке отчета:', error)
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
  max-width: 600px;
  width: 100%;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  font-family: 'Inter', sans-serif;
}

h2 {
  margin-bottom: 1.5rem;
  font-size: 1.4rem;
}

.form-box label {
  display: block;
  margin-bottom: 1rem;
  font-size: 0.95rem;
  font-weight: 500;
}

input[type="text"],
input[type="number"],
input[type="date"],
input[type="file"],
select {
  width: 100%;
  padding: 0.6rem;
  margin-top: 0.3rem;
  border: 1px solid #d1d5db;
  border-radius: 2rem;
  font-size: 0.95rem;
  background-color: #f9f9f9;
}

select {
  appearance: none;
}

.form-actions {
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
}

.cancel-btn:hover {
  background-color: #b0b0b0;
}
</style>
