<template>
  <section class="content">
    <h2>История отправленных отчётов</h2>
    <div class="report-grid">
      <button class="report-card" @click="viewReport('Quarterly Financial Report')">
        <h3>Quarterly Financial Report</h3>
        <p>Due: Dec 15, 2023</p>
      </button>
      <button class="report-card" @click="viewReport('Annual Performance Review')">
        <h3>Annual Performance Review</h3>
        <p>Due: Jan 20, 2024</p>
      </button>
      <button class="report-card" @click="viewReport('Project Milestone Report')">
        <h3>Project Milestone Report</h3>
        <p>Due: Feb 5, 2024</p>
      </button>
      <button class="report-card" @click="viewReport('Monthly Sales Analysis')">
        <h3>Monthly Sales Analysis</h3>
        <p>Due: Feb 28, 2024</p>
      </button>
    </div>
  </section>
</template>

<script>
export default {
  name: 'ReportHistory',
  methods: {
    viewReport(title) {
      alert(`Открытие отчёта: ${title}`)
      // здесь можно сделать emit, открыть модалку или перейти на страницу отчёта
    }
  }
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
