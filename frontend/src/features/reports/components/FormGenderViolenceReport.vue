<template>
  <BaseModal v-model="isOpen" @close="$emit('close')">
  <template #header>
    Отчет: Гендерное насилие
  </template>
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

        <BaseInput
        v-model="form.caseDescription"
        label="Описание случая:"
        multiline
        required
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
  </BaseModal>
</template>

<script setup>
import { reactive, ref, onMounted, isProxy } from 'vue'
import { submitPartnerGenderViolenceReport } from '@/services/reports/genderViolence.service'
import { getEnum } from '@/services/enumService'
import BaseSelect from '@/components/common/BaseSelect.vue'
import BaseMultiSelect from '@/components/common/BaseMultiSelect.vue'
import BaseButton from '@/components/common/BaseButton.vue'
import BaseModal from '@/components/common/BaseModal.vue'
import BaseInput from '@/components/common/BaseInput.vue'
import BaseTextarea from '@/components/common/BaseTextarea.vue'


const emit = defineEmits(['reportSubmitted', 'close'])

const isOpen = ref(true)

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

textarea {
  min-height: 80px;
  line-height: 1.5;
}

.form-actions {
  display: flex;
  gap: 1rem;
  margin-top: 1.5rem;
}

</style>

