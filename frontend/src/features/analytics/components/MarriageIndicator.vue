<template>
  <div>
    <LineChart
      v-if="labels.length && values.length"
      :labels="labels"
      :values="values"
      label="Зарегистрированные браки"
    />
    <p v-else>Загрузка данных...</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import LineChart from "@/components/charts/LineChart.vue";
import { fetchMarriageCountByYear } from "@/services/indicators/marriageIndicator.service";

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
