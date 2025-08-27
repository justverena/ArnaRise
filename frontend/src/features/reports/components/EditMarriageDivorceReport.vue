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
        size="small"
        required
        @input="calculateRate"
        />

        <BaseInput
        v-model="form.divorceCount"
        label="Количество разводов:"
        type="number"
        min="1"
        size="small"
        required
        @input="calculateRate"
        />

        <BaseInput
        :model-value="form.ratioDivorcesToMarriagePercent.toFixed(2)"
        label="Отношение разводов к бракам (%):"
        type="number"
        size="small"
        disabled />

        <BaseInput
        v-model="form.averageAge"
        label="Средний возраст вступающих в брак:"
        type="number"
        min="1"
        size="small"
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
import { getRejectedMarriageDivorceReports, editRejectedMarriageDivorceReport } from '@/services/reports/marriageDivorce.service'
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
        console.error('Ошибка при получении отчета.', error)
        alert('Неу удалось загрузить отчет. Проверьте консоль.')
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

form {
  display: flexbox;
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

.form-actions {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  margin-top: 1.5rem;
}
</style>
