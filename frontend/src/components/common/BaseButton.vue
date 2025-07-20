<template>
  <button
    :class="[
      'base-button',
      variant,
      { 'is-loading': loading },
    ]"
    :disabled="disabled || loading"
    @click="$emit('click', $event)"
  >
    <slot />
  </button>
</template>

<script>
export default {
  name: 'BaseButton',
  props: {
    variant: {
      type: String,
      default: 'primary', // 'primary', 'secondary', 'danger'
    },
    loading: Boolean,
    disabled: Boolean,
  },
};
</script>

<style scoped>
.base-button {
  padding: 0.5rem 1rem;
  border-radius: 0.375rem;
  font-weight: 600;
  cursor: pointer;
  transition: 0.2s;
}
.primary {
  background-color: #2d6cdf;
  color: white;
}
.secondary {
  background-color: #e2e8f0;
  color: #1a202c;
}
.danger {
  background-color: #e53e3e;
  color: white;
}
.is-loading {
  opacity: 0.6;
  pointer-events: none;
}
</style>
