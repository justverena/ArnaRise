<template>
  <div class="base-input">
    <label v-if="label" :for="id">{{ label }}</label>
    <input
      :id="id"
      :type="type"
      :placeholder="placeholder"
      v-model="innerValue"
      :disabled="disabled"
      @input="$emit('update:modelValue', innerValue)"
    />
  </div>
</template>

<script>
export default {
  name: 'BaseInput',
  props: {
    modelValue: [String, Number],
    label: String,
    placeholder: String,
    type: {
      type: String,
      default: 'text',
    },
    id: String,
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
.base-input {
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
}
input {
  padding: 0.5rem;
  border: 1px solid #cbd5e0;
  border-radius: 0.375rem;
}
</style>
