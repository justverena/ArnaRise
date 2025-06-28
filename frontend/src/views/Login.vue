<template>
  <div class="login-page">
    <h2>Добро пожаловать</h2>
    <form @submit.prevent="handleLogin">
      <input v-model="email" placeholder="Email" type="email" required />
      <input v-model="password" placeholder="Password" type="password" required />

      <button type="submit">Войти</button>
    </form>
    <p v-if="error" style="color: red;">{{ error }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import api from '@/services/api.js'
import { useRouter } from 'vue-router'

const email = ref('')
const password = ref('')
const error = ref('')
const router = useRouter()

const handleLogin = async () => {
  error.value = ''
  try {
    const res = await api.post('/auth/login', {
      email: email.value,
      password: password.value
    })

    const token = res.data.token

    if (!token) {
      error.value = 'no token in server response'
      return
    }

    localStorage.setItem('token', token)
    router.push('/')

  }
  catch (err) {
    console.log('error while logging in:', err)
    error.value = 'wrong email or password'
  }
}

</script>

<style scoped>
.login-page {
  max-width: 400px;
  margin: 100px auto;
  padding: 2rem;
  border: 1px solid #ccc;
  border-radius: 8px;
  background: #f9f9f9;
  color: #333; 
}

h2 {
  text-align: center;
  margin-bottom: 1rem;
  color: #222; 
}

.login-form {
  display: flex;
  flex-direction: column;
}

.login-form label {
  margin-top: 1rem;
  font-weight: bold;
  color: #333;
}

.login-form input {
  padding: 0.5rem;
  margin-top: 0.3rem;
  border: 1px solid #ccc;
  border-radius: 4px;
  color: #222; 
  background-color: #fff;
}

.login-form button {
  margin-top: 1.5rem;
  padding: 0.7rem;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>
