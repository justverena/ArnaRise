<template>
  <div class="container">
    <header class="header">
      <h1 class="logo">{{ $t('report.createReport') }}</h1>
      <BaseButton variant="primary" size="lg" shape="square" @click="goBack">{{ $t('buttons.back') }}</BaseButton>
    </header>

    <main class="template-wrapper">
      <div class="template-box">

        <div class="template-grid">
          <div class="template-card">
            <h3>{{ $t('report.marriageDivorce') }}</h3>
            <p>{{ $t('report.marriageDivorceDescription') }}</p>
            <BaseButton @click="selected = 'marriage'">{{ $t('buttons.create') }}</BaseButton>
          </div>

          <div class="template-card">
            <h3>{{ $t('report.genderViolence') }}</h3>
            <p>{{ $t('report.genderViolenceDescription') }}</p>
            <BaseButton @click="selected = 'violence'">{{ $t('buttons.create') }}</BaseButton>
          </div>
        </div>
      </div>
    </main>

    <ReportMarriageForm v-if="selected === 'marriage'" @close="selected = ''" />
    <ReportViolenceForm v-if="selected === 'violence'" @close="selected = ''" />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import ReportMarriageForm from '@/features/reports/components/FormMarriageDivorceReport.vue'
import ReportViolenceForm from '@/features/reports/components/FormGenderViolenceReport.vue'
import BaseButton from '@/components/common/BaseButton.vue'

const router = useRouter()
const goBack = () => router.back()
const selected = ref('')
</script>



<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap');

* {
  box-sizing: border-box;
}

body {
  font-family: 'Inter', sans-serif;
  margin: 0;
  background-color: #f9fafb;
}

.container {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background: white;
  border-bottom: 1px solid #e5e7eb;
}

.logo {
  font-weight: 700;
  font-size: 1.2rem;
}

.top-button {
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 0.5rem;
  cursor: pointer;
  font-size: 0.9rem;
}

.template-wrapper {
  display: flex;
  justify-content: center;
  padding: 2rem;
}

.template-box {
  background: white;
  padding: 2rem;
  border-radius: 1rem;
  max-width: 800px;
  width: 100%;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.template-box h2 {
  font-size: 1.5rem;
  margin-bottom: 2rem;
}

.template-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
}

.template-card {
  align-items: left;
  background-color: #f3f4f6;
  padding: 1.5rem;
  border-radius: 0.75rem;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.template-card h3 {
  font-size: 1.2rem;
  margin-bottom: 0.5rem;
  color: #333;
}

.template-card p {
  font-size: 0.95rem;
  color: #555;
  margin-bottom: 1rem;
}

</style>