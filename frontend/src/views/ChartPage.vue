<template>
  <div class="chart-page">
    <!-- Верхняя панель -->
    <div class="top-bar">
      <button @click="updateChart">Обновить</button>

      <div class="dropdown-container">
        <button @click="toggleIndicators">Индикаторы</button>
        <div class="dropdown" v-if="showIndicators">
          <Indicators @indicator-selected="onIndicatorSelected" />
        </div>
      </div>

      <div class="dropdown-container">
        <button @click="toggleFilters">Фильтры</button>
        <div class="dropdown" v-if="showFilters">
          <Filters @filter-updated="onFiltersChanged" />
        </div>
      </div>
    </div>

    <!-- График -->
    <div class="chart-wrapper">
      <MyChart :key="chartKey" :labels="labels" :values="values" />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import MyChart from '../components/MyChart.vue'
import Filters from '../components/Filters.vue'
import Indicators from '../components/Indicators.vue'

const labels = ref(['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'])
const values = ref([100, 120, 90, 150, 130, 80, 70])
const chartKey = ref(0)

const showFilters = ref(false)
const showIndicators = ref(false)

const selectedFilters = ref({})
const selectedIndicator = ref('')

function updateChart() {
  values.value = Array.from({ length: 7 }, () => Math.floor(Math.random() * 200) + 10)
  chartKey.value++
}

function onFiltersChanged(filters) {
  selectedFilters.value = filters
  showFilters.value = false
  updateChart()
}

function onIndicatorSelected(indicator) {
  selectedIndicator.value = indicator
  showIndicators.value = false
  updateChart()
}

function toggleFilters() {
  showFilters.value = !showFilters.value
  showIndicators.value = false
}

function toggleIndicators() {
  showIndicators.value = !showIndicators.value
  showFilters.value = false
}
</script>

<style scoped>
.chart-page {
  padding: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.top-bar {
  display: flex;
  gap: 1rem;
  align-items: flex-start;
  margin-bottom: 1rem;
  position: relative;
}

button {
  padding: 0.6rem 1rem;
  background-color: #42a5f5;
  color: white;
  font-weight: bold;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

.dropdown-container {
  position: relative;
}

.dropdown {
  position: absolute;
  top: 110%;
  left: 0;
  z-index: 10;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 1rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  min-width: 300px;
  animation: fadeIn 0.25s ease-in-out;
}

.chart-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 2rem;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-6px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
