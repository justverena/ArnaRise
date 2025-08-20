<template>
  <table class="base-table">
    <thead>
      <tr>
        <th v-for="col in columns" :key="col.key">
          {{ col.label }}
        </th>
        <th v-if="$slots.actions">Действия</th>
      </tr>
    </thead>

    <tbody>
      <tr v-for="row in rows" :key="row[idKey]">
        <td v-for="col in columns" :key="col.key">
          {{ row[col.key] }}
        </td>

        <td v-if="$slots.actions">
          <slot name="actions" :row="row"></slot>
        </td>
      </tr>
    </tbody>
  </table>
</template>

<script setup>
defineProps({
  columns: {
    type: Array,
    required: true,
    // Пример: [{ label: 'ID', key: 'id' }, { label: 'Имя', key: 'name' }]
  },
  rows: {
    type: Array,
    required: true
  },
  idKey: {
    type: String,
    default: 'id'
  }
})
</script>

<style scoped>
.base-table {
  width: 100%;
  border-collapse: collapse;
}

.base-table th, .base-table td {
  border: 1px solid #ddd;
  padding: 8px;
}

.base-table th {
  background-color: #f4f4f4;
}
</style>
