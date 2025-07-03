
<template>
  <div class="indicators-page">
    <h2>Индикаторы</h2>
    <div class="indicator-card" v-for="indicator in indicators" :key="indicator.label">
      <h3>{{ indicator.label }}</h3>
      <p>{{ indicator.value }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '@/services/api'

const indicators = ref([])

onMounted(async () => {
  try {
    const res = await api.get('/stats/indicators')
    indicators.value = res.data
  } catch (err) {
    console.error('Ошибка при загрузке индикаторов:', err)
  }
})
</script>

<style scoped>
.indicators-page {
  padding: 2rem;
}

.indicator-card {
  background: #f5f5f5;
  padding: 1rem;
  border-radius: 8px;
  margin-bottom: 1rem;
}
</style>


