<template>
  <div class="indicators">
    <h3>{{ $t('indicators.chooseIndicator') }}</h3>

    <BaseSelect
      v-model="selectedIndicator"
      :options="indicators"
      :label="$t('indicators.chooseIndicator')"
      placeholder="Выберите индикатор"
      value-key="value"
      label-key="label"
      @update:modelValue="emitIndicator"
      size="sm"
    />
  </div>
</template>

<script setup>
import { ref, defineEmits, computed} from 'vue'
import { useI18n } from 'vue-i18n'
import BaseSelect from '@/components/common/BaseSelect.vue'

const emit = defineEmits(['indicator-selected'])

const { t } = useI18n()

const selectedIndicator = ref('')

const indicators = computed(() => [
  { label: t('indicators.marriages'), value: 'marriage' },
  { label: t('indicators.divorces'), value: 'divorce' },
  { label: t('indicators.ratio'), value: 'divorceRatio' },
  { label: t('indicators.averageMarriageAge'), value: 'avgAge' }
]) 

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
</style>
