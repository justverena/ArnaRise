<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <h2>Редактирование отчёта: Браки и разводы</h2>

      <p>Здесь появится форма редактирования шаблона.</p>

      <div class="form-actions">
        <button class="submit-btn" @click="submitEdit">Сохранить</button>
        <button class="cancel-btn" @click="$emit('close')">Отмена</button>
      </div>
    </div>
  </div>
</template>

<script setup>
const emit = defineEmits(['close'])

function submitEdit() {
  alert('Редактирование отчета отправлено!')
  emit('close')
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.4);
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
  font-family: 'Inter', sans-serif;
}
.form-actions {
  margin-top: 2rem;
  display: flex;
  justify-content: space-between;
}
.submit-btn {
  background-color: #4caf50;
  color: white;
  padding: 0.7rem 1.2rem;
  border: none;
  border-radius: 2rem;
  cursor: pointer;
}
.cancel-btn {
  background-color: #ccc;
  color: #333;
  padding: 0.7rem 1.2rem;
  border: none;
  border-radius: 2rem;
  cursor: pointer;
}
</style>
