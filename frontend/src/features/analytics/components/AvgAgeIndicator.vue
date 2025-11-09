<template>
  <div>
    <div v-if="labels.length && values.length">
      <LineChart
      v-if="activeCharts.includes('Line')"
      :labels="labels"
      :values="values"
      label="Средний возраст вступающих в брак (линейный график)"
      />

      <PieChart
      v-if="activeCharts.includes('Pie')"
      :labels="labels"
      :values="values"
      label="Средний возраст вступающих в брак (круговой график)"
      />

      <BarChart
      v-if="activeCharts.includes('Bar')"
      :labels="labels"
      :values="values"
      label="Средний возраст вступающих в брак (столбчатый график)"
      />


    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import BarChart from '@/components/charts/BarChart.vue';
import LineChart from '@/components/charts/LineChart.vue';
import PieChart from '@/components/charts/PieChart.vue';
import { fetchAvgAgeByYear } from '@/services/indicators/indicators.service';

const props = defineProps({
  activeCharts: {
    type: Array,
    default: () => ['Line', 'Bar', 'Pie']
  },
  filters: {
    type: Object,
    default: () => ({district: ""})
  },
  filters: {
    type: Object,
    default: () => ({ district: ""})
  }
})

const labels = ref([])
const values = ref([])

async function loadData() {
  try {
    const data = await fetchAvgAgeByYear(props.filters.district || null)
    labels.value = data.map(item => String(item.reportYear));
    values.value = data.map(item => item.marriageAvAge);
  } catch (error) {
    console.error("Ошибка загрузки данных для графика:", error);
  }
  
}

onMounted(loadData)
watch(() => props.filters, loadData, {deep:true})

</script>
