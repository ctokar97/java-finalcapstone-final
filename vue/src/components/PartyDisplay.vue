<script>
export default {
  name: "PartyDisplay",
  props: ['party'],
}
</script>

<template>
  <div class="display-container">
    <div class="name-container">
      <h1>{{ party.party_name }}</h1>
    </div>
  </div>
</template>

<style scoped>
.display-container {
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid red;
  height: 100vh;
  width: 100vw;
}
</style>