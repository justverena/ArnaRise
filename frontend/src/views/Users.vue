<template>
    <div class="users">
        <h1>Users List</h1>

        <pre>{{ users }}</pre>

        <table border="1" cellpadding="10">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="user in users" :key="user.id">
                    <td>{{ user.name }}</td>
                    <td>{{ user.email }}</td>
                    <td>{{ user.role?.name || '-' }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '@/services/api'

const users = ref([])

onMounted(async () => {
    try {
        const response = await api.get('/users')
        console.log('Ответ с сервера:', response.data)
        users.value = response.data
    }
    catch(error) {
        console.error('error getting users:', error)
    }
    
})

</script>

<style scoped>
.users {
    max-width: 800px;
    margin: 30px auto;
}

</style>