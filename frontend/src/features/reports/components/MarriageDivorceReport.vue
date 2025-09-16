<template>

    <BaseModal v-model="isOpen" @close="$emit('close')">
        <template #header>
            <span v-if="mode === 'create'">{{ $t('report.create', { type: $t('report.marriageDivorce') }) }}</span>
            <span v-else-if="mode === 'edit'">{{ $t('report.edit', { type: $t('report.marriageDivorce') }) }}</span>
            <span v-else>{{ $t('report.read', { type: $t('report.marriageDivorce') }) }}</span>
        </template>

        <div v-if="loading">{{ $t('report.loading') }}</div>

        <form v-else-if="mode!== 'show'" @submit.prevent="handleSubmit">
            <BaseSelect
                v-model="form.reportYear"
                :options="ReportYears"
                :label="$t('report.reportYear')"
                placeholder="Выберите год"
                value-key="key"
                label-key="value"
                />

            <BaseSelect
                v-model="form.district"
                :options="Districts"
                :label="$t('report.district')"
                placeholder="Выберите район"
                value-key="key"
                label-key="value"
                />

            <BaseInput
                v-model="form.marriageCount"
                :label="$t('report.marriageCount')"
                type="number"
                min="1"
                size="small"
                required
                @input="calculateRate"
                />

            <BaseInput
                v-model="form.divorceCount"
                :label="$t('report.divorceCount')"
                type="number"
                min="1"
                size="small"
                required
                @input="calculateRate"
                />

            <BaseInput
                :model-value="form.ratioDivorcesToMarriagePercent.toFixed(2)"
                :label="$t('report.ratio')"
                type="number"
                size="small"
                required
                disabled
                />

            <BaseInput
                v-model="form.averageAge"
                :label="$t('report.averageAge')"
                type="number"
                min="1"
                size="small"
                required
                @input="calculateRate"
                />

            <BaseSelect
                v-model="form.source"
                :options="Sources"
                :label="$t('report.source')"
                placeholder="Выберите источник"
                value-key="key"
                label-key="value"
                />

            <div class="form-actions">
                <BaseButton type="submit" >{{ $t('buttons.submit') }}</BaseButton>
                <BaseButton @click="$emit('close')" type="button" variant="secondary">{{ $t('buttons.close') }}</BaseButton>
            </div>  
        </form>

        <div v-else>

            <div v-if="!report">{{ $t('buttons.loading') }}</div>
            <div v-else>
                <BaseTable
                :columns="columns"
                :rows="rows"
                :id-key="'field'"
                />

            <div class="form-actions">
            <BaseButton v-if="showApproveReject" @click="$emit('approve')" size="sm">{{ $t('report.approve') }}</BaseButton>
            <BaseButton v-if="showApproveReject" @click="$emit('reject')" variant="danger" size="sm">{{ $t('report.reject') }}</BaseButton>
            <BaseButton type="button" @click="$emit('close')" variant="secondary">{{ $t('buttons.close') }}</BaseButton>
            </div>
            </div>
        </div>
    
    </BaseModal>
 
</template>

<script setup>
import { reactive, ref, onMounted, computed} from 'vue'
import { getEnum } from '@/services/enumService'
import { watch } from 'vue'
import { useI18n } from 'vue-i18n'
import BaseSelect from '@/components/common/BaseSelect.vue'
import BaseButton from '@/components/common/BaseButton.vue'
import BaseModal from '@/components/common/BaseModal.vue'
import BaseInput from '@/components/common/BaseInput.vue'
import BaseTable from '@/components/common/BaseTable.vue'

const props = defineProps({
    mode: {type: String, default: 'show'},
    reportId: {type: String, default:null},
    showApproveReject: {type: Boolean, default:false},
    initialData: {type:Object , default:null}
})

const emit = defineEmits(['submit', 'close', 'approve', 'reject'])

const {t} = useI18n()

const columns = [
    {label: t('report.field'), key:'field'},
    {label: t('report.value'), key:'value'}
]

const rows = computed(() => {
    if (!report.value) return []

    return [
        {field: 'ID', value:report.value.id},
        {field: t('report.reportYear'), value: getEnumValue(ReportYears.value, report.value.reportYear)},
        {field: t('report.district'), value: getEnumValue(Districts.value, report.value.district)},
        {field: t('report.marriageCount'), value:report.value.marriageCount},
        {field: t('report.divorceCount'), value:report.value.divorceCount},
        {field: t('report.ratio'), value:report.value.ratioDivorcesToMarriagePercent},
        {field: t('report.averageAge'), value:report.value.averageAge},
        {field: t('report.source'), value: getEnumValue(Sources.value, report.value.source)},
        {field: t('report.senderId'), value: report.value.submittedBy},
        {field: t('report.reportStatus'), value: getEnumValue(ReportStatuses.value, report.value.status)},
        report.value.rejectionReason
            ? { field: t('report.rejectionReason'), value: report.value.rejectionReason }
            : null
    ].filter(Boolean)
})

function handleSubmit() {
    emit('submit', { ...form })
}

const isOpen = ref(true)
const report = ref(null)
const loading = ref(false)

const form = reactive({
    reportYear: '',
    district: '',
    marriageCount: null,
    divorceCount: null,
    ratioDivorcesToMarriagePercent: 0,
    averageAge: null,
    source: '',
})

const ReportYears = ref([])
const Districts = ref([])
const Sources = ref([])
const ReportStatuses = ref([])

function getEnumValue(list, key) {
  if (!Array.isArray(list) || !key) return key
  return list.find(item => item.key === key)?.value || key
}

const calculateRate = () => {
  const m = form.marriageCount
  const d = form.divorceCount

  if (m && d && m > 0) {
    form.ratioDivorcesToMarriagePercent = (d / m) * 100
  } else {
    form.ratioDivorcesToMarriagePercent = 0
  }
}


onMounted(async () => {
    try {
        ReportYears.value = await getEnum('report-year')
        Districts.value = await getEnum('district')
        Sources.value = await getEnum('source')
        ReportStatuses.value = await getEnum('report-status')

        watch(
            () => props.initialData,
            (newVal) => {
                if (newVal) {
                    Object.assign(form, newVal)
                    report.value = newVal
                }
            },
            { immediate: true }
        )
    } catch (error) {
        console.error('Ошибка загрузки enum-ов:', error)
        emit('close')
    } finally {
        loading.value = false
    }
})


</script>

<style scoped>

form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}
label {
  display: flex;
  flex-direction: column;
  font-weight: 500;
  gap: 0.3rem;
}
input[type="date"] {
  padding: 0.6rem 0.8rem;
  border: 1px solid #ccc;
  border-radius: 8px;
}
.details-table {
  width: 100%;
  border-collapse: collapse;
}
.details-table td {
  border: 1px solid #ddd;
  padding: 6px;
}
.form-actions {
  display: flex;
  justify-content: flex-start;
  gap: 1rem;
  margin-top: 1.5rem;
}

</style>