<template>
  <transition name="fade" v-if="show">
    <div id="login">
      <form class="login-form" @submit.prevent="login">
        <h1>Please Sign In</h1>
        <div role="alert" v-if="invalidCredentials">
          Invalid username and password!
        </div>
        <div role="alert" v-if="this.$route.query.registration">
          Thank you for registering, please sign in.
        </div>
        <div class="form-input-group">
          <label for="username">Username</label>
          <input type="text" id="username" v-model="user.username" required autofocus/>
        </div>
        <div class="form-input-group">
          <label for="password">Password</label>
          <input type="password" id="password" v-model="user.password" required/>
        </div>
        <button type="submit">Sign in</button>
        <p>
          <NavigateToEventsButton class="navigate-to-parties"/>
          <router-link :to="{ name: 'register' }">Need an account? Sign up.</router-link>
        </p>
      </form>
    </div>
  </transition>
</template>

<script>
import authService from "../services/AuthService";
import NavigateToEventsButton from "@/components/NavigateToEventsButton.vue";

export default {
  name: "login",
  components: {
    NavigateToEventsButton
  },
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false,
      show: false
    };
  },
  methods: {
    login() {
      authService
          .login(this.user)
          .then(response => {
            if (response.status == 200) {
              this.$store.commit("SET_AUTH_TOKEN", response.data.token);
              this.$store.commit("SET_USER", response.data.user);
              this.$router.push("/home");
            }
          })
          .catch(error => {
            const response = error.response;

            if (response.status === 401) {
              this.invalidCredentials = true;
            }
          });
    }
  },
  mounted() {
    this.show = true;
  }
};
</script>

<style scoped>
.form-input-group {
  margin-bottom: 1rem;
}

label {
  margin-right: 0.5rem;
}

#login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;


}

.login-form {
  width: 30%;
  height: 40%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);

  backdrop-filter: blur(10px);
}

h1 {
  justify-content: flex-start;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 1s;
}

.fade-enter, .fade-leave-to {
  opacity: 15%;
}

.navigate-to-parties {
  display: flex;
  justify-content: center;
}

</style>