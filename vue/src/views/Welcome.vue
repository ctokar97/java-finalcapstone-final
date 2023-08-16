<template>
  <div  class="welcome">
    <div class="content-wrapper">
      <h1>Harmony Mix</h1>
      <img class="welcome-image" src="@/images/HarmonyMix.jpg" alt="">
      <h2>Welcome To Harmony Mix! Where all your party playlist needs live!</h2>
      <button @click="goToLogin" type="button">Click here to get started!</button>
      </div>
    </div>
</template>

<script>
export default {

methods: {
  goToLogin() {
    this.$router.push({ name: 'login' });
  }
}
}
</script>

<style scoped>

@import url('https://fonts.cdnfonts.com/css/bukhari-script');

.welcome {
  margin: auto;
  width: 100vw;
  height: 100vh;

}

.content-wrapper {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  height: 100%;
}

h1 {
  color: purple;
  font-size: 200px;
  display: flex;
  margin: 0;
  font-family: 'Bukhari Script', sans-serif;

  
}

h2{
    color:purple;
  display: flex;
  margin: 0;
  font-size: 25px;  
  font-family: 'Bukhari Script', sans-serif;
}

button{
   display: flex;
  justify-content: center;
  align-items: center;  
  padding: 15px 32px;
  text-align: center;
  font-size: 16px;
  background-color: purple;
  border-radius: 24px;
  font-family: 'Bukhari Script', sans-serif;
}

img {

  width: 20%;
  

}


</style>