<template>
  <div class="chart-page">
    <h1>Chart Page</h1>
    <button @click="updateChart">Update</button>
    <MyChart :key="chartKey" :labels="labels" :values="values" />
    <router-link to="">All Indicators</router-link>
    <router-link to="">All Filters</router-link>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import MyChart from '../components/MyChart.vue'

const labels = ref(['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'])
const values = ref([100, 120, 90, 150, 130, 80, 70])
const chartKey = ref(0)

function updateChart() {
  labels.value = labels.value.map(day => `${day} ${Math.floor(Math.random() * 10)}`)
  values.value = Array.from({ length: 7 }, () => Math.floor(Math.random() * 200) + 10)
  chartKey.value++
}
</script>

<style scoped>
/* ✅ добавим отступы и рамку для наглядности */
.chart-page {
  padding: 2rem;
  max-width: 700px;
  margin: auto;
  background: #fefefe;
  border: 1px solid #ccc;
  border-radius: 8px;
}

button {
  margin: 1rem 0;
  padding: 0.5rem 1rem;
  background: #42a5f5;
  border: none;
  color: white;
  border-radius: 8px;
  cursor: pointer;
}
</style>
