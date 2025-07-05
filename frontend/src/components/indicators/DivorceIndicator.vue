<template>
  <div class="chart-container">
    <Bar :data="chartData" :options="chartOptions" />
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { Bar } from 'vue-chartjs'
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
} from 'chart.js'

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
)

const props = defineProps({
  filters: {
    type: Object,
    required: false
  }
})

// Пример данных, позже заменишь на API
const chartData = computed(() => ({
  labels: ['2020', '2021', '2022', '2023'],
  datasets: [
    {
      label: 'Разводы',
      data: [1200, 1300, 1250, 1400],
      backgroundColor: '#f44336'
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
      beginAtZero: true
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
