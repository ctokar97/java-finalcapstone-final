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
    <div class="user-container">
      <p>People playing:</p>
      <div class="scrolling-users">
        <p class ="username" v-for="user in party.users" :key="user.id">{{ user.username }}</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.display-container {
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid red;
  width: 100%;
}

.user-container {
  margin-top: 1em;
}

.scrolling-users {
  display: flex;
  flex-wrap: wrap;
}

.username {
  margin: 0.5em;
}
</style>