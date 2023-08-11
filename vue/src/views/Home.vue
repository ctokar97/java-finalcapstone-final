<template>
  <transition name="fade" v-if="show">
    <div class="home">
      <h1>Check out these parties!</h1>
      <PartyContainer/>
    </div>
  </transition>
</template>


<script>
import PartyContainer from "@/components/PartyContainer.vue";

export default {
  name: "home",
  components: {
    PartyContainer
  },
  data() {
    return {
      show: false
    };
  },
  mounted() {
    this.show = true;
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

.fade-enter-active, .fade-leave-active {
  transition: opacity 1s;
}
.fade-enter, .fade-leave-to {
  opacity: 15%;
}

</style>
