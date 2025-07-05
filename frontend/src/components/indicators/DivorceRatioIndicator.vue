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

// 📌 Регистрация необходимых компонентов Chart.js
ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
)

// 📥 Пропсы (можно использовать filters, если будешь фильтровать позже)
const props = defineProps({
  filters: {
    type: Object,
    required: false
  }
})

// 📊 Пример данных: ты позже заменишь это на реальные данные (с бэка или Pinia)
const chartData = computed(() => ({
  labels: ['Браки', 'Разводы'],
  datasets: [
    {
      label: 'Количество',
      data: [3200, 1200], // ✏️ Заменишь позже
      backgroundColor: ['#4caf50', '#f44336']
    }
  ]
}))

// ⚙️ Опции графика
const chartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      display: false
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
