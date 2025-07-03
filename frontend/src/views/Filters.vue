<template>
  <div class="filters-page">
    <h2>Фильтры происшествий</h2>

    <form @submit.prevent="applyFilters" class="filters-form">
      <select v-model="filters.city" required>
        <option disabled value="">Выберите город</option>
        <option value="Алматы">Алматы</option>
      </select>

      <select v-model="filters.district" required>
        <option disabled value="">Выберите район</option>
        <option v-for="district in districts" :key="district" :value="district">
          {{ district }}
        </option>
      </select>

      <select v-model="filters.gender">
        <option disabled value="">Пол</option>
        <option value="мужской">Мужской</option>
        <option value="женский">Женский</option>
      </select>

      <input v-model="filters.age" type="number" placeholder="Возраст" min="0" />

      <select v-model="filters.violenceType">
        <option disabled value="">Вид насилия</option>
        <option value="физическое">Физическое</option>
        <option value="психологическое">Психологическое</option>
        <option value="сексуальное">Сексуальное</option>
        <option value="экономическое">Экономическое</option>
      </select>

      <input v-model="filters.date" type="date" placeholder="Дата" />

      <button type="submit">Применить фильтры</button>
    </form>

    <div class="results" v-if="filteredCases.length">
      <h3>Результаты:</h3>
      <div v-for="(item, index) in filteredCases" :key="index" class="case-card">
        <p><strong>Дата:</strong> {{ item.date }}</p>
        <p><strong>Описание:</strong> {{ item.description }}</p>
        <p><strong>Пол:</strong> {{ item.gender }}</p>
        <p><strong>Возраст:</strong> {{ item.age }}</p>
        <p><strong>Город:</strong> {{ item.city }}</p>
        <p><strong>Район:</strong> {{ item.district }}</p>
        <p><strong>Вид насилия:</strong> {{ item.violenceType }}</p>
        <p><strong>Принятые меры:</strong> {{ item.measures }}</p>
        <p><strong>Орган:</strong> {{ item.organization }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import api from '@/services/api'

const filters = ref({
  city: '',
  district: '',
  gender: '',
  age: '',
  violenceType: '',
  date: ''
})

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

const filteredCases = ref([])

const applyFilters = async () => {
  try {
    const res = await api.get('/cases', { params: filters.value })
    filteredCases.value = res.data
  } catch (err) {
    console.error('Ошибка при получении данных:', err)
  }
}
</script>

<style scoped>
.filters-page {
  padding: 2rem;
}

.filters-form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  max-width: 400px;
  margin-bottom: 2rem;
}

.filters-form select,
.filters-form input {
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 6px;
}

button {
  padding: 0.75rem;
  background-color: #42b983;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-weight: bold;
}

button:hover {
  background-color: #369b6d;
}

.case-card {
  background: #f9f9f9;
  padding: 1rem;
  border-radius: 8px;
  margin-bottom: 1rem;
}
</style>
