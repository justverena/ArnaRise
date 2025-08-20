<template>
  <label class="base-label">
    <span v-if="label" class="base-label-text">{{ label }}</span>
    <input
      v-bind="$attrs"
      :type="type"
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
      class="base-input"
    />
  </label>
</template>

<script setup>
defineProps({
  modelValue: [String, Number],
  label: String,
  type: {
    type: String,
    default: 'text'
  }
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
}

.base-label-text {
  display: block;
  margin-bottom: 0.3rem;
}

.base-input {
  width: 100%;
  padding: 0.6rem;
  border: 1px solid #d1d5db;
  border-radius: 2rem;
  font-size: 0.95rem;
  background-color: #f9f9f9;
  outline: none;
}
</style>
