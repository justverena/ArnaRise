<script setup>
import { Bar } from 'vue-chartjs'
import { ref, watch } from 'vue'
import { Chart as ChartJS, BarElement, CategoryScale, LinearScale } from 'chart.js'

ChartJS.register(BarElement, CategoryScale, LinearScale)

const props = defineProps(['chartData', 'chartOptions'])

</script>

<template>
  <Bar :data="chartData" :options="chartOptions" />
</template>
