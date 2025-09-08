<template>
  <section class="content">
    <h2>Отклоненные отчеты</h2>
    <div class="report-grid">
      <button
        v-for="report in reports"
        :key="report.id"
        class="report-card"
        @click="viewReport(report)">
      <h3>{{ getReportTypeTitle(report) }}</h3>
      <p>{{ formatDate(report.date) }}</p>
      <p v-if="report.rejectionReason" class="text-red-600 text-sm">
        Отклонен: {{ report.rejectionReason }}
      </p>
      </button>
    </div>
  </section>

<EditMarriageDivorceReport
  v-if="showEditModal && selectedReport?.type === 'MARRIAGE'"
  :report-id="selectedReport.id"
  @close="closeModal"
  
/>

<EditViolenceReport
  v-if="showEditModal && selectedReport?.type === 'GENDER'"
  :report-id="selectedReport.id"
  mode="edit"
  @close="closeModal"
  
  />
</template>

<script>
import { getRejectedGenderViolenceReports } from '@/services/reports/genderViolence.service'
import { getRejectedMarriageDivorceReports } from '@/services/reports/marriageDivorce.service'
import { getAnalystMarriageDivorceReportById } from '@/services/reports/marriageDivorce.service'
import EditMarriageDivorceReport from '../components/EditMarriageDivorceReport.vue'
import EditViolenceReport from '../components/EditGenderViolenceReport.vue'

export default {
  name: 'ReportHistory',
  data() {
    return {
      reports: [],
      selectedReport: null,
      showEditModal: false
    }
  },
  methods: {
    async fetchReports() {
      try {
        const [violenceRes, marriageRes] = await Promise.all([
          getRejectedGenderViolenceReports(),
          getRejectedMarriageDivorceReports()
        ])
        this.reports = [
          ...violenceRes.data.map(r => ({ ...r, type: 'GENDER'})),
          ...marriageRes.data.map(r => ({ ...r, type: 'MARRIAGE'}))
        ]
      } catch (error) {
        console.error('ошибка при загрузке отчетов', error)
      }
      
    },
    viewReport(report) {
      if (report.type === 'GENDER') {
    console.log('Просмотр отчета о гендерном насилии:', report)

    const preparedForSend = {
      ...report,
      rejectionReason: '',  
      status: 'PENDING'     
    }
    console.log('Готовый к отправке объект:', preparedForSend)
  }
  
      this.selectedReport = report
      this.showEditModal = true
    },
    getReportTypeTitle(report) {
      return report.type === 'GENDER'
      ? 'Гендерное насилие'
      : 'Браки и разводы'
    },
    formatDate(date) {
      const d = new Date(date)
      return d.toLocaleDateString('ru-RU')
    },
    closeModal() {
      this.showEditModal = false
      this.selectedReport = null
    },
    handleUpdateSuccess() {
      this.fetchReports()
    }
  },
  mounted() {
    this.fetchReports()
  },
  components: {
    EditMarriageDivorceReport,
    EditViolenceReport
  },
  emits: ['close'],
}
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap');

* {
  box-sizing: border-box;
}

body {
  margin: 0;
  font-family: 'Inter', sans-serif;
}

.content {
  padding: 2rem;
  background: white;
  border-radius: 0.5rem;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.05);
}

.content h2 {
  font-size: 1.5rem;
  margin-bottom: 1.5rem;
  color: #333;
}

.report-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
  gap: 1rem;
}

.report-card {
  background-color: #e5e7eb;
  padding: 1rem;
  border-radius: 0.5rem;
  text-align: left;
  cursor: pointer;
  border: none;
  transition: background-color 0.2s ease-in-out;
}

.report-card:hover {
  background-color: #4caf50;
}

.report-card h3 {
  font-size: 1rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: #111827;
}

.report-card p {
  font-size: 0.875rem;
  color: #374151;
}
</style>
