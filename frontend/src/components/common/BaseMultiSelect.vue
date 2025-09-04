<template>
  <div class="form-group-wrapper">
    <label class="form-label">{{ label }}</label>
    <div
      class="form-group"
      :class="[sizeClass, borderSizeClass]"
    >
      <div
        v-for="option in options"
        :key="option.key"
        class="form-option"
      >
        <input
          type="checkbox"
          :id="`multi-${option.key}`"
          :value="option.key"
          v-model="modelValueProxy"
          :class="['checkbox', sizeClass]"
        />
        <label :for="`multi-${option.key}`" :class="['option-label', sizeClass]">
          {{ option.value }}
        </label>
      </div>
    </div>
  </div>
</template>

<script setup>
import { watch, computed } from 'vue'

const props = defineProps({
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
  },
  size: {
    type: String,
    default: 'md',
  },
  borderSize: {
    type: String,
    default: 'md',
  },
})

const emit = defineEmits(['update:modelValue'])
const modelValueProxy = defineModel({ type: Array, default: [] })

watch(modelValueProxy, (newValue) => {
  emit('update:modelValue', newValue)
})

const sizeMap = {
  sm: 'size-sm',
  md: 'size-md',
  lg: 'size-lg',
}

const borderSizeMap = {
  sm: 'border-sm',
  md: 'border-md',
  lg: 'border-lg',
}

const sizeClass = computed(() => sizeMap[props.size] || sizeMap.md)
const borderSizeClass = computed(() => borderSizeMap[props.borderSize] || borderSizeMap.md)
</script>

<style scoped>
.form-group-wrapper {
  display: flex;
  flex-direction: column;
  gap: 4px;
  align-self: stretch;
  width: 100%;
  min-width: 0;
  box-sizing: border-box;
}

.form-label {
  font-weight: 500;
  margin-bottom: 4px;
}

.form-group {
  border-radius: 4px;
  display: flex;
  flex-direction: column;
  flex: 1;
  gap: 6px;
  border: 1px solid #ccc;
  background: #fff;
  width: 100%;
}

.form-option {
  display: flex;
  align-items: center;
  gap: 6px;
}

.checkbox {
  cursor: pointer;
}
.option-label {
  cursor: pointer;
}

.size-sm.form-group {
  padding: 4px;
}
.size-sm .checkbox {
  width: 14px;
  height: 14px;
}
.size-sm .option-label {
  font-size: 12px;
}

.size-md.form-group {
  padding: 8px;
}
.size-md .checkbox {
  width: 18px;
  height: 18px;
}
.size-md .option-label {
  font-size: 14px;
}

.size-lg.form-group {
  padding: 12px;
}
.size-lg .checkbox {
  width: 22px;
  height: 22px;
}
.size-lg .option-label {
  font-size: 16px;
}

.border-sm.form-group { width: 50%; }
.border-md.form-group { width: 70%; }
.border-lg.form-group { width: 100%; }
</style>
