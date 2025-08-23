<template>
  <BaseModal v-model="isOpen" @close="$emit('close')">
    <template #header>
      Изменение отчета: Браки и разводы
    </template>

    <form @submit.prevent="submitUpdate">
        <BaseSelect
          v-model="form.reportYear"
          :options="reportYears"
          label="Отчетный год"
          placeholder="Выберите год"
          value-key="key"
          label-key="value"
        />

        <BaseSelect
          v-model="form.district"
          :options="districts"
          label="Район"
          placeholder="Выберите район"
          value-key="key"
          label-key="value"
        />

        <BaseInput
        v-model="form.marriageCount"
        label="Количество зарегистрированных браков:"
        type="number"
        min="1"
        required
        @input="calculateRate"
        />

        <BaseInput
        v-model="form.divorceCount"
        label="Количество разводов:"
        type="number"
        min="1"
        required
        @input="calculateRate"
        />

        <BaseInput
        :model-value="form.ratioDivorcesToMarriagePercent.toFixed(2)"
        label="Отношение разводов к бракам (%):"
        type="number"
        disabled />

        <BaseInput
        v-model="form.averageAge"
        label="Средний возраст вступающих в брак:"
        type="number"
        min="1"
        required
        @input="calculateRate"
        />

        <BaseSelect
          v-model="form.source"
          :options="sources"
          label="Источник"
          placeholder="Выберите источник"
          value-key="key"
          label-key="value"
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
import { getRejectedMarriageDivorceReports, editRejectedMarriageDivorceReport } from '@/services/marriageDivorce.service'
import BaseSelect from '@/components/common/BaseSelect.vue'
import BaseButton from '@/components/common/BaseButton.vue'
import BaseModal from '@/components/common/BaseModal.vue'
import BaseInput from '@/components/common/BaseInput.vue'
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
  reportYear: '',
  district: '',
  marriageCount: null,
  divorceCount: null,
  ratioDivorcesToMarriagePercent: 0,
  averageAge: null,
  source: ''
})

const reportYears = ref([])
const districts = ref([])
const sources = ref([])

onMounted(async () => {
  try {
    reportYears.value = await getEnum('report-year')
    districts.value = await getEnum('district')
    sources.value = await getEnum('source')
    await fetchReports()
  } catch (error) {
    console.error('Ошибка загрузки enum-ов:', error)
    alert('Не удалось загрузить данные. Проверьте консоль.')
    emit('close')
  }
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


const fetchReports = async () => {
    try {
        const { data } = await getRejectedMarriageDivorceReports()
        const report = data.find(r => r.id === reportId)
        if (!report) throw new Error('No reports')
        
        form.reportYear = report.reportYear
        form.district = report.district
        form.marriageCount = report.marriageCount
        form.divorceCount = report.divorceCount
        form.ratioDivorcesToMarriagePercent = report.ratioDivorcesToMarriagePercent || 0
        form.averageAge = report.averageAge
        form.source = report.source
        
    } catch (error) {
        console.error('oshibka pri poluchenii otcheta', error)
        alert('ne udalos zatruzit otchet. check console')
        emit('close')
    }
}

const submitUpdate = async () => {
  const updatedData = {
    reportYear: form.reportYear,
    district: form.district,
    marriageCount: form.marriageCount,
    divorceCount: form.divorceCount,
    ratioDivorcesToMarriagePercent: form.ratioDivorcesToMarriagePercent,
    averageAge: form.averageAge,
    source: form.source
  };

  try {
    await editRejectedMarriageDivorceReport(reportId, updatedData);
    alert('Отчет успешно обновлен и отправлен повторно.');
    emit('reportSubmitted');
    emit('close');
  } catch (error) {
    console.error('Ошибка отправки:', error);
    alert('Не удалось обновить отчет. Проверьте консоль.');
  }
};

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
