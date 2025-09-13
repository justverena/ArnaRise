<template>
  <div class="chart-page">
    <div class="main-content">
      <!-- Сайдбар с чекбоксами -->
      <nav class="sidebar">
        <div class="sidebar-actions">
          <BaseButton @click="updateChart" shape="square">Обновить</BaseButton>

          <BaseButton @click="toggleIndicators" shape="square">Индикаторы</BaseButton>
          <div class="dropdown" v-if="showIndicators">
            <Indicators @indicator-selected="onIndicatorSelected" />
          </div>

          <BaseButton @click="toggleFilters" shape="square">Фильтры</BaseButton>
          <div class="dropdown" v-if="showFilters">
            <Filters @filter-updated="onFiltersChanged" />
          </div>
        </div>


        <h3 class="sidebar-title">Типы графиков</h3>
        <ul>
          <li v-for="type in availableCharts" :key="type">
            <label class="chart-type-option">
              <input type="checkbox" v-model="activeCharts" :value="type" />
              {{ type }}
            </label>
          </li>
        </ul>

        
      </nav>

      <!-- Контент справа -->
      <div class="content">
        <div class="chart-wrapper">
          <!-- Line -->
          <div v-if="activeCharts.includes('Line')" class="chart line-chart">
            <component
              :is="currentChartComponent"
              :filters="selectedFilters"
              :active-charts="['Line']"
              :key="chartKey + '-line'"
            />
          </div>

          <!-- Pie -->
          <div v-if="activeCharts.includes('Pie')" class="chart pie-chart">
            <component
              :is="currentChartComponent"
              :filters="selectedFilters"
              :active-charts="['Pie']"
              :key="chartKey + '-pie'"
            />
          </div>

          <!-- Bar -->
          <div v-if="activeCharts.includes('Bar')" class="chart bar-chart">
            <component
              :is="currentChartComponent"
              :filters="selectedFilters"
              :active-charts="['Bar']"
              :key="chartKey + '-bar'"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import Filters from '@/features/filters/Filters.vue'
import Indicators from '@/features/analytics/components/Indicators.vue'
// индикаторы
import MarriageChart from '@/features/analytics/components/MarriageIndicator.vue'
import DivorceRatioChart from '@/features/analytics/components/DivorceRatioIndicator.vue'
import AvgAgeChart from '@/features/analytics/components/AvgAgeIndicator.vue'
import DivorceChart from '@/features/analytics/components/DivorceIndicator.vue'

import BaseButton from '@/components/common/BaseButton.vue'

const chartKey = ref(0)

const showFilters = ref(false)
const showIndicators = ref(false)

const selectedFilters = ref({  district: '' })
const selectedIndicator = ref('marriage')

// доступные графики
const availableCharts = ['Line', 'Bar', 'Pie']
const activeCharts = ref([...availableCharts]) // все включены по умолчанию

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
  chartKey.value++ // форсированный перерендер
}

function onFiltersChanged(filters) {
  selectedFilters.value = {
    district: filters.district?.key || '' 
  }
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

watch(selectedFilters, () => {
  updateChart()
}, {deep: true})
</script>

<style scoped>
.chart-page {
  padding: 1rem;
  max-width: 100%;
  margin: 0 auto;
  font-family: 'Inter', sans-serif;
}

.main-content {
  display: flex;
}

/* Сайдбар */
.sidebar {
  width: 220px;
  background: #f3f4f6;
  padding: 1rem;
  border-right: 1px solid #e5e7eb;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.sidebar-title {
  font-weight: 600;
  font-size: 1rem;
  margin-bottom: 0.5rem;
}

.sidebar ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.sidebar li {
  margin-bottom: 0.5rem;
}

.chart-type-option {
  font-size: 1.1rem;
  display: flex;
  align-items: center;
  gap: 1rem;
}

.sidebar-actions {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

/* Контент справа */
.content {
  flex: 1;
  padding: 1.5rem;
}

/* --- GRID для графиков --- */
.chart-wrapper {
  display: grid;
  grid-template-areas:
    "line pie"
    "bar bar";
  grid-template-columns: 2fr 1fr;
  grid-template-rows: 1fr 1fr;
  gap: 2rem;
  min-height: 600px;
}

.line-chart {
  grid-area: line;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.pie-chart {
  grid-area: pie;
  display: flex;
  justify-content: center;
  align-items: center;
}

.bar-chart {
  grid-area: bar;
}

.chart {
  background: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 1rem;
  box-shadow: 0 2px 6px rgba(0,0,0,0.05);
}

.dropdown {
  position: relative;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 8px;
  padding: 1rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  min-width: 200px;
}
</style>
