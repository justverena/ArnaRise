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
  labels: Array,
  values: Array,
  label: String
})

const chartData = computed(() => ({
  labels: props.labels,
  datasets: [{
    label: props.label,
    data: props.values,
    borderColor: '#42a5f5',
    fill: false,
    tension: 0.4
  }]
}))

const chartOptions = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: { legend: { position: 'top' } },
  scales: { y: { beginAtZero: true } }
}
</script>

<style scoped>
.chart-container {
  width: 800px;
  max-width: 1000px;
  height: 500px;
  margin: auto;
}
</style>
