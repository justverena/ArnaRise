<template>
  <div class="lang-toggle" @click="toggleLang">
    <div class="lang-option" :class="{ active: lang === 'ru' }">RU</div>
    <div class="lang-option" :class="{ active: lang === 'kz' }">KZ</div>
    <div class="lang-slider" :class="{ right: lang === 'kz' }"></div>
  </div>
</template>


<script setup>
import { ref } from 'vue'
import { loadEnumsForLang } from '@/i18n'

const lang = ref(localStorage.getItem('lang') || 'ru')

const toggleLang = async () => {
  lang.value = lang.value === 'ru' ? 'kz' : 'ru'
  localStorage.setItem('lang', lang.value)
  await loadEnumsForLang(lang.value)
}
</script>

<style scoped>
.lang-toggle {
  position: relative;
  display: flex;
  align-items: center;
  background: #e0e0e0;
  border-radius: 20px;
  overflow: hidden;
  width: 80px;
  height: 32px;
  cursor: pointer;
}

.lang-option {
  flex: 1;
  text-align: center;
  z-index: 2;
  font-weight: bold;
  color: #555;
  transition: color 0.3s ease;
}

.lang-option.active {
  color: white;
}

.lang-slider {
  position: absolute;
  top: 2px;
  left: 2px;
  width: 36px;
  height: 28px;
  background: #009B67;
  border-radius: 18px;
  transition: transform 0.3s ease;
  z-index: 1;
}

.lang-slider.right {
  transform: translateX(40px);
}
</style>
