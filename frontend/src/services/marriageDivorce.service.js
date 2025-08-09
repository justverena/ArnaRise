import api from '@/services/api'

// marriageDivorce.service.js (для аналитика)
export const getAnalystMarriageDivorceReports = () =>
  api.get("/analyst/reports/marriage-divorce");

export const approveMarriageDivorceReport = (id) =>
  api.post(`/analyst/reports/marriage-divorce/${id}/approve`);

export const rejectMarriageDivorceReport = (id, rejectionReason) =>
  api.post(`/analyst/reports/marriage-divorce/${id}/reject`, {
    rejectionReason,
  });
