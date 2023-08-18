<template>
  <div  class="welcome">
    <div class="content-wrapper">
      <h1>Harmony Mix</h1>
      <h2>Welcome To Harmony Mix! Where all your party playlist needs live!</h2>
      
      
      <button @click="goToLogin" type="button">Click here to get started!</button>
      <img class="welcome-image" src="../images/HarmonyMix.jpg" alt="">
      <h3>Disclamer: This is a country music free application. Do not attempt to play country music.</h3>
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
@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,400;0,500;1,900&display=swap');

.welcome {
  width: 100vw;
  height: 100vh;
  padding-top: 4em;

}

.content-wrapper {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  height: 100%;
}

h1 {
  color: #ffffff;
  font-size: 200px;
  display: flex;
  margin: 0;
  font-family: 'Poppins', sans-serif;
  text-shadow: -7px 7px 5px rgba(0, 0, 0, 0.2);

  
}

h2{
    color: #ffffff;
  display: flex;
  margin: 0;
  font-size: 30px;  
  font-family: 'Poppins', sans-serif;
text-shadow: -2px 2px 5px rgba(0, 0, 0, 0.2);
}

h3{
    color: #ffffff;
  display: flex;
  margin: 0;
  font-size: 15px;  
  font-family: 'Poppins', sans-serif;
  text-shadow: -2px 2px 5px rgba(0, 0, 0, 0.2);
}

button{
  display: flex;
  justify-content: center;
  align-items: center;  
  padding: 15px 32px;
  text-align: center;
  font-size: 16px;
  color: #ffffff;
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
  transform: scale(1.2);
}

img {
  width: 20%;
}


</style>