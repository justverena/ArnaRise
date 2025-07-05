<template>
  <div class="marriage-indicator">
    <LineChart :labels="labels" :values="values" />
  </div>
</template>

<script setup>
import { computed } from 'vue'
import LineChart from '../charts/LineChart.vue' // ✅ базовый график

const props = defineProps({
  filters: {
    type: Object,
    required: false,
    default: () => ({})
  }
})

// ❗Заглушка данных — заменишь на реальные позже
const rawMarriageData = [
  { year: '2020', count: 2400 },
  { year: '2021', count: 3100 },
  { year: '2022', count: 2900 },
  { year: '2023', count: 3300 }
]

// 📌 Здесь можно будет применять фильтры к данным
const filteredData = computed(() => {
  // Пример фильтрации — пока возвращаем как есть
  return rawMarriageData
})

// 🎯 labels и values — то, что нужно базовому графику
const labels = computed(() => filteredData.value.map(entry => entry.year))
const values = computed(() => filteredData.value.map(entry => entry.count))
</script>

<style scoped>
.marriage-indicator {
  width: 100%;
}
</style>
