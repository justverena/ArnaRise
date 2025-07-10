<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <h2>Отчет: Гендерное насилие</h2>
      <form @submit.prevent="submit">
        <label>Регион <input v-model="form.region" type="text" /></label>
        <label>Количество обращений <input v-model="form.reports" type="number" /></label>
        <label>Количество пострадавших женщин <input v-model="form.femaleVictims" type="number" /></label>
        <label>Количество пострадавших мужчин <input v-model="form.maleVictims" type="number" /></label>
        <label>Дата отчета <input v-model="form.date" type="date" /></label>

        <div class="form-actions">
          <button class="submit-btn" type="submit">Отправить</button>
          <button class="cancel-btn" type="button" @click="$emit('close')">Закрыть</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'

const form = reactive({
  region: '',
  reports: 0,
  femaleVictims: 0,
  maleVictims: 0,
  date: ''
})

const submit = () => {
  console.log('Violence Report:', form)
  alert('Отчет по гендерному насилию отправлен!')
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
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  font-family: 'Inter', sans-serif;
}

h2 {
  margin-bottom: 1.5rem;
  font-size: 1.4rem;
}

.form-box label {
  display: block;
  margin-bottom: 1rem;
  font-size: 0.95rem;
  font-weight: 500;
}

input[type="text"],
input[type="number"],
input[type="date"],
input[type="file"],
select {
  width: 100%;
  padding: 0.6rem;
  margin-top: 0.3rem;
  border: 1px solid #d1d5db;
  border-radius: 2rem;
  font-size: 0.95rem;
  background-color: #f9f9f9;
}

select {
  appearance: none;
}

.form-actions {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  margin-top: 1.5rem;
}

.submit-btn {
  flex: 1;
  background-color: #4caf50;
  color: white;
  padding: 0.8rem;
  border: none;
  border-radius: 2rem;
  font-size: 1rem;
  cursor: pointer;
}

.submit-btn:hover {
  background-color: #43a047;
}

.cancel-btn {
  flex: 1;
  background-color: #ccc;
  color: #333;
  padding: 0.8rem;
  border: none;
  border-radius: 2rem;
  font-size: 1rem;
  cursor: pointer;
}

.cancel-btn:hover {
  background-color: #b0b0b0;
}
</style>
