<template>
  <div class="container">
    <header class="header">
      <h1 class="logo">Админ Панель</h1>
      <button class="top-button" @click="goBack">Назад</button>
    </header>

    <main class="form-wrapper">
      <form class="form-box" @submit.prevent="registerUser">
        <h2>Регистрация нового пользователя</h2>

        <label>
          Имя
          <input type="text" v-model="name" placeholder="Введите имя" required />
        </label>

        <label>
          Email
          <input type="email" v-model="email" placeholder="Введите email" required />
        </label>

        <label>
          Пароль
          <input type="password" v-model="password" placeholder="Введите пароль" required />
        </label>

        <label>
          Роль
          <select v-model="role" required>
            <option value="admin">Админ</option>
            <option value="analyst">Аналитик</option>
            <option value="partner">Партнёр</option>
          </select>
        </label>

        <button class="submit-button" type="submit">Зарегистрировать</button>

        <p v-if="success" class="success">{{ success }}</p>
        <p v-if="error" class="error">{{ error }}</p>
      </form>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import api from '@/services/api'
import { postAdminUser } from '@/services/adminService'

const name = ref('')
const email = ref('')
const password = ref('')
const role = ref('analyst')
const error = ref('')
const success = ref('')
const router = useRouter()

const goBack = () => {
  router.back()
}

const registerUser = async () => {
  error.value = ''
  success.value = ''
  try {
    const token = localStorage.getItem('token')
    const res = await postAdminUser(
      {
        name: name.value,
        email: email.value,
        password: password.value,
        role: role.value
      },
      token
    )
    success.value = res.data.message || 'Пользователь успешно зарегистрирован!'
  } catch (err) {
    error.value = err.response?.data?.message || 'Ошибка регистрации'
  }
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap');

* {
  box-sizing: border-box;
}

body {
  font-family: 'Inter', sans-serif;
  margin: 0;
  background-color: #f9fafb;
}

.container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background: white;
  border-bottom: 1px solid #e5e7eb;
}

.logo {
  font-weight: 700;
  font-size: 1.2rem;
}

.top-button {
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 0.5rem;
  cursor: pointer;
  font-size: 0.9rem;
}

.form-wrapper {
  display: flex;
  justify-content: center;
  padding: 2rem;
}

.form-box {
  background: white;
  padding: 2rem;
  border-radius: 1rem;
  max-width: 500px;
  width: 100%;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
}

.form-box h2 {
  font-size: 1.5rem;
  margin-bottom: 1.5rem;
}

label {
  display: block;
  margin-bottom: 1rem;
  font-size: 0.95rem;
  font-weight: 500;
}

input[type="text"],
input[type="email"],
input[type="password"],
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

.submit-button {
  width: 100%;
  padding: 0.8rem;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 2rem;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 1rem;
}

.submit-button:hover {
  background-color: #43a047;
}

.success {
  margin-top: 1rem;
  color: green;
  font-weight: 500;
}

.error {
  margin-top: 1rem;
  color: red;
  font-weight: 500;
}
</style>
