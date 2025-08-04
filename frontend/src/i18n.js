import { createI18n } from 'vue-i18n'
import enumsRu from './i18n/enums/ru'

export default createI18n({
  legacy: false,
  locale: 'ru',
  fallbackLocale: 'en',
  messages: {
    ru: {
      enums: enumsRu
    }
  }
})
