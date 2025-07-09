// src/i18n.js
import { createI18n } from 'vue-i18n'

const messages = {
  ru: {
    message: {
      hello: 'Привет мир'
    }
  },
  en: {
    message: {
      hello: 'Hello world'
    }
  }
}

const i18n = createI18n({
  legacy: false,
  locale: 'ru',
  fallbackLocale: 'en',
  messages
})

export default i18n
