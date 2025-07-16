import { createI18n } from 'vue-i18n'
import enumsRu from './i18n/enums/ru'

const messages = {
  ru: {
    enums: enumsRu,
    message: {
      hello: 'Привет мир'
    }
  },
  en: {
    enums: {}, // Можно добавить позже
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
