<template>
  <div class="lang-switch">
    <button 
      v-for="option in languages" 
      :key="option.value"
      :class="{ active: lang === option.value }"
      @click="changeLang(option.value)"
    >
      {{ option.label }}
    </button>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { loadEnumsForLang } from '@/i18n'

const lang = ref(localStorage.getItem('lang') || 'ru')

const changeLang = async (newLang) => {
  if (lang.value !== newLang) {
    lang.value = newLang
    await loadEnumsForLang(newLang) 
  }
}

const languages = [
  { value: 'ru', label: 'Русский' },
  { value: 'kz', label: 'Қазақ' }
]
</script>

<style scoped>
.lang-switch {
  display: flex;
  gap: 8px;
}

button {
  padding: 6px 12px;
  border: 1px solid #ccc;
  background: white;
  cursor: pointer;
}

button.active {
  background: #007bff;
  color: white;
  border-color: #007bff;
}
</style>
