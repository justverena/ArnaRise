<template>
  <BarChart
    :labels="['Мужчины', 'Женщины']"
    :values="[30, 27]"
    label="Средний возраст вступающих в брак"
    :colors="['#5161AC', '#E2383F']"
  />
</template>

<script setup>
import BarChart from '@/components/charts/BarChart.vue';
</script>
