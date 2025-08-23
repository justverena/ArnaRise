<template>
  <label class="base-label">
    <span v-if="label" class="base-label-text">{{ label }}</span>

    <textarea
      v-if="multiline"
      v-bind="$attrs"
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
      class="base-input base-textarea"
    ></textarea>

    <input
      v-else
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
  },
  multiline: {
    type: Boolean,
    default: false
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
  color: #333;
}

.base-label-text {
  display: block;
  margin-bottom: 0.3rem;
}

.base-input {
  width: 100%;
  padding: 0.6rem 0.8rem;
  border: 1px solid #d1d5db;
  border-radius: 0.75rem;
  font-size: 1rem;
  background-color: #f9f9f9;
  outline: none;
  font-family: inherit;
}

.base-input:focus {
  border-color: #2563eb;
  box-shadow: 0 0 0 2px rgba(37, 99, 235, 0.2);
}

.base-textarea {
  min-height: 80px;
  resize: vertical;
  line-height: 1.5;
}
</style>
