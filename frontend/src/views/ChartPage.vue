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

    <!-- Компонент графика -->
    <div class="chart-wrapper">
      <component
        :is="currentChartComponent"
        :filters="selectedFilters"
        :key="chartKey"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import Filters from '../components/Filters.vue'
import Indicators from '../components/Indicators.vue'

// ИМПОРТИРУЕМ компоненты графиков
import MarriageChart from '../components/indicators/MarriageIndicator.vue'
import DivorceRatioChart from '../components/indicators/DivorceRatioIndicator.vue'
import AvgAgeChart from '../components/indicators/AvgAgeIndicator.vue'
import DivorceChart from '@/components/indicators/DivorceIndicator.vue'

const chartKey = ref(0)

const showFilters = ref(false)
const showIndicators = ref(false)

const selectedFilters = ref({})
const selectedIndicator = ref('marriage') // начальный индикатор

const indicatorComponentMap = {
  marriage: MarriageChart,
  divorce: DivorceChart,
  divorceRatio: DivorceRatioChart,
  avgAge: AvgAgeChart
  
}

const currentChartComponent = computed(() => {
  return indicatorComponentMap[selectedIndicator.value] || MarriageChart
})

function updateChart() {
  chartKey.value++ // триггер перерендера
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
  min-height: 500px;
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
