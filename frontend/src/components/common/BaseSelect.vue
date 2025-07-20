<template>
  <div class="base-select">
    <label v-if="label" :for="id">{{ label }}</label>
    <select
      :id="id"
      v-model="innerValue"
      :disabled="disabled"
      @change="$emit('update:modelValue', innerValue)"
    >
      <option v-if="placeholder" disabled value="">{{ placeholder }}</option>
      <option
        v-for="option in options"
        :key="option.value"
        :value="option.value"
      >
        {{ option.label }}
      </option>
    </select>
  </div>
</template>

<script>
export default {
  name: 'BaseSelect',
  props: {
    modelValue: [String, Number],
    options: {
      type: Array,
      required: true,
      // формат: [{ label: 'Kazakhstan', value: 'kz' }]
    },
    label: String,
    placeholder: String,
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
.base-select {
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
}
select {
  padding: 0.5rem;
  border: 1px solid #cbd5e0;
  border-radius: 0.375rem;
}
</style>
