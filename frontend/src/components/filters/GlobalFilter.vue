<script setup>
import { useGlobalStore } from '@/store'
const store = useGlobalStore()

const applyFilters = () => {
  store.setFilters({ gender: 'женский', age: 25 }) // пример
}
</script>

<template>
  <div>
    <select @change="applyFilters">
      <option value="женский">Женский</option>
      <option value="мужской">Мужской</option>
    </select>
  </div>
</template>
