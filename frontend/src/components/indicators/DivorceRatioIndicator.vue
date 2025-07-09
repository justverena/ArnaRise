<template>
  <BarChart
    :labels="['Браки', 'Разводы']"
    :values="[3100, 1300]"
    label="Отношение разводов к бракам"
    :colors="['#59C0D5', '#F47321']"
  />
</template>

<script setup>
import BarChart from '../charts/BarChart.vue'
</script>
