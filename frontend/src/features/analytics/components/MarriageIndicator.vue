<template>
  <LineChart
    :labels="['2020', '2021', '2022', '2023']"
    :values="[2400, 3100, 2900, 3300]"
    label="Зарегистрированные браки"
  />
</template>

<script setup>
import LineChart from '@/components/charts/LineChart.vue';
</script>
