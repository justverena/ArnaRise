import api from '@/services/api'

export const getAdminUsers = (data) =>
    api.get("/admin/users", data);

export const deleteAdminUsers = (id, data) =>
    api.delete(`/admin/users/delete/${id}`, data);

export const postAdminUser = (data) =>
    api.post("/admin/register", data);
