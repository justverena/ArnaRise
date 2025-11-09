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
  labels: {
    type: Array,
    required: true
  },
  values: {
    type: Array,
    required: true
  },
  label: {
    type: String,
    default: ''
  },
  colors: {
    type: Array,
    default: () => ['#42a5f5', '#66bb6a']
  },
  showPercent: {
    type:Boolean,
    default: false
  }
})

const chartData = computed(() => ({
  labels: props.labels,
  datasets: [
    {
      label: props.label,
      data: props.values,
      backgroundColor: props.colors
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
  width: 100%;
  max-width: 1000px;
  height: 400px;
  margin: auto;
}
</style>
