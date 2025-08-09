<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <h2>Ожидающие отчеты о гендерном насилии</h2>

      <table>
        <thead>
          <tr>
            <th>Дата инцидента</th>
            <th>Район</th>
            <th>Пол</th>
            <th>Тип насилия</th>
            <th>Действия</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="report in reports" :key="report.id">
            <td>{{ report.date }}</td>
            <td>{{ districts.find(d => d.key === report.district)?.value || report.district }}</td>
            <td>{{ genders.find(g => g.key === report.gender)?.value || report.gender }}</td>
            <td>{{ violenceTypes.find(vt => vt.key === report.violenceType)?.value || report.violenceType }}</td>
            <td>
              <button class="approve-btn" @click="approve(report.id)">Одобрить</button>
              <button class="reject-btn" @click="reject(report.id)">Отклонить</button>
            </td>
          </tr>
        </tbody>
      </table>

      <div class="form-actions">
        <button class="cancel-btn" @click="$emit('close')">Закрыть</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getEnum } from '@/services/enumService'
import {
  getAnalystGenderViolenceReports,
  approveAnalystGenderViolenceReport,
  rejectAnalystGenderViolenceReport,
  submitPartnerGenderViolenceReport} from '@/services/genderViolence.service'

const emit = defineEmits(['close'])

const reports = ref([])
const districts = ref([])
const genders = ref([])
const violenceTypes = ref([])

const fetchReports = async () => {
  try {
    const response = await getAnalystGenderViolenceReports()
    reports.value = response.data
  } catch (error) {
    console.error('Ошибка загрузки отчетов:', error)
    alert('Не удалось загрузить отчеты')
  }
}

const approve = async (id) => {
  try {
    await approveAnalystGenderViolenceReport(id)
    alert('Отчет одобрен')
    await fetchReports()
  } catch (error) {
    console.error('Ошибка при одобрении отчета:', error)
    alert('Не удалось одобрить отчет')
  }
}

const reject = async (id) => {
  const reason = prompt('Введите причину отклонения отчета:')
  if (!reason) return
  try {
    await rejectAnalystGenderViolenceReport(id, { rejectionReason: reason })
    alert('Отчет отклонен')
    await fetchReports()
  } catch (error) {
    console.error('Ошибка при отклонении отчета:', error)
    alert('Не удалось отклонить отчет')
  }
}

onMounted(async () => {
  try {
    districts.value = await getEnum('district')
    genders.value = await getEnum('gender')
    violenceTypes.value = await getEnum('violence-type')
    await fetchReports()
  } catch (error) {
    console.error('Ошибка загрузки enum-ов:', error)
  }
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
  max-width: 700px;
  width: 100%;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  font-family: 'Inter', sans-serif;
  overflow-x: auto;
}

h2 {
  margin-bottom: 1.5rem;
  font-size: 1.4rem;
}

table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
}

th, td {
  padding: 0.75rem;
  text-align: left;
  border: 1px solid #d1d5db;
}

button {
  padding: 6px 10px;
  border-radius: 4px;
  cursor: pointer;
}

.approve-btn {
  background-color: #4caf50;
  color: white;
  border: none;
}

.approve-btn:hover {
  background-color: #43a047;
}

.reject-btn {
  background-color: #e53935;
  color: white;
  border: none;
}

.reject-btn:hover {
  background-color: #c62828;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 1.5rem;
}

.cancel-btn {
  background-color: #ccc;
  color: #333;
  padding: 0.8rem 1.2rem;
  border: none;
  border-radius: 2rem;
  font-size: 1rem;
  cursor: pointer;
}

.cancel-btn:hover {
  background-color: #b0b0b0;
}
</style>
