<template>
  <div class="filters">
    <h3>Фильтры</h3>

    <form @submit.prevent="applyFilters">
      <select v-model="localFilters.city">
        <option disabled value="">Выберите город</option>
        <option value="Алматы">Алматы</option>
      </select>

      <select v-model="localFilters.district">
        <option disabled value="">Выберите район</option>
        <option v-for="district in districts" :key="district" :value="district">
          {{ district }}
        </option>
      </select>

      <select v-model="localFilters.gender">
        <option disabled value="">Пол</option>
        <option value="мужской">Мужской</option>
        <option value="женский">Женский</option>
      </select>

      <input v-model="localFilters.age" type="number" min="0" placeholder="Возраст" />

      <select v-model="localFilters.violenceType">
        <option disabled value="">Вид насилия</option>
        <option value="физическое">Физическое</option>
        <option value="психологическое">Психологическое</option>
        <option value="сексуальное">Сексуальное</option>
        <option value="экономическое">Экономическое</option>
      </select>

      <input v-model="localFilters.date" type="date" placeholder="Дата" />

      <button type="submit">Применить</button>
    </form>
  </div>
</template>

<script setup>
import { reactive, toRefs, watch } from 'vue'
import { defineEmits } from 'vue'

const emit = defineEmits(['filter-updated'])

const districts = [
  'Алатауский',
  'Алмалинский',
  'Ауэзовский',
  'Бостандыкский',
  'Жетысуский',
  'Медеуский',
  'Наурызбайский',
  'Турксибский'
]

const localFilters = reactive({
  city: '',
  district: '',
  gender: '',
  age: '',
  violenceType: '',
  date: ''
})

function applyFilters() {
  emit('filter-updated', { ...localFilters })
}
</script>

<style scoped>
.filters {
  padding: 1rem;
  background: #f4f4f4;
  border-radius: 10px;
  margin-bottom: 1rem;
}
form {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}
form input, form select {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 6px;
}
button {
  background-color: #42b983;
  color: white;
  padding: 0.6rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
}
</style>
