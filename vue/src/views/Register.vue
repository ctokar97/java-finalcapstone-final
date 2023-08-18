<template>
  <transition name="fade" v-if="show">
    <div id="register">
      <form class="register-form" @submit.prevent="register">
        <h1>Create Account</h1>
        <div role="alert" v-if="registrationErrors">
          {{ registrationErrorMsg }}
        </div>
        <div class="form-input-group">
          <label for="username">Username</label>
          <input type="text" id="username" v-model="user.username" required autofocus/>
        </div>
        <div class="form-input-group">
          <label for="password">Password</label>
          <input type="password" id="password" v-model="user.password" required/>
        </div>
        <div class="form-input-group">
          <label for="confirmPassword">Confirm Password</label>
          <input type="password" id="confirmPassword" v-model="user.confirmPassword" required/>
        </div>
        <div class="button-group">
          <button type="submit" @click="makeUserRoleDJ">Create DJ Account</button>
          <button class="regular-account-button" type="submit" @click="makeUserRoleUser">Create Regular Account</button>
        </div>
        <router-link class="already-have-account" :to="{ name: 'login' }">Already have an account? Log in.</router-link>
      </form>
    </div>
  </transition>
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
      show: false
    };
  },
  methods: {
    makeUserRoleDJ() {
      this.user.role = 'DJ';
    },
    makeUserRoleUser() {
      this.user.role = 'user';
    },
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
            .register(this.user)
            .then((response) => {
              if (response.status == 201) {
                this.$router.push({
                  path: '/login',
                  query: {registration: 'success'},
                });
              }
            })
            .catch((error) => {
              const response = error.response;
              this.registrationErrors = true;
              if (response.status === 400) {
                this.registrationErrorMsg = 'Bad Request: Validation Errors';
              }
            });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
  mounted() {
    this.show = true;
  }
};
</script>

<style scoped>

@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,400;0,500;1,900&display=swap');

h1 {
  margin-bottom: .5em;
}

.form-input-group {
  margin-bottom: 1rem;
}

label {
  margin-right: 0.5rem;
  font-family: 'Poppins', sans-serif;
}

#register {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.register-form {
  width: 40%;
  height: 50%;
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

.fade-enter-active, .fade-leave-active {
  transition: opacity 1s;
}

.fade-enter, .fade-leave-to {
  opacity: 15%;
}

.form-input-group {
  display: flex;
  flex-direction: column;
  font-family: 'Poppins', sans-serif;
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
  height: 3.5em;
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
  z-index: 1;
}

.regular-account-button {
  height: 3.1em;
  margin-left: 1.3em;
  margin-right: 1.3em;
  font-family: 'Poppins', sans-serif;
}

.already-have-account {
  display: inline-block;
  font-size: 0.8em;
  color: white;
  text-decoration: none;
  margin-top: .5em;
  transition: transform 0.2s ease-in-out;
  font-family: 'Poppins', sans-serif;
}

.already-have-account:hover {
  transform: scale(1.1);
}

.button-group {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 3em;
  margin-bottom: 1em;
}

</style>
