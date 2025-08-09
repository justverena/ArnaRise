import api from '@/services/api'

export const getAnalystGenderViolenceReports = () =>
  api.get('/analyst/reports/gender-violence')

export const approveAnalystGenderViolenceReport = (id) =>
  api.post(`/analyst/reports/gender-violence/${id}/approve`)

export const rejectAnalystGenderViolenceReport = (id, data) =>
  api.post(`/analyst/reports/gender-violence/${id}/reject`, data)

export const submitPartnerGenderViolenceReport = (data) =>
  api.post('/partner/reports/gender-violence', data)
