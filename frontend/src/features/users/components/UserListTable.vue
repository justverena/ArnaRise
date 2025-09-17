<template>
  <h1>{{ $t('admin.usersList') }}</h1>
  <div class="users">
    <BaseTable
      :columns="columns"
      :rows="users"
      idKey="id"
    >
      <template #actions="{ row }">
        <BaseButton
          variant="danger"
          size="small"
          shape="square"
          @click="deleteUser(row.id)"
        >
          {{ $t('buttons.delete') }}
        </BaseButton>
      </template>
    </BaseTable>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getAdminUsers, deleteAdminUsers } from '@/services/adminService'
import BaseTable from '@/components/common/BaseTable.vue'
import BaseButton from '@/components/common/BaseButton.vue'
import { useI18n } from 'vue-i18n'

const { t } = useI18n()
const users = ref([])

const columns = [
  { label: t('admin.name'), key: 'name' },
  { label: t('admin.email'), key: 'email' }
]

const fetchUsers = async () => {
  try {
    const response = await getAdminUsers()
    users.value = response.data.map(user => ({
      ...user
    }))
  } catch {
  }
}

onMounted(fetchUsers)

const deleteUser = async (userId) => {
  const confirmed = confirm(t('admin.actions.confirmDelete'))
  if (!confirmed) return

  try {
    await deleteAdminUsers(userId)
    alert(t('admin.actions.deleteSuccess'))
    await fetchUsers()
  } catch {
    alert(t('admin.actions.deleteError'))
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