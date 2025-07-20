<template>
  <div class="base-textarea">
    <label v-if="label" :for="id">{{ label }}</label>
    <textarea
      :id="id"
      :placeholder="placeholder"
      v-model="innerValue"
      :rows="rows"
      :disabled="disabled"
      @input="$emit('update:modelValue', innerValue)"
    />
  </div>
</template>

<script>
export default {
  name: 'BaseTextarea',
  props: {
    modelValue: String,
    label: String,
    placeholder: String,
    id: String,
    rows: {
      type: Number,
      default: 4,
    },
    disabled: Boolean,
  },
  computed: {
    innerValue: {
      get() {
        return this.modelValue;
      },
      set(val) {
        this.$emit('update:modelValue', val);
      },
    },
  },
};
</script>

<style scoped>
.base-textarea {
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
}
textarea {
  padding: 0.5rem;
  border: 1px solid #cbd5e0;
  border-radius: 0.375rem;
  resize: vertical;
}
</style>
