import api from '@/services/api'

export const submitPartnerGenderViolenceReport = (data) =>
  api.post("/partner/reports/gender-violence", data);

export const getPartnerGenderViolenceReports = () =>
  api.get("/partner/reports/gender-violence");

export const getRejectedGenderViolenceReports = () =>
  api.get("/partner/reports/gender-violence/rejected");

export const editRejectedGenderViolenceReport = (id, data) => 
  api.patch(`/partner/reports/gender-violence/${id}`, data)

export const getAnalystGenderViolenceReports = () =>
  api.get('/analyst/reports/gender-violence')

export const approveAnalystGenderViolenceReport = (id) =>
  api.post(`/analyst/reports/gender-violence/${id}/approve`)

export const rejectAnalystGenderViolenceReport = (id, data) =>
  api.post(`/analyst/reports/gender-violence/${id}/reject`, data)


