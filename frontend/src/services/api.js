import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  const lang = localStorage.getItem('lang') || 'ru'; 

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  config.headers['Accept-Language'] = lang;
  
  return config;
}, error => {
  return Promise.reject(error);
});

export const login = (data) => api.post("/auth/login", data);

export default api;
