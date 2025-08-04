<template>
  <label class="base-label">
    <span v-if="label" class="base-label-text">{{ label }}</span>

    <textarea
      v-bind="$attrs"
      class="base-textarea"
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
    />

    <div v-if="showCounter" class="counter">
      {{ (modelValue || '').length }}<span v-if="maxlength">/{{ maxlength }}</span>
    </div>

    <FormError v-if="error" :message="error" />
  </label>
</template>

<script setup>
import FormError from './FormError.vue'

defineProps({
  modelValue: {
    type: String,
    default: ''
  },
  label: String,
  maxlength: Number,
  showCounter: {
    type: Boolean,
    default: false
  },
  error: String
})
defineEmits(['update:modelValue'])
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

.base-textarea {
  width: 100%;
  min-height: 100px;
  padding: 0.6rem 0.8rem;
  margin-top: 0.3rem;
  border: 1px solid #d1d5db;
  border-radius: 0.75rem;
  font-size: 1rem;
  background-color: #f9f9f9;
  font-family: inherit;
  resize: vertical;
  outline: none;
}

.counter {
  margin-top: 0.3rem;
  font-size: 0.8rem;
  color: #6b7280;
}
</style>
