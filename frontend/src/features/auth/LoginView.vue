<template>
  <div class="login-wrapper">
    <div class="login-page">
      <h2>{{ $t('login.title') }}</h2>
      <form @submit.prevent="handleLogin">
        <input
          v-model="email"
          :placeholder="$t('login.enterEmail')"
          type="email"
          required
        />
        <input
          v-model="password"
          :placeholder="$t('login.enterPassword')"
          type="password"
          required
        />
        <BaseButton type="submit" size="lg" shape="square">
          {{ $t('buttons.login') }}
        </BaseButton>
      </form>
      <p v-if="error" style="color: red;">{{ error }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { login } from '@/services/api.js'
import { useRouter, useRoute } from 'vue-router'
import { jwtDecode } from 'jwt-decode'
import BaseButton from '@/components/common/BaseButton.vue'
import { useI18n } from 'vue-i18n'

const email = ref('')
const password = ref('')
const error = ref('')
const router = useRouter()
const route = useRoute()
const { t } = useI18n()

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
    const res = await login({
      email: email.value,
      password: password.value
    })

    const token = res.data.token

    if (!token) {
      error.value = t('login.errors.noToken')
      return
    }

    localStorage.setItem('token', token)

    const decoded = decodeToken(token)
    const role = decoded?.role || ''
    localStorage.setItem('role', role)

    const redirectPath = route.query.redirect || '/'
    router.push(redirectPath)
  } catch {
    error.value = t('login.errors.wrongCredentials')
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
  background-color: #4caf50;
  color: white;
  font-weight: bold;
  font-size: 1rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.2s;
}

form button:hover {
  background-color: #009B67;
}
</style>
