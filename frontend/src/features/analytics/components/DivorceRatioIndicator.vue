<template>
  <div>
    <div v-if="labels.length && values.length">
      <LineChart
      v-if="activeCharts.includes('Line')"
      :labels="labels"
      :values="values"
      :showPercent="true"
      label="Отношение браков к разводам (линейный график)"
      />

      <PieChart
      v-if="activeCharts.includes('Pie')"
      :labels="labels"
      :values="values"
      :showPercent="true"
      label="Отношение браков к разводам (круговой график)"
      />

      <BarChart
      v-if="activeCharts.includes('Bar')"
      :labels="labels"
      :values="values"
      :showPercent="true"
      label="Отношение браков к разводам (столбчатый график)"
      />

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import BarChart from '@/components/charts/BarChart.vue';
import LineChart from '@/components/charts/LineChart.vue';
import PieChart from '@/components/charts/PieChart.vue';
import { fetchDivorceRatioByYear } from '@/services/indicators/indicators.service';

const props = defineProps({
  activeCharts: {
    type: Array,
    default: () => ['Line', 'Bar', 'Pie']
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
    const data = await fetchDivorceRatioByYear(props.filters.district || null)
    labels.value = data.map(item => String(item.reportYear))
    values.value = data.map(item => item.ratioDivorcesToMarriagePercent)
  } catch (error) {
    console.error("Ошибка загрузки данных для графика:", error);
  }
  
}

onMounted(loadData)
watch(() => props.filters, loadData, {deep:true})

</script>
