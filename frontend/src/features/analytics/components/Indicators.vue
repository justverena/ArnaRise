<template>
  <div class="indicators">
    <h3>{{ $t('indicators.chooseIndicator') }}</h3>

    <select v-model="selectedIndicator" @change="emitIndicator">
      <option disabled value="" v-if="!selectedIndicator">{{ $t('indicators.chooseIndicator') }}</option>
      <option
        v-for="indicator in indicators"
        :key="indicator.value"
        :value="indicator.value"
      >
        {{ indicator.label }}
      </option>
    </select>
  </div>
</template>


<script setup>
import { ref, defineEmits } from 'vue'
import { useI18n } from 'vue-i18n'

const emit = defineEmits(['indicator-selected'])

const {t} = useI18n()

const indicators = [
  { label: t('indicators.marriages'), value: 'marriage' },
  { label: t('indicators.divorces'), value: 'divorce' },
  { label: t('indicators.ratio'), value: 'divorceRatio' },
  { label: t('indicators.averageMarriageAge'), value: 'avgAge' }
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
