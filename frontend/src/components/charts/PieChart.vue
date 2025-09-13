<template>
  <div class="chart-container">
    <h3 class="chart-title">{{ label }}</h3>
    <canvas ref="canvas"></canvas>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from "vue";
import { Chart, PieController, ArcElement, Tooltip, Legend } from "chart.js";

Chart.register(PieController, ArcElement, Tooltip, Legend);

const props = defineProps({
  labels: { type: Array, required: true },
  values: { type: Array, required: true },
  label: { type: String, default: "Диаграмма" },
  showPercent: { type:Boolean, default:false },
});

const canvas = ref(null);
let chartInstance = null;

onMounted(() => {
  if (canvas.value) {
    chartInstance = new Chart(canvas.value, {
      type: "pie",
      data: {
        labels: props.labels,
        datasets: [
          {
            label: props.label,
            data: props.values,
            backgroundColor: [
              "#FF6384",
              "#36A2EB",
              "#FFCE56",
              "#4BC0C0",
              "#9966FF"
            ]
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          legend: { position: "top" },
          tooltip: {
            callbacks: {
              label: function (context) {
                let label = context.label ? context.label + ": " : "";
                if (context.parsed !== null) {
                  label += context.parsed;
                  if (props.showPercent) {
                    label += "%";
                  }
                }
                return label;
              }
            }
          }
        }
      }
    });
  }
});

// перерисовка при изменении данных
watch([() => props.labels, () => props.values], () => {
  if (chartInstance) {
    chartInstance.data.labels = props.labels;
    chartInstance.data.datasets[0].data = props.values;
    chartInstance.update();
  }
});
</script>

<style scoped>
.chart-container {
  background: #fff;
  border-radius: 8px;
  padding: 1rem;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
}
.chart-title {
  font-weight: 600;
  margin-bottom: 0.5rem;
  text-align: center;
}
</style>
