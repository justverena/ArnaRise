<template>
  <div class="login-wrapper">
    <div class="login-page">
      <h2>Выполните вход в систему</h2>
      <form @submit.prevent="handleLogin">
        <input v-model="email" placeholder="Email" type="email" required />
        <input v-model="password" placeholder="Password" type="password" required />
        <button type="submit">Войти</button>
      </form>
      <p v-if="error" style="color: red;">{{ error }}</p>
    </div>
  </div>
</template>


<script setup>
import { ref } from 'vue'
import api from '@/services/api.js'
import { useRouter, useRoute } from 'vue-router'
import { jwtDecode } from 'jwt-decode'

const email = ref('')
const password = ref('')
const error = ref('')
const router = useRouter()
const route = useRoute()

function decodeToken(token) {
  try {
    return jwtDecode(token)
  } catch {
    return null
  }
}

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

    const decoded = decodeToken(token)
    console.log(decoded)
    const role = decoded?.role || ''

    localStorage.setItem('role', role)

    router.push('/')

    const redirectPath = route.query.redirect || '/'
    router.push(redirectPath)

  } catch (err) {
    console.log('error while logging in:', err)
    error.value = 'wrong email or password'
  }
}
</script>


<style scoped>

.login-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 70vh;
  height: 100vh;
  width: 600px;
}



.login-page {
  width: 70%;
  height: 400px;
  max-width: 800px;
  padding: 3rem;
  border-radius: 12px;
  background: #fff;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  color: #333;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

h2 {
  text-align: center;
  margin-bottom: 1.5rem;
  color: #222;
}

form {
  display: flex;
  flex-direction: column;
  width: 100%;
  gap: 1rem;
}

form input {
  padding: 0.75rem;
  width: 100%;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 1rem;
}

form button {
  padding: 0.75rem;
  width: 100%;
  background-color: #42b983;
  color: white;
  font-weight: bold;
  font-size: 1rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.2s;
}

form button:hover {
  background-color: #369b6d;
}
</style>
