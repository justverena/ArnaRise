<template>
  <label class="base-label">
    <span v-if="label" class="base-label-text">{{ label }}</span>
    <select
      v-bind="$attrs"
      class="base-select"
      :class="[{ 'is-multiple': multiple, 'has-placeholder': !modelValue }, sizes[size]]"
      :multiple="multiple"
      :value="modelValue"
      @change="onChange"
    >
      <option
        v-if="!multiple && !modelValue && placeholder"
        value=""
        disabled
        hidden
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
  },
  size: {
    type: String,
    default: 'md',
    validator: (value) => ['sm', 'md', 'lg'].includes(value)
  }
})

const emit = defineEmits(['update:modelValue'])

const sizes = {
  sm: 'base-select-sm',
  md: 'base-select-md',
  lg: 'base-select-lg'
}

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
  padding: 8px;
  border-radius: 8px;
  border: 1px solid #ccc;
  font-size: 16px;
}

.base-select-sm {
  max-width: 150px;
  min-width: 110px;
  width: 110px;
}

.base-select-md {
  min-width: 220px;
  width: auto;
}

.base-select option[disabled] {
  color: #999;
}

.base-select option:not([disabled]) {
  color: #000;
}

.base-label {
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
  width: 100%;
}

.base-select-lg {
  flex: 1;
  width: 100%;
}

.base-label-text {
  margin-bottom: 6px;
  font-weight: 500;
}

.base-select.has-placeholder {
  color: #999;
}
</style>
