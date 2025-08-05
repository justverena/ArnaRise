import api from './api'

export const getEnum = (enumName, locale = 'ru') => {
    const token = localStorage.getItem('token')

    return api.get(`/enums/${enumName}`, {
        headers: {
        'Accept-Language': locale,
        }
    }).then(res => res.data)
}

