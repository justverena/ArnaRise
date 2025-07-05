<template>
  <BarChart
    :labels="['2020', '2021', '2022', '2023']"
    :values="[1100, 1300, 1200, 1350]"
    label="Разводы"
    :colors="['#f44336']"
  />
</template>

<script setup>
import BarChart from '../charts/BarChart.vue'
</script>
