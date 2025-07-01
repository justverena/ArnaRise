<template>
  <div class="register-page">
    <h2>Регистрация нового пользователя</h2>
    <form @submit.prevent="registerUser">
      <input v-model="name" placeholder="Имя" required />
      <input v-model="email" placeholder="Email" required />
      <input v-model="password" placeholder="Пароль" required />
      <select v-model="role" required>
        <option value="admin">Admin</option>
        <option value="analyst">Analyst</option>
        <option value="partner">Partner</option>
      </select>
      <button type="submit">Зарегистрировать</button>
    </form>
    <p v-if="success" style="color: green;">{{ success }}</p>
    <p v-if="error" style="color: red;">{{ error }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import api from '@/services/api'

const name = ref('')
const email = ref('')
const password = ref('')
const role = ref('analyst')
const error = ref('')
const success = ref('')

const registerUser = async () => {
  error.value = ''
  success.value = ''
  try {
    const token = localStorage.getItem('token')
    const res = await api.post('/auth/admin/register',
      { name: name.value, email: email.value, password: password.value, role: role.value },
      { headers: { Authorization: `Bearer ${token}` } }
    )
    success.value = res.data.message
  } catch (err) {
    error.value = err.response?.data?.message || 'Ошибка регистрации'
  }
}
</script>

<style scoped>
.register-page {
  max-width: 400px;
  margin: auto;
  padding: 2rem;
  background: #fff;
  border-radius: 12px;
}
</style>
