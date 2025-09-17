<template>
  <div class="filters">
    <h3>{{ $t('filters.title') }}</h3>

    <form @submit.prevent="applyFilters">
      <BaseSelect
        v-model="localFilters.district"
        :options="districts"
        :label="$t('filters.selectDistrict')"
        placeholder="Выберите район"
        value-key="key"
        label-key="value"
        size="sm"
      />

      <BaseButton type="submit">
        {{ $t('filters.apply') }}
      </BaseButton>
    </form>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted, watch } from 'vue'
import { defineEmits } from 'vue'
import { getEnum } from '@/services/enumService.js'
import { useI18n } from 'vue-i18n'
import BaseSelect from '@/components/common/BaseSelect.vue'
import BaseButton from '@/components/common/BaseButton.vue'

const emit = defineEmits(['filter-updated'])
const districts = ref([])

const localFilters = reactive({
  district: ''
})

const { locale } = useI18n()

async function loadDistricts() {
  try {
    districts.value = await getEnum('district', 'ru') 
    // ⚠️ проверь: на бэке этот енам называется "district" (с маленькой), 
    // а не "District" (как ты в первом примере написал)
  } catch (e) {
    console.error('Ошибка загрузки районов:', e)
  }
}

function applyFilters() {
  emit('filter-updated', { ...localFilters })
}

onMounted(() => {
  loadDistricts()
})

watch(locale, (newLang) => {
  loadDistricts(newLang)
})
</script>


<style scoped>
.filters {
  padding: 1rem;
  background: #f4f4f4;
  border-radius: 10px;
  margin-bottom: 1rem;
}
form {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}
select {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 6px;
}
button {
  background-color: #42b983;
  color: white;
  padding: 0.6rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}
</style>
