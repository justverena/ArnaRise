<template>
  <div class="chart-container">
    <Line :data="chartData" :options="chartOptions" />
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { Line } from 'vue-chartjs'
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  CategoryScale,
  LinearScale,
  PointElement
} from 'chart.js'

// ✅ Регистрация компонентов Chart.js
ChartJS.register(
  Title,
  Tooltip,
  Legend,
  LineElement,
  CategoryScale,
  LinearScale,
  PointElement
)

const props = defineProps({
  filters: {
    type: Object,
    required: false
  }
})

// 📊 Пример данных — замени на динамические позже
const chartData = computed(() => ({
  labels: ['2019', '2020', '2021', '2022', '2023'],
  datasets: [
    {
      label: 'Женщины',
      data: [23.5, 24.0, 24.4, 24.7, 25.1],
      borderColor: '#f06292',
      backgroundColor: '#f8bbd0',
      tension: 0.3,
      fill: false
    },
    {
      label: 'Мужчины',
      data: [26.8, 27.2, 27.5, 28.0, 28.4],
      borderColor: '#42a5f5',
      backgroundColor: '#bbdefb',
      tension: 0.3,
      fill: false
    }
  ]
}))

const chartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      position: 'top'
    }
  },
  scales: {
    y: {
      beginAtZero: false,
      title: {
        display: true,
        text: 'Возраст'
      }
    },
    x: {
      title: {
        display: true,
        text: 'Год'
      }
    }
  }
}
</script>

<style scoped>
.chart-container {
  width: 800px;
  height: 500px;
  margin: auto;
}
</style>
