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
  labels: { type: Array, default: () => [] },
  values: { type: Array, default: () => [] },
  label: { type: String, default: "" },
  showPercent: { type:Boolean, default:false }
})

const chartData = computed(() => ({
  labels: props.labels,
  datasets: [
    {
      label: props.label,
      data: props.values,
      borderColor: '#59C0D5',
      fill: false,
      tension: 0.4
    }
  ]
}))

const chartOptions = computed(() => ({
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    legend: {
      position: 'top'
    },
    tooltip: {
      callbacks: {
        label: function (context) {
          let label = context.dataset.label ? context.dataset.label + ': ' : ''
          if (context.parsed.y !== null) {
            label += context.parsed.y
            if (props.showPercent) {
              label += '%'
            }
          }
          return label
        }
      }
    }
  },
  scales: {
    y: {
      beginAtZero: true
    }
  }
}))
</script>

<style scoped>
.chart-container {
  width: 800px;
  max-width: 1000px;
  height: 450px;
  margin: auto;
}
</style>
