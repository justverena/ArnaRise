<template>
  <label class="base-label">
    <span v-if="label" class="base-label-text">{{ label }}</span>
    <div class="wrapper">
      <input
        v-bind="$attrs"
        :type="type"
        :value="modelValue"
        @input="$emit('update:modelValue', $event.target.value)"
        class="base-input"
        :class="sizes[size]"
      />
    </div>
  </label>
</template>

<script setup>
const props = defineProps({
  modelValue: [String, Number],
  label: String,
  type: {
    type: String,
    default: 'text'
  },
  size: {
    type: String,
    default: 'medium',
    validator: val => ['small', 'medium', 'large'].includes(val)
  }
})

defineEmits(['update:modelValue'])

const sizes = {
  small: 'base-input-sm',
  medium: 'base-input-md',
  large: 'base-input-lg'
}
</script>

<style scoped>
.wrapper {
  flex: 1;
}

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

.base-input {
  border: 1px solid #d1d5db;
  border-radius: 0.75rem;
  background-color: #f9f9f9;
  outline: none;
  font-family: inherit;
  padding: 0.6rem 0.8rem;
  height: 40px;
  box-sizing: border-box;
}

.base-input:focus {
  border-color: #2563eb;
  box-shadow: 0 0 0 2px rgba(37, 99, 235, 0.2);
}

.base-input-sm {
  width: 20%;
}

.base-input-md {
  width: 60%;
}

.base-input-lg {
  width: 100%;
}
</style>
