<template>
  <div class="home">
    <h1>Check out these parties!</h1>
    <PartyContainer/>
  </div>
</template>

<script>
import PartyContainer from "@/components/PartyContainer.vue";

export default {
  name: "home",
  components: {
    PartyContainer
  },

  created() {
    this.$store.dispatch('fetchParty');
  },
}
</script>

<style scoped>

.home {
  display: flex;
  flex-direction: column;
  width: 100vw;
}

h1 {
  display: flex;
  justify-content: center;
  align-items: center;
}

</style>
