import { defineStore } from 'pinia'

export const useIndicatorStore = defineStore('global', {
  state: () => ({
    filters: {},
    currentIndicator: null
  }),
  actions: {
    setFilters(filters) {
      this.filters = filters
    },
    setIndicator(indicator) {
      this.currentIndicator = indicator
    }
  }
})
