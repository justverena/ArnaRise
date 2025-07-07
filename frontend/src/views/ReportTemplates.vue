<!-- views/ReportTemplate.vue -->
<template>
  <div class="report-template">
    <h1>Шаблоны отчётов</h1>

    <button @click="showAddModal = true">Добавить шаблон</button>

    <ul class="template-list">
      <li v-for="(template, index) in templates" :key="index">
        {{ template.name }}
        <button @click="editTemplate(template)">Редактировать</button>
      </li>
    </ul>

    <!-- Модальные окна -->
    <AddTemplate v-if="showAddModal" @close="showAddModal = false" />
    <EditTemplate
      v-if="showEditModal"
      :template="templateToEdit"
      @close="showEditModal = false"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import AddTemplate from '@/components/templates/AddTemplate.vue'
import EditTemplate from '@/components/templates/EditTemplate.vue'

const showAddModal = ref(false)
const showEditModal = ref(false)
const templateToEdit = ref(null)

const templates = ref([
  { name: 'Отчёт по бракам' },
  { name: 'Разводы и возраст' }
])

function editTemplate(template) {
  templateToEdit.value = template
  showEditModal.value = true
}
</script>

<style scoped>
.report-template {
  padding: 2rem;
}

.template-list {
  margin-top: 1rem;
  list-style: none;
  padding: 0;
}

.template-list li {
  margin-bottom: 0.5rem;
  background: #f3f3f3;
  padding: 1rem;
  border-radius: 8px;
}
</style>
