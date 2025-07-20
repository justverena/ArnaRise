<template>
  <p v-if="error" class="form-error">{{ error }}</p>
</template>

<script>
export default {
  name: 'FormError',
  props: {
    error: {
      type: String,
      default: '',
    },
  },
};
</script>

<style scoped>
.form-error {
  color: #e53e3e;
  font-size: 0.875rem;
  margin-top: -0.5rem;
  margin-bottom: 0.75rem;
}
</style>
