<template>
  <div class="modal-overlay">
    <div class="modal-content">
      <h2>Список организаций</h2>
      <div class="org-list">
        <div class="org-card">
          <h3>ТОО «Астана Инвест»</h3>
          <p>Сфера: Инвестиции и развитие</p>
          <p>Город: Астана</p>
        </div>

        <div class="org-card">
          <h3>АО «КазТехПром»</h3>
          <p>Сфера: Производство техники</p>
          <p>Город: Алматы</p>
        </div>

        <div class="org-card">
          <h3>ТОО «ЗерноЭкспорт»</h3>
          <p>Сфера: Агроэкспорт</p>
          <p>Город: Костанай</p>
        </div>
      </div>

      <div class="modal-actions">
        <button @click="$emit('close')">Закрыть</button>
      </div>
    </div>
  </div>
</template>

<script setup>
// без логики, только отображение
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
  border-radius: 12px;
  max-width: 700px;
  width: 100%;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
  font-family: 'Inter', sans-serif;
}

h2 {
  margin-bottom: 1.5rem;
  font-size: 1.5rem;
  color: #333;
}

.org-list {
  display: grid;
  grid-template-columns: 1fr;
  gap: 1rem;
}

.org-card {
  background-color: #f3f4f6;
  padding: 1rem 1.25rem;
  border-radius: 0.5rem;
  border: 1px solid #ccc;
}

.org-card h3 {
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: #222;
}

.org-card p {
  font-size: 0.9rem;
  color: #444;
  margin: 0.25rem 0;
}

.modal-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 1.5rem;
}

.modal-actions button {
  padding: 0.5rem 1rem;
  background-color: #6EC36B;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 0.9rem;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.modal-actions button:hover {
  background-color: #6EC36B;
}
</style>
