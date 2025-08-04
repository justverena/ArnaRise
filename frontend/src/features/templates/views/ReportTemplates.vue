<template>
  <EditMarriageDivorceReport v-if="showMarriageEdit" @close="showMarriageEdit = false" />
  <EditGenderViolenceReport v-if="showViolenceEdit" @close="showViolenceEdit = false" />

  <button @click="showMarriageEdit = true">Редактировать брак/развод</button>
  <button @click="showViolenceEdit = true">Редактировать насилие</button>
</template>

<script setup>
import EditGenderViolenceReport from '@/features/templates/components/EditGenderViolence.vue'
import EditMarriageDivorceReport from '@/features/templates/components/EditMarriageDivorceReport.vue'
import { ref } from 'vue'

const showMarriageEdit = ref(false)
const showViolenceEdit = ref(false)
</script>


<style scoped>
.report-template {
  padding: 2rem;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 0 5px rgba(0,0,0,0.05);
  font-family: 'Inter', sans-serif;
  max-width: 900px;
  margin: 30px auto;
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
}

.actions {
  margin-bottom: 1rem;
  display: flex;
  justify-content: flex-end;
}

.add-btn {
  background-color: #1976d2;
  color: #fff;
  border: none;
  padding: 0.5rem 1rem;
  font-size: 0.9rem;
  border-radius: 8px;
  cursor: pointer;
}

.add-btn:hover {
  background-color: #1565c0;
}

table {
  width: 100%;
  border-collapse: collapse;
  font-size: 0.95rem;
}

th, td {
  padding: 0.75rem;
  text-align: left;
  border-bottom: 1px solid #e0e0e0;
}

th {
  background-color: #f3f4f6;
}

.edit-btn {
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 0.4rem 0.8rem;
  border-radius: 6px;
  font-size: 0.85rem;
  cursor: pointer;
}

.edit-btn:hover {
  background-color: #43a047;
}
</style>
