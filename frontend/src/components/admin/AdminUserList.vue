<template>
  <section class="user-list">
    <h2>Список пользователей</h2>
    <table>
      <thead>
        <tr>
          <th>Имя</th>
          <th>Email</th>
          <th>Роль</th>
          <th>Дата создания</th>
          <th>Дата обновления</th>
          <th>Действия</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id">
          <td>{{ user.username }}</td>
          <td>{{ user.email }}</td>
          <td>{{ roleName(user.role_id) }}</td>
          <td>{{ formatDate(user.created_at) }}</td>
          <td>{{ formatDate(user.updated_at) }}</td>
          <td>
            <button class="edit-btn">Изменить</button>
            <button class="delete-btn">Удалить</button>
          </td>
        </tr>
      </tbody>
    </table>
  </section>
</template>

<script setup>
const users = [
  {
    id: '5ed6ac02-e44b-4e53-bb6d-f6cb0170e915',
    username: 'Admin',
    email: 'admin@example.com',
    role_id: 1,
    created_at: '2025-07-01 05:47:11.811130',
    updated_at: '2025-07-01 05:47:11.811130'
  },
  {
    id: '0544e6a0-de6a-4823-8dc3-472af34d05e8',
    username: 'analyst',
    email: 'analyst@example.com',
    role_id: 2,
    created_at: '2025-07-01 13:09:13.645450',
    updated_at: '2025-07-01 13:09:13.645450'
  },
  {
    id: '417b9c14-6db5-4777-86da-0bf4dbf4c966',
    username: 'partner',
    email: 'partner@example.com',
    role_id: 3,
    created_at: '2025-07-03 13:28:54.358097',
    updated_at: '2025-07-03 13:28:54.358097'
  },
  {
    id: 'af7619d0-6897-41b6-ac1f-985afdc6f0e5',
    username: 'alan',
    email: 'alan@example.com',
    role_id: 2,
    created_at: '2025-07-05 13:34:25.350629',
    updated_at: '2025-07-05 13:34:25.350629'
  },
  {
    id: 'ed4e2c77-4199-445b-aacd-7dcda8fa882c',
    username: 'aaa',
    email: 'aaaa@gmail.com',
    role_id: 2,
    created_at: '2025-07-08 15:04:11.367662',
    updated_at: '2025-07-08 15:04:11.367662'
  },
  {
    id: '29f5a9f2-bfad-4c25-9ca3-f0bbda1a4f9d',
    username: 'analitik',
    email: 'analitix@example.com',
    role_id: 2,
    created_at: '2025-07-08 15:27:48.199974',
    updated_at: '2025-07-08 15:27:48.199974'
  },
  {
    id: '8c188bfc-846e-418d-9cbe-30a89e340ab8',
    username: 'analyst',
    email: 'analystt@example.com',
    role_id: 2,
    created_at: '2025-07-08 15:29:27.632557',
    updated_at: '2025-07-08 15:29:27.632557'
  },
  {
    id: '482885dc-5f43-4e64-8c5a-41b2d29595c4',
    username: 'partner',
    email: 'partnerr@example.com',
    role_id: 3,
    created_at: '2025-07-08 15:30:49.800266',
    updated_at: '2025-07-08 15:30:49.800266'
  }
]

function roleName(roleId) {
  switch (roleId) {
    case 1: return 'Админ'
    case 2: return 'Аналитик'
    case 3: return 'Партнёр'
    default: return 'Неизвестно'
  }
}

function formatDate(dateStr) {
  return new Date(dateStr).toLocaleString('ru-RU')
}
</script>

<style scoped>
.user-list {
  padding: 2rem;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 0 5px rgba(0,0,0,0.05);
  font-family: 'Inter', sans-serif;
}

h2 {
  font-size: 1.5rem;
  margin-bottom: 1rem;
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

.edit-btn,
.delete-btn {
  padding: 0.3rem 0.7rem;
  margin-right: 0.3rem;
  font-size: 0.85rem;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  color: white;
}

.edit-btn {
  background-color: #4caf50;
}

.delete-btn {
  background-color: #e53935;
}
</style>
