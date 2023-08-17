<template>
  <transition name="fade" v-if="show">
    <div class="home">
      <h1>Check out these parties!</h1>
      <div class="button-wrapper">
        <button class="party-creator-button" v-if="hasRoleDJ" @click="showPartyCreator = !showPartyCreator">create a party</button>
        <button class="login-to-spotify" @click="getSpotifyUserLogin">Log into Spotify!</button>
      </div>
      <PartyCreator v-if="showPartyCreator" @close="showPartyCreator = false"></PartyCreator>
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
      if (!this.userAuthorities) {
        return false;
      }
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
@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,500;1,900&display=swap');

h1 {
  margin-top: 1em;
  font-family: 'Poppins', sans-serif;
  font-size: 5em;
  text-shadow: -7px 7px 5px rgba(0, 0, 0, 0.2);
}

.button-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 5em;
}

button {
  width: 10em;
  height: 2em;
  color: white;
  margin: 2em;
  font-family: 'Poppins', sans-serif;

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
  transform: scale(1.5);
}



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
