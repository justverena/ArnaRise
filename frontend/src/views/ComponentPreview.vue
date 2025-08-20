<template>
  <div style="padding:2rem; max-width:820px; margin:0 auto;">
    <h2 style="margin-bottom:1rem;">Компоненты (превью)</h2>

    <BaseInput v-model="name" label="Имя" />

    <BaseSelect
      v-model="district"
      label="Район"
      :options="districts"
      placeholder="Выберите район"
    />

    <BaseSelect
      v-model="actions"
      label="Меры (множественный выбор)"
      :options="actionOptions"
      multiple
    />

    <BaseTextarea
      v-model="desc"
      label="Описание"
      :maxlength="200"
      showCounter
    />

    <div style="display:flex; gap:1rem; margin-top:1rem;">
      <BaseButton @click="open = true">Открыть модалку</BaseButton>
      <BaseButton type="cancel" @click="reset">Сбросить</BaseButton>
    </div>

    <BaseModal v-model="open">
      <template #header>Пример модалки</template>
      <p>Контент модального окна</p>
      <template #footer>
        <BaseButton type="cancel" @click="open=false">Закрыть</BaseButton>
        <BaseButton @click="submit">Сохранить</BaseButton>
      </template>
    </BaseModal>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import BaseInput from '@/components/common/BaseInput.vue'
import BaseSelect from '@/components/common/BaseSelect.vue'
import BaseTextarea from '@/components/common/BaseTextarea.vue'
import BaseButton from '@/components/common/BaseButton.vue'
import BaseModal from '@/components/common/BaseModal.vue'

const name = ref('')
const district = ref('')
const actions = ref([])
const desc = ref('')
const open = ref(false)

const districts = [
  { value: 'ALATAU', label: 'Алатау' },
  { value: 'ALMALY', label: 'Алмалы' },
  { value: 'AUEZOV', label: 'Ауэзов' }
]

const actionOptions = [
  { value: 'POLICE', label: 'Полиция' },
  { value: 'SHELTER', label: 'Укрытие' },
  { value: 'LEGAL_SUPPORT', label: 'Юридическая помощь' }
]

function reset() {
  name.value = ''
  district.value = ''
  actions.value = []
  desc.value = ''
}

function submit() {
  alert('Сохранено!')
  open.value = false
}
</script>
