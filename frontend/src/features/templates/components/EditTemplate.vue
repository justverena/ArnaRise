<!-- components/templates/EditTemplate.vue -->
<template>
  <div class="modal">
    <div class="modal-content">
      <h2>Редактировать шаблон</h2>

      <input v-model="name" placeholder="Название шаблона" />

      <button @click="close">Закрыть</button>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, defineProps, defineEmits } from 'vue'

const props = defineProps({
  template: Object
})

const emit = defineEmits(['close'])
const name = ref('')

watch(
  () => props.template,
  (newVal) => {
    if (newVal) name.value = newVal.name
  },
  { immediate: true }
)

function close() {
  emit('close')
}
</script>

<style scoped>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  width: 400px;
  box-shadow: 0 6px 18px rgba(0, 0, 0, 0.2);
}
</style>
