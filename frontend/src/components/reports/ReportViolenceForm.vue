<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <h2>Отчет: Гендерное насилие</h2>
      <form @submit.prevent="submit">
        <!-- Район -->
        <label>Район:
          <select v-model="form.district" required>
            <option disabled value="">Выберите</option>
            <option value="ALATAU">Алатау</option>
            <option value="ALMALY">Алмалы</option>
            <option value="AUEZOV">Ауэзов</option>
            <option value="BOSTANDYK">Бостандык</option>
            <option value="ZHETISU">Жетысу</option>
            <option value="MEDEU">Медеу</option>
            <option value="NAURYZBAY">Наурызбай</option>
            <option value="TURKSIB">Турксиб</option>
          </select>
        </label>

        <!-- Дата -->
        <label>Дата инцидента:
          <input v-model="form.date" type="date" required />
        </label>

        <!-- Пол -->
        <label>Пол:
          <select v-model="form.gender" required>
            <option disabled value="">Выберите</option>
            <option value="MALE">Мужской</option>
            <option value="FEMALE">Женский</option>
          </select>
        </label>

        <!-- Возраст -->
        <label>Возраст:
          <input v-model="form.age" type="number" min="0" required />
        </label>

        <!-- Тип насилия -->
        <label>Тип насилия:
          <select v-model="form.violenceType" required>
            <option disabled value="">Выберите</option>
            <option value="PHYSICAL">Физическое</option>
            <option value="PSYCHOLOGICAL">Психологическое</option>
            <option value="SEXUAL">Сексуальное</option>
            <option value="ECONOMIC">Экономическое</option>
            <option value="STALKING">Преследование</option>
            <option value="OTHER">Другое</option>
          </select>
        </label>

        <!-- Место -->
        <label>Место происшествия:
          <select v-model="form.location" required>
            <option disabled value="">Выберите</option>
            <option value="HOME">Дом</option>
            <option value="APARTMENT">Квартира</option>
            <option value="WORKPLACE">Работа</option>
            <option value="PUBLIC_PLACE">Публичное место</option>
            <option value="UNKNOWN">Неизвестно</option>
            <option value="OTHER">Другое</option>
          </select>
        </label>

        <!-- Время суток -->
        <label>Время суток:
          <select v-model="form.timeOfDay" required>
            <option disabled value="">Выберите</option>
            <option value="MORNING">Утро</option>
            <option value="AFTERNOON">День</option>
            <option value="EVENING">Вечер</option>
            <option value="NIGHT">Ночь</option>
          </select>
        </label>

        <!-- Социальный статус -->
        <label>Социальный статус:
          <select v-model="form.socialStatus" required>
            <option disabled value="">Выберите</option>
            <option value="EMPLOYED">Трудоустроен</option>
            <option value="UNEMPLOYED">Безработный</option>
            <option value="STUDENT">Студент</option>
            <option value="RETIRED">Пенсионер</option>
            <option value="HOUSEWIFE">Домохозяйка</option>
            <option value="MINOR">Несовершеннолетний</option>
            <option value="DISABLED">Инвалид</option>
            <option value="IMMIGRANT">Иммигрант</option>
            <option value="HOMELESS">Бездомный</option>
            <option value="UNKNOWN">Неизвестно</option>
          </select>
        </label>

        <!-- Отношения -->
        <label>Отношения с агрессором:
          <select v-model="form.aggressorRelation" required>
            <option disabled value="">Выберите</option>
            <option value="SPOUSE">Супруг/а</option>
            <option value="EX_SPOUSE">Бывший супруг/а</option>
            <option value="PARTNER">Партнер</option>
            <option value="EX_PARTNER">Бывший партнер</option>
            <option value="PARENT">Родитель</option>
            <option value="SIBLING">Брат/Сестра</option>
            <option value="CHILD">Ребенок</option>
            <option value="FRIEND">Друг</option>
            <option value="STRANGER">Незнакомец</option>
            <option value="NEIGHBOR">Сосед</option>
            <option value="EMPLOYER">Работодатель</option>
            <option value="TEACHER">Учитель</option>
            <option value="LAW_ENFORCEMENT">Правоохранитель</option>
            <option value="OTHER">Другое</option>
            <option value="UNKNOWN">Неизвестно</option>
          </select>
        </label>

        <!-- Описание -->
        <label>Описание случая:
          <textarea v-model="form.caseDescription" rows="3" />
        </label>

        <!-- Орган -->
        <label>Орган, принявший меры:
          <select v-model="form.authority">
            <option disabled value="">Выберите</option>
            <option value="POLICE">Полиция</option>
            <option value="MEDICAL">Медицина</option>
            <option value="SOCIAL_SERVICES">Соц. службы</option>
            <option value="NGO">НПО</option>
            <option value="COURT">Суд</option>
            <option value="HOTLINE">Горячая линия</option>
            <option value="SCHOOL_ADMIN">Школьная администрация</option>
            <option value="LOCAL_GOVERNMENT">Акимат</option>
            <option value="HUMAN_RIGHTS_ORG">Правозащитники</option>
            <option value="NONE">Нет</option>
            <option value="OTHER">Другое</option>
          </select>
        </label>

        <!-- Меры -->
        <label>Принятые меры:
          <select v-model="form.action" required>
            <option disabled value="">Выберите</option>
            <option value="POLICE">Полиция</option>
            <option value="SHELTER">Укрытие</option>
            <option value="MEDICAL_ASSISTANCE">Медицинская помощь</option>
            <option value="FOLLOW_UP_SCHEDULED">Назначен контрольный визит</option>
            <option value="CASE_OPENED">Открыто дело</option>
            <option value="LEGAL_SUPPORT">Юридическая помощь</option>
            <option value="PSYCHOLOGIST">Психолог</option>
            <option value="SHELTER_PROVIDED">Предоставлено укрытие</option>
            <option value="PSYCHOLOGICAL_SUPPORT">Психологическая поддержка</option>
            <option value="RELOCATION">Перемещение</option>
            <option value="RESTRAINING_ORDER">Запретительный ордер</option>
            <option value="OTHER">Другое</option>
          </select>
        </label>

        <div class="form-actions">
          <button class="submit-btn" type="submit">Отправить</button>
          <button class="cancel-btn" type="button" @click="$emit('close')">Закрыть</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { submitPartnerGenderViolenceReport } from '@/services/api.js'

