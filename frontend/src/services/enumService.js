import api from './api'

export const getEnum = (enumName, lang) => {
  return api.get(`/enums/${enumName}`, {
    headers: { 'Accept-Language': lang }
  }).then(res => res.data);
}
