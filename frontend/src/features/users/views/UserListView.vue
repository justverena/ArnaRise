<template>
    <div class="users">
        <h1>Users List</h1>

        <table border="1" cellpadding="10">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="user in users" :key="user.id">
                    <td>{{ user.name }}</td>
                    <td>{{ user.email }}</td>
                    <td>{{ roleName(user.name) }}</td>
                    <td>
                        <button @click="deleteUser(user.id)">Удалить</button>
                    </td>

                </tr>
            </tbody>
        </table>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '@/services/api'

const users = ref([])

const fetchUsers = async () => {
    try {
        const response = await api.get('/admin/users')
        console.log('Ответ с сервера:', response.data)
        users.value = response.data
    }
    catch(error) {
        console.error('error getting users:', error)
    }

}

onMounted(fetchUsers)

const deleteUser = async (userId) => {
  const confirmed = confirm('Вы уверены, что хотите удалить этого пользователя?')
  if (!confirmed) return

  try {
    await api.delete(`/admin/users/delete/${userId}`)
    alert('Пользователь удалён')
    await fetchUsers() // Перезагружаем список
  } catch (error) {
    console.error('Ошибка при удалении пользователя:', error)
    alert('Не удалось удалить пользователя')
  }
}

function roleName(name) {
  switch (name.toLowerCase()) {
    case 'admin':
      return 'Админ'
    case 'analyst':
      return 'Аналитик'
    case 'partner':
      return 'Партнёр'
    default:
      return 'Админ'
  }
}


</script>

<style scoped>
.users {
  max-width: 800px;
  margin: 30px auto;
  font-family: Arial, sans-serif;
}

table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
}

th, td {
  padding: 8px;
  text-align: left;
  border: 1px solid #ccc;
}

button {
  background-color: #e53935;
  color: white;
  border: none;
  padding: 6px 10px;
  cursor: pointer;
  border-radius: 4px;
}

button:hover {
  background-color: #c62828;
}
</style>