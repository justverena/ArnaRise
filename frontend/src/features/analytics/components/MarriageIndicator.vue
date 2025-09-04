<template>
  <div>
    <div v-if="labels.length && values.length">
      <LineChart
        v-if="activeCharts.includes('Line')"
        :labels="labels"
        :values="values"
        label="Зарегистрированные браки (линейный график)"
      />

      <PieChart
      v-if="activeCharts.includes('Pie')"
      :labels="labels"
      :values="values"
      label="Распределение браков (круговая диаграмма)"
      />


      <BarChart
        v-if="activeCharts.includes('Bar')"
        :labels="labels"
        :values="values"
        label="Зарегистрированные браки (столбчатый график)"
      />
    </div>
    <p v-else>Загрузка данных...</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import LineChart from "@/components/charts/LineChart.vue";
import BarChart from "@/components/charts/BarChart.vue";
import PieChart from "@/components/charts/PieChart.vue";
import { fetchMarriageCountByYear } from "@/services/indicators/indicators.service";

const props = defineProps({
  activeCharts: {
    type: Array,
    default: () => ['Line', 'Bar', 'Pie']
  }
})

const labels = ref([]);
const values = ref([]);

onMounted(async () => {
  try {
    const data = await fetchMarriageCountByYear();
    labels.value = data.map(item => String(item.reportYear));
    values.value = data.map(item => item.marriageCount);
  } catch (error) {
    console.error("Ошибка загрузки данных для графика:", error);
  }
});
</script>
