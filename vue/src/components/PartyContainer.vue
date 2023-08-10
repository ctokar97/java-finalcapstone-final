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
  display:flex;
  flex-direction: row;
  height: 50vh;
  width: 100vw
}

.party {
  height: 100%;
  width: 100%;
}

</style>