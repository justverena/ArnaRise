<template>
  <div class="filters">
    <h3>{{ $t('filters.title') }}</h3>

    <form @submit.prevent="applyFilters">
      <select v-model="localFilters.district">
        <option disabled value="">{{ $t('filters.selectDistrict') }}</option>
        <option v-for="district in districts" :key="district" :value="district">
          {{ district }}
        </option>
      </select>

      <button type="submit">{{ $t('filters.apply') }}</button>
    </form>
  </div>
</template>


<script setup>
import { reactive, ref, onMounted } from 'vue'
import { defineEmits } from 'vue'
import { getEnum } from '@/services/enumService.js'

const emit = defineEmits(['filter-updated'])
const districts = ref([])

const localFilters = reactive({
  district: ''
})

async function loadDistricts() {
  try {
    // enumName и lang подставь как у тебя на бэке
    districts.value = await getEnum('District', 'ru')
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