const emit = defineEmits(['reportSubmitted', 'close'])

const form = reactive({
  gender: '',
  date: '',
  district: '',
  age: null,
  violenceType: '',
  location: '',
  timeOfDay: '',
  socialStatus: '',
  aggressorRelation: '',
  caseDescription: '',
  authority: '',
  action: ''
})

const submit = async () => {
  try {
    await submitPartnerGenderViolenceReport({
      ...form,
      rejectionReason: '',
      status: 'PENDING'
    })
    alert('Отчет успешно отправлен!')
    emit('reportSubmitted')
    emit('close')
  } catch (error) {
    console.error('Ошибка отправки:', error)
    alert('Не удалось отправить отчет. Проверьте консоль.')
  }
}
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999;
}

.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 1rem;
  max-width: 1000px;
  width: 800px;
  height: 800px;
  overflow-y: auto;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  font-family: 'Inter', sans-serif;
}

h2 {
  margin-bottom: 1.5rem;
  font-size: 1.6rem;
  font-weight: 600;
}

form {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

label {
  display: flex;
  flex-direction: column;
  font-size: 0.95rem;
  font-weight: 500;
  color: #333;
}

input[type="text"],
input[type="number"],
input[type="date"],
select,
textarea {
  width: 100%;
  padding: 0.6rem 0.8rem;
  margin-top: 0.4rem;
  border: 1px solid #d1d5db;
  border-radius: 0.75rem;
  font-size: 1rem;
  background-color: #f9f9f9;
  font-family: inherit;
  resize: vertical;
}

select {
  appearance: none;
}

textarea {
  min-height: 80px;
  line-height: 1.5;
}

.form-action {
  display: flex;
  justify-content: space-between;
  gap: 1rem;
  margin-top: 1.5rem;
}

.submit-btn {
  flex: 1;
  background-color: #4caf50;
  color: white;
  padding: 0.8rem;
  border: none;
  border-radius: 2rem;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-btn:hover {
  background-color: #43a047;
}

.cancel-btn {
  flex: 1;
  background-color: #ccc;
  color: #333;
  padding: 0.8rem;
  border: none;
  border-radius: 2rem;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.cancel-btn:hover {
  background-color: #b0b0b0;
}
</style>

