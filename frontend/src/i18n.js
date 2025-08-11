import { createI18n } from 'vue-i18n'
import { getEnum } from '@/services/enumService'

const i18n = createI18n({
  legacy: false,
  locale: localStorage.getItem('lang') || 'ru',
  fallbackLocale: 'ru',
  messages: {}
})

async function loadEnumsForLang(lang) {
  localStorage.setItem('lang', lang)

  const enumsList = [
    'action-taken',
    'aggressor-relation',
    'authority',
    'district',
    'gender',
    'location-type',
    'report-status',
    'report-year',
    'social-status',
    'source',
    'time-of-day',
    'violence-type',
  ]

  const enums = {}
  for (const enumName of enumsList) {
    enums[enumName] = await getEnum(enumName, lang) 
  }

  i18n.global.setLocaleMessage(lang, { enums })
  i18n.global.locale.value = lang
}

await loadEnumsForLang(i18n.global.locale.value)

export { i18n, loadEnumsForLang }
export default i18n
