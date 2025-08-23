<template>
  <h1>Список пользователей</h1>
    <div class="users">
        

        <BaseTable
          :columns="columns"
          :rows="users"
          idKey="id"
        >
          <template #actions="{ row }">
            <BaseButton variant="danger" size="small" shape="square" @click="deleteUser(row.id)">Удалить</BaseButton>
          </template>
        </BaseTable>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAdminUsers, deleteAdminUsers } from '@/services/adminService'
import BaseTable from '@/components/common/BaseTable.vue'
import BaseButton from '@/components/common/BaseButton.vue'

const users = ref([])

const columns = [
  { label: 'Имя', key: 'name' },
  { label: 'Электронная почта', key: 'email' }
]

const fetchUsers = async () => {
    try {
        const response = await getAdminUsers()
        console.log('Ответ с сервера:', response.data)
        users.value = response.data.map(user => ({
          ...user
        }))
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
    await deleteAdminUsers(userId)
    alert('Пользователь удалён')
    await fetchUsers() 
  } catch (error) {
    console.error('Ошибка при удалении пользователя:', error)
    alert('Не удалось удалить пользователя')
  }
}



</script>

<style scoped>

.users {
  background-color: #fff;
  width: 800px;
  margin: 0px auto;
  font-family: Arial, sans-serif;
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