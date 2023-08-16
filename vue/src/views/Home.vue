<template>
  <transition name="fade" v-if="show">
    <div class="home">
      <h1>Check out these parties!</h1>
      <button v-if="hasRoleDJ" @click="showPartyCreator = !showPartyCreator">create a party</button>
      <button @click="getSpotifyUserLogin">Log into Spotify!</button>
      <PartyCreator v-if = "showPartyCreator" @close = "showPartyCreator = false"></PartyCreator>
      <PartyContainer/>
    </div>
  </transition>
</template>


<script>
import PartyContainer from "@/components/PartyContainer.vue";
import {mapState} from "vuex";
import PartyCreator from "@/components/PartyCreator.vue";

export default {
  name: "home",
  components: {
    PartyCreator,
    PartyContainer
  },
  data() {
    return {
      show: false,
      showPartyCreator: false
    };
  },
  mounted() {
    this.show = true;
  },

  created() {
    this.$store.dispatch('fetchParty');
  },
  computed: {
    ...mapState({
      userAuthorities: state => state.user.authorities
    }),
    hasRoleDJ() {
      return this.userAuthorities.some(auth => auth.name === 'ROLE_DJ');
    }
  },
  methods: {
    getSpotifyUserLogin() {
      const getSpotifyUserLogin = () => {
        fetch("http://localhost:9000/api/login")
            .then((response) => response.text())
            .then(response => {
              window.location.replace(response);
            })
      }
      return getSpotifyUserLogin();
    },
    showCurrentUser() {
      console.log(this.user);
    }
  }
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
