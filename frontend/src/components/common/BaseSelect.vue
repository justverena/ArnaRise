<!-- src/components/common/BaseSelect.vue -->
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
        :key="opt[valueKey]"
        :value="opt[valueKey]"
      >
        {{ opt[labelKey] }}
      </option>
    </select>

    <FormError v-if="error" :message="error" />
  </label>
</template>

<script setup>
import { computed } from 'vue'
import FormError from './FormError.vue'

const props = defineProps({
  modelValue: [String, Number, Array],
  label: String,
  options: {
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
  error: String,
  valueKey: {
    type: String,
    default: 'value'
  },
  labelKey: {
    type: String,
    default: 'label'
  }
})

const emit = defineEmits(['update:modelValue'])

const normalizedOptions = computed(() =>
  props.options.map((o) =>
    typeof o === 'object'
      ? o
      : { [props.valueKey]: o, [props.labelKey]: String(o) }
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
.base-select {
  width: 100%;
  padding: 8px;
  border-radius: 8px;
  border: 1px solid #ccc;
  font-size: 16px;
}

.base-label {
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
}

.base-label-text {
  margin-bottom: 6px;
  font-weight: 500;
}
</style>
