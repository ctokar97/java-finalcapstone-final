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
        <button class="sign-in" type="submit">Sign in</button>
        <p>
          <router-link class="need-an-account" :to="{ name: 'register' }">Need an account? Sign up.</router-link>
        </p>
      </form>
    </div>
  </transition>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {

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

@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,400;0,500;1,900&display=swap');

.form-input-group {
  display: flex;
  flex-direction: column;
}

.sign-in {
  margin-top: 1em;
}

label {
  margin-right: 0.5rem;
  font-family: 'Poppins', sans-serif;
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
  justify-content: space-around;
  align-items: center;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);

  backdrop-filter: blur(10px);
}

h1 {
  justify-content: flex-start;
  font-family: 'Poppins', sans-serif;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 1s;
}

.fade-enter, .fade-leave-to {
  opacity: 15%;
}

input {
  border: 1px solid rgba(255, 255, 255, 0.2);
  background-color: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);
  color: white;
  font-family: 'Poppins', sans-serif;
}

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
  font-family: 'Poppins', sans-serif;
}

button:hover {
  background: rgba(255, 255, 255, 0.4);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.4);
  transform: scale(1.2);
}

.need-an-account {
  display: inline-block;
  font-size: 0.8em;
  color: white;
  text-decoration: none;
  margin-top: .5em;
  transition: transform 0.2s ease-in-out;
  font-family: 'Poppins', sans-serif;
}

.need-an-account:hover {
  color: white;
  transform: scale(1.1);
}

</style>