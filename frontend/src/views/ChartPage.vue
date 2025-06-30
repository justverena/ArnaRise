<template>
  <div class="chart-page">
    <h1>Chart Page</h1>
    <button @click="updateChart">Update</button>
    <MyChart :key="chartKey" :labels="labels" :values="values" />

    <div class="actions-wrapper">
      <router-link to="" class="action-card"><span>All Indicators</span></router-link>
      <router-link to="" class="action-card"><span>All Filters</span></router-link>
    </div>
  </div>
</template>


<script setup>
import { ref } from 'vue'
import MyChart from '../components/MyChart.vue'

const labels = ref(['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'])
const values = ref([100, 120, 90, 150, 130, 80, 70])
const chartKey = ref(0)

function updateChart() {
  labels.value = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'];

  // Обновим только значения
  values.value = Array.from({ length: 7 }, () => Math.floor(Math.random() * 200) + 10);

  // Обновим ключ, чтобы перерендерить график
  chartKey.value++;
}
</script>

<style scoped>
.chart-page {
  width: 90vw;
  margin: 2rem auto ;
  padding: 2rem;
  background: #fff;
  border-radius: 8px;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.actions-wrapper {
  display: flex;
  gap: 1rem;
  margin-top: 2rem;
  width: 100%;
  justify-content: center;
}

.action-card {
  width: 200px;
  height: 100px;
  padding: 1rem;
  background-color: #f9f9f9;
  border: 2px solid #ccc;
  border-radius: 12px;
  text-align: center;
  text-decoration: none;
  color: #333;
  transition: all 0.2s ease-in-out;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.action-card:hover {
  background-color: #e8f5e9;
  border-color: #42b983;
  transform: scale(1.03);
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
