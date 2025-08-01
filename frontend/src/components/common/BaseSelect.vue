<template>
  <label class="base-label">
    <span v-if="label" class="base-label-text">{{ label }}</span>

    <select
      v-bind="$attrs"
      class="base-select"
      :class="{ 'is-multiple': multiple }"
      :multiple="multiple"
      :value="modelValue"
      @change="onChange"
    >
      <option
        v-if="!multiple && placeholder"
        disabled
        :value="''"
      >
        {{ placeholder }}
      </option>

      <option
        v-for="opt in normalizedOptions"
        :key="opt.value"
        :value="opt.value"
      >
        {{ opt.label }}
      </option>
    </select>

    <FormError v-if="error" :message="error" />
  </label>
</template>

<script setup>
import { computed } from 'vue'
import FormError from './FormError.vue'

const props = defineProps({
  modelValue: {
    type: [String, Number, Array],
    default: ''
  },
  label: String,
  options: {
    /** Можно передать:
     *  - ['A', 'B']  -> value=label='A'
     *  - [{ value:'A', label:'Алмалы' }, ...]
     */
    type: Array,
    default: () => []
  },
  placeholder: {
    type: String,
    default: 'Выберите'
  },
  multiple: {
    type: Boolean,
    default: false
  },
  error: String
})

const emit = defineEmits(['update:modelValue'])

const normalizedOptions = computed(() =>
  props.options.map((o) =>
    typeof o === 'object' ? o : { value: o, label: String(o) }
  )
)

function onChange(e) {
  if (props.multiple) {
    const values = Array.from(e.target.selectedOptions).map((o) => o.value)
    emit('update:modelValue', values)
  } else {
    emit('update:modelValue', e.target.value)
  }
}
</script>

<style scoped>
.base-label {
  display: block;
  margin-bottom: 1rem;
  font-size: 0.95rem;
  font-weight: 500;
  font-family: 'Inter', sans-serif;
  color: #333;
}

.base-label-text {
  display: block;
  margin-bottom: 0.3rem;
}

.base-select {
  width: 100%;
  padding: 0.6rem;
  border: 1px solid #d1d5db;
  border-radius: 2rem;
  font-size: 0.95rem;
  background-color: #f9f9f9;
  outline: none;
  appearance: none;
}

.base-select.is-multiple {
  border-radius: 0.75rem; /* в multiple выглядит аккуратнее */
  padding: 0.4rem;
}
</style>
