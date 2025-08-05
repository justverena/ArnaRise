<!-- src/components/common/BaseMultiSelect.vue -->
<template>
  <div class="form-group">
    <label class="form-label">{{ label }}</label>
    <div class="space-y-2">
      <div
        v-for="option in options"
        :key="option.key"
        class="flex items-center gap-2"
      >
        <input
          type="checkbox"
          :id="`multi-${option.key}`"
          :value="option.key"
          v-model="modelValueProxy"
        />
        <label :for="`multi-${option.key}`">{{ option.value }}</label>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, watch, computed, onMounted } from 'vue'

defineProps({
  label: {
    type: String,
    required: true,
  },
  options: {
    type: Array,
    required: true,
  },
  modelValue: {
    type: Array,
    required: true,
  }
})

const emit = defineEmits(['update:modelValue'])

const modelValueProxy = defineModel({ type: Array, default: [] })

watch(modelValueProxy, (newValue) => {
  emit('update:modelValue', newValue)
})
</script>

<style scoped>
.form-group {
  border: 1px solid #ccc;
  border-radius: 4px;
  padding: 8px;
}
</style>
