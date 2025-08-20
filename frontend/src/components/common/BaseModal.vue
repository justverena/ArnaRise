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
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
  backdrop-filter: blur(2px); /* лёгкое размытие заднего фона */
}

.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 0; /* более мягкие закругления */
  max-width: 900px; /* увеличена ширина */
  width: 90%; /* адаптивная ширина на экранах меньше 900px */
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.2); /* мягкая тень */
  font-family: 'Inter', sans-serif;
  max-height: 85vh;
  overflow-y: auto;
  transition: all 0.3s ease; /* плавное появление/изменение */
}

.modal-header {
  margin-bottom: 1rem;
  font-size: 1.6rem; /* чуть больше заголовок */
  font-weight: 600;
  border-bottom: 1px solid #eee; /* разделитель */
  padding-bottom: 0.5rem;
}

.modal-body {
  padding-top: 0.5rem;
}

.modal-footer {
  margin-top: 1.5rem;
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
}
</style>

