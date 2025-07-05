<template>
  <BarChart
    :labels="['Мужчины', 'Женщины']"
    :values="[30, 27]"
    label="Средний возраст вступающих в брак"
    :colors="['#42a5f5', '#e91e63']"
  />
</template>

<script setup>
import BarChart from '../charts/BarChart.vue'
</script>
