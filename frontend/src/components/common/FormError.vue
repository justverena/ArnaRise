<template>
  <p class="form-error" role="alert">{{ message }}</p>
</template>

<script setup>
defineProps({
  message: {
    type: String,
    required: true
  }
})
</script>

<style scoped>
.form-error {
  margin-top: 0.35rem;
  font-size: 0.85rem;
  color: #d32f2f; /* аккуратный красный */
  font-family: 'Inter', sans-serif;
}
</style>
