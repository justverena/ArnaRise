import { defineStore } from 'pinia'
import { fetchEnum } from '@/services/enumService'

export const useEnumStore = defineStore('enumStore', {
  state: () => ({
    enums: {}
  }),
  actions: {
    async loadEnum(enumName) {
      if (!this.enums[enumName]) {
        const data = await fetchEnum(enumName)
        this.enums[enumName] = data
      }
    },
    getEnumOptions(enumName) {
      return this.enums[enumName] || []
    }
  }
})
