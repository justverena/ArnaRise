import api from '@/services/api'

export const submitMarriageDivorceReport = (data) =>
  api.post("/partner/reports/marriage-divorce", data);

export const getRejectedMarriageDivorceReports = () =>
  api.get("/partner/reports/marriage-divorce/rejected")

export const editRejectedMarriageDivorceReport = (id, data) =>
  api.patch(`/partner/reports/marriage-divorce/${id}`, data)

export const getAnalystMarriageDivorceReports = () =>
  api.get("/analyst/reports/marriage-divorce");

export const getAnalystMarriageDivorceReportById = (id) =>
  api.get(`/analyst/reports/marriage-divorce/${id}`);

export const approveMarriageDivorceReport = (id) =>
  api.post(`/analyst/reports/marriage-divorce/${id}/approve`);

export const rejectMarriageDivorceReport = (id, rejectionReason) =>
  api.post(`/analyst/reports/marriage-divorce/${id}/reject`, {
    rejectionReason,
  });
