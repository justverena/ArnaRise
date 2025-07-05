<template>
  <div class="indicators">
    <h3>Выбор индикатора</h3>

    <select v-model="selectedIndicator" @change="emitIndicator">
      <option disabled value="">Выберите индикатор</option>
      <option v-for="indicator in indicators" :key="indicator.value" :value="indicator.value">
        {{ indicator.label }}
      </option>
    </select>
  </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue'

const emit = defineEmits(['indicator-selected'])

const indicators = [
  { label: 'Количество зарегистрированных браков', value: 'marriage' },
  { label: 'Количество разводов', value: 'divorce' },
  { label: 'Отношение разводов к бракам (%)', value: 'divorceRatio' },
  { label: 'Средний возраст вступающих в брак', value: 'avgAge' }
]

const selectedIndicator = ref('')

const emitIndicator = () => {
  emit('indicator-selected', selectedIndicator.value)
}
</script>

<style scoped>
.indicators {
  padding: 1rem;
  background: #f4f4f4;
  border-radius: 10px;
  margin-bottom: 1rem;
}
select {
  width: 100%;
  padding: 0.6rem;
  border: 1px solid #ccc;
  border-radius: 6px;
}
</style>
