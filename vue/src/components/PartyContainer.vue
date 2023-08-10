<script>
import PartyDisplay from "@/components/PartyDisplay.vue";
export default {
  name: "PartyContainer",
  components: {
    PartyDisplay
  },
  computed: {
    parties() {
      return this.$store.getters.getParties;
    }
  }
}
</script>

<template>
  <div class="party-wrapper">
    <PartyDisplay class="party" v-for="party in parties" :key="party.id" :party="party" />
  </div>
</template>

<style scoped>
.party-wrapper {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-around;
  width: 100%;
}

.party {
  height: 25em;
  width: 30%;
  margin: 1%;
  padding: 1%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);

  backdrop-filter: blur(10px);
}
</style>