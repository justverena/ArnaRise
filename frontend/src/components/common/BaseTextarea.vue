<template>
  <label class="base-label">
    <span v-if="label" class="base-label-text">{{ label }}</span>

    <textarea
      v-bind="$attrs"
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
      class="base-textarea"
      :class="sizes[size]"
    />

    <div v-if="showCounter" class="counter">
      {{ (modelValue || '').length }}<span v-if="maxlength">/{{ maxlength }}</span>
    </div>

    <FormError v-if="error" :message="error" />
  </label>
</template>

<script setup>
import FormError from './FormError.vue'

const props = defineProps({
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
  error: String,
  size: {
    type: String,
    default: 'medium',
    validator: val => ['small', 'medium', 'large'].includes(val)
  }
})

defineEmits(['update:modelValue'])

const sizes = {
  small: 'textarea-sm',
  medium: 'textarea-md',
  large: 'textarea-lg'
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

.base-textarea {
  width: 100%;
  min-height: 100px;
  max-height: 250px;
  border: 1px solid #d1d5db;
  border-radius: 0.75rem;
  font-family: inherit;
  background-color: #f9f9f9;
  outline: none;
  resize: vertical;
  margin-top: 0.3rem;
  box-sizing: border-box;
}

.textarea-sm {
  padding: 0.9rem 1rem;
  font-size: 1rem;
  width: 30%;
}

.textarea-md {
  padding: 0.9rem 1rem;
  font-size: 1rem;
  width: 50%;
}

.textarea-lg {
  padding: 0.9rem 1rem;
  font-size: 1rem;
  width: 100%;
}

.base-textarea:focus {
  border-color: #2563eb;
  box-shadow: 0 0 0 2px rgba(37, 99, 235, 0.2);
}

.counter {
  margin-top: 0.3rem;
  font-size: 0.8rem;
  color: #6b7280;
}
</style>
