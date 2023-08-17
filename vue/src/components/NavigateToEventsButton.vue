<script>

export default {
  name: "NavigateToEventsButton",
}
</script>

<template>
  <div>
    <router-link :to="{name: 'home'}" ><button>View Parties</button></router-link>
  </div>
</template>

<style scoped>
button {
  width: 10em;
  height: 2em;
  color: white;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);

  backdrop-filter: blur(10px);
  transition: transform 0.3s ease-in-out;
}

button:hover {
  background: rgba(255, 255, 255, 0.4);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.4);
  transform: scale(1.2);
}

</style>