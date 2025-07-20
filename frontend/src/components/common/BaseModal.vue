<template>
  <div v-if="visible" class="overlay" @click.self="$emit('close')">
    <div class="modal">
      <button class="close-button" @click="$emit('close')">✕</button>
      <slot />
    </div>
  </div>
</template>

<script>
export default {
  name: 'BaseModal',
  props: {
    visible: {
      type: Boolean,
      default: false,
    },
  },
};
</script>

<style scoped>
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}
.modal {
  background: white;
  padding: 1.5rem;
  border-radius: 0.5rem;
  min-width: 300px;
  position: relative;
}
.close-button {
  position: absolute;
  top: 0.5rem;
  right: 0.5rem;
  border: none;
  background: none;
  font-size: 1.25rem;
  cursor: pointer;
}
</style>
