// src/services/api.js
import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

// автоматически вставлять токен во все запросы
api.interceptors.request.use(config => {
  const token = localStorage.getItem('token'); // убедись, что access_token сохраняется именно под ключом 'token'
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, error => {
  return Promise.reject(error);
});

export const login = (data) => api.post("/auth/login", data);

export const submitPartnerGenderViolenceReport = (data) =>
  api.post("/partner/reports/gender-violence", data);

export const submitMarriageDivorceReport = (data) =>
  api.post("/partner/reports/marriage-divorce", data);

export const getPartnerGenderViolenceReports = () =>
  api.get("/partner/reports/gender-violence");

export const getRejectedGenderViolenceReports = () =>
  api.get("/partner/reports/gender-violence/rejected");

export const getRejectedMarriageDivorceReports = () =>
  api.get("/partner/reports/marriage-divorce/rejected")

export const editRejectedMarriageDivorceReport = (id, data) =>
  api.patch(`/partner/reports/marriage-divorce/${id}`, data)

export const editRejectedViolenceReport = (id, data) => 
  api.patch(`/partner/reports/gender-violence/${id}`, data)

// export const getUsers = () => api.get("/users");
// export const getAuditLogs = () => api.get("/audit");
// export const getIndicators = () => api.get("/indicator");
// export const getFilters = () => api.get("/filter");

export default api;
