<template>
  <component
    :is="componentType"
    :to="to"
    :href="href"
    :disabled="disabled"
    :class="[
      'base-button',
      variants[variant],
      sizes[size],
      shapes[shape],
      { 'base-button-disabled': disabled }
    ]"
    v-bind="$attrs"
    @click="$emit('click')"
  >
    <slot />
  </component>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  variant: { type: String, default: 'primary' },
  size: { type: String, default: 'md' },
  shape: {
    type: String,
    default: 'round',
    validator: (value) => ['round', 'semi', 'square'].includes(value)
  },
  disabled: { type: Boolean, default: false },
  to: { type: String, default: null },
  href: { type: String, default: null }
})

defineEmits(['click'])

const variants = {
  primary: 'base-button-primary',
  secondary: 'base-button-secondary',
  danger: 'base-button-danger',
  link: 'base-button-link'
}

const sizes = {
  sm: 'base-button-sm',
  md: 'base-button-md',
  lg: 'base-button-lg'
}

const shapes = {
  round: 'base-button-round',
  semi: 'base-button-semi',
  square: 'base-button-square'
}

const componentType = computed(() => {
  if (props.to) return 'router-link'
  if (props.href) return 'a'
  return 'button'
})
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap');

.base-button {
  font-family: 'Inter', sans-serif;
  border: none;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.25s ease, color 0.25s ease;
  display: inline-flex;
  align-items: center;
  justify-content: center;
}

.base-button-sm {
  padding: 0.4rem 0.8rem;
  font-size: 0.85rem;
}

.base-button-md {
  padding: 0.6rem 1.2rem;
  font-size: 0.95rem;
}

.base-button-lg {
  padding: 0.8rem 1.6rem;
  font-size: 1.1rem;
}

.base-button-round {
  border-radius: 2rem;
}

.base-button-semi {
  border-radius: 0.75rem;
}

.base-button-square {
  border-radius: 0.25rem;
}

.base-button-primary {
  background-color: #4caf50;
  color: white;
}
.base-button-primary:hover {
  background-color: #43a047;
}

.base-button-secondary {
  background-color: #e5e7eb;
  color: #374151;
}
.base-button-secondary:hover {
  background-color: #d1d5db;
}

.base-button-danger {
  background-color: #ef4444;
  color: white;
}
.base-button-danger:hover {
  background-color: #dc2626;
}

.base-button-link {
  background: transparent;
  color: #2563eb;
  text-decoration: underline;
}
.base-button-link:hover {
  color: #1d4ed8;
}

.base-button-disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
</style>
