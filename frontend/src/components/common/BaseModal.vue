<template>
  <teleport to="body">
    <div v-if="modelValue" class="modal-overlay" @click="onBackdrop">
      <div class="modal-content" @click.stop>
        <header v-if="$slots.header" class="modal-header">
          <slot name="header" />
        </header>

        <main class="modal-body">
          <slot />
        </main>

        <footer v-if="$slots.footer" class="modal-footer">
          <slot name="footer" />
        </footer>
      </div>
    </div>
  </teleport>
</template>

<script setup>
const props = defineProps({
  modelValue: { type: Boolean, default: false },
  closeOnBackdrop: { type: Boolean, default: true }
})
const emit = defineEmits(['update:modelValue', 'close'])

function onBackdrop() {
  if (!props.closeOnBackdrop) return
  emit('update:modelValue', false)
  emit('close')
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  inset: 0;
  background-color: rgba(0,0,0,.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 1rem;
  max-width: 600px;
  width: 100%;
  box-shadow: 0 2px 10px rgba(0,0,0,0.2);
  font-family: 'Inter', sans-serif;
  max-height: 85vh;
  overflow: auto;
}

.modal-header {
  margin-bottom: 1rem;
  font-size: 1.4rem;
  font-weight: 600;
}

.modal-body {
  /* содержимое формы/текста */
}

.modal-footer {
  margin-top: 1.5rem;
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
}
</style>
