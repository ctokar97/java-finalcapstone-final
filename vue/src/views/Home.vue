<template>
  <transition name="fade" v-if="show">
    <div class="home">
      <h1>Check out these parties!</h1>
      <button v-if="hasRoleDJ">create a party</button>
      <button @click="getSpotifyUserLogin">Log into Spotify!</button>
      <PartyContainer/>
    </div>
  </transition>
</template>


<script>
import PartyContainer from "@/components/PartyContainer.vue";
import {mapState} from "vuex";

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
    console.log(this.userAuthorities);
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
