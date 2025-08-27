<template>

  <BaseModal v-model="isOpen" @close="$emit('close')">
  <template #header>
    Отчет: Браки и разводы
  </template>

  <form @submit.prevent="submit">
        <BaseSelect
          v-model="form.reportYear"
          :options="ReportYears"
          label="Отчетный год"
          placeholder="Выберите год"
          value-key="key"
          label-key="value"
        />

        <BaseSelect
          v-model="form.district"
          :options="Districts"
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
          :options="Sources"
          label="Источник"
          placeholder="Выберите источник"
          value-key="key"
          label-key="value"
        />

        <div class="form-actions">
          <BaseButton type="submit" >Отправить</BaseButton>
          <BaseButton @click="$emit('close')" type="button" variant="secondary" >Закрыть</BaseButton>
        </div>
      </form>
  </BaseModal>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import BaseSelect from '@/components/common/BaseSelect.vue'
import { getEnum } from '@/services/enumService'
import { submitMarriageDivorceReport } from '@/services/marriageDivorce.service'
import BaseButton from '@/components/common/BaseButton.vue'
import BaseModal from '@/components/common/BaseModal.vue'
import BaseInput from '@/components/common/BaseInput.vue'
const emit = defineEmits(['reportSubmitted', 'close'])

const isOpen = ref(true)

const form = reactive({
  reportYear: '',
  district: '',
  marriageCount: null,
  divorceCount: null,
  ratioDivorcesToMarriagePercent: 0,
  averageAge: null,
  source: ''
})

const ReportYears = ref([])
const Districts = ref([])
const Sources = ref([])

onMounted(async () => {
  try {
    ReportYears.value = await getEnum('report-year')
    Districts.value = await getEnum('district')
    Sources.value = await getEnum('source')
  } catch (error) {
    console.error('Ошибка загрузки enum-ов:', error)
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

const submit = async () => {
  try {
    await submitMarriageDivorceReport({
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

input[type="date"],

.form-actions {
  display: flex;
  gap: 1rem;
  margin-top: 1.5rem;
}

</style>
