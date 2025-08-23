<template>
  <div class="chart-page">
    <!-- upper panel -->
    <div class="top-bar">
     <BaseButton @click="updateChart" shape="square">Обновить</BaseButton>

      <div class="dropdown-container">
        <BaseButton @click="toggleIndicators" shape="square">Индикаторы</BaseButton>
        <div class="dropdown" v-if="showIndicators">
          <Indicators @indicator-selected="onIndicatorSelected" />
        </div>
      </div>

      <div class="dropdown-container">
        <BaseButton @click="toggleFilters" shape="square">Фильтры</BaseButton>
        <div class="dropdown" v-if="showFilters">
          <Filters @filter-updated="onFiltersChanged" />
        </div>
      </div>
    </div>

    <!-- graph component -->
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
import Filters from '@/features/filters/Filters.vue'
import Indicators from '@/features/analytics/components/Indicators.vue'
//indicators

import MarriageChart from '@/features/analytics/components/MarriageIndicator.vue'
import DivorceRatioChart from '@/features/analytics/components/DivorceRatioIndicator.vue'
import AvgAgeChart from '@/features/analytics/components/AvgAgeIndicator.vue'
import DivorceChart from '@/features/analytics/components/DivorceIndicator.vue'

import BaseButton from '@/components/common/BaseButton.vue'

const chartKey = ref(0)

const showFilters = ref(false)
const showIndicators = ref(false)

const selectedFilters = ref({})
const selectedIndicator = ref('marriage') 

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
  chartKey.value++ // prerender trigger
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
  background-color: #6CC069;
  color: white;
  font-weight: bold;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}

button:hover {
  background-color: #009B67;
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
