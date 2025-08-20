<template>
  <button
    :class="[
      'base-button',
      type === 'submit' ? 'base-button-submit' : 'base-button-cancel'
    ]"
    v-bind="$attrs"
    @click="$emit('click')"
  >
    <slot />
  </button>
</template>

<script setup>
defineProps({
  type: {
    type: String,
    default: 'submit' // 'submit' | 'cancel'
  }
})
defineEmits(['click'])
</script>

<style scoped>
.base-button {
  flex: 1;
  padding: 0.8rem;
  border: none;
  border-radius: 2rem;
  font-size: 1rem;
  cursor: pointer;
  font-family: 'Inter', sans-serif;
  transition: background-color 0.3s ease;
}

.base-button-submit {
  background-color: #4caf50;
  color: white;
}

.base-button-submit:hover {
  background-color: #43a047;
}

.base-button-cancel {
  background-color: #ccc;
  color: #333;
}

.base-button-cancel:hover {
  background-color: #b0b0b0;
}
</style>
