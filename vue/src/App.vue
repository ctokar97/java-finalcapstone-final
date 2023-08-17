<template>
  <div id="app">
    <div id="nav" class="home-logout">
      <div class="menu-items">
        <router-link class="home-router" v-bind:to="{ name: 'home' }">Home</router-link>
        <router-link class="logout-router" v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
      </div>
    </div>
    <router-view />
  </div>
</template>



<style>
@import url('https://fonts.cdnfonts.com/css/bukhari-script');
@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,400;0,500;1,900&display=swap');

body {
  min-height: 100vh;
  margin: 0;
  width: 100vw;
  overflow-x: hidden;
  color: white;
  background: linear-gradient(57deg, purple, pink, blue, green);
  background-size: 1000% 1000%;
  animation: ani 10s ease infinite;
}

@keyframes ani {
  0%{background-position:0% 25%}
  25%{background-position:25% 50%}
  50%{background-position: 50% 75%}
  75%{background-position: 75% 100%}
  100%{background-position: 100% 75%}
  75%{background-position: 75% 50%}
  50%{background-position: 50% 25%}
  25%{background-position: 25% 10%}
  0%{background-position: 10% 0%}


}

body::before {
  position: absolute;
  content: "";
  left: 30%;
  top: 20%;
  width: 450px;
  height: 450px;
  background: #17141d;
  border-radius: 62% 47% 82% 35% / 45% 45% 80% 66%;
  will-change: border-radius, transform, opacity;
  animation:sliderShape 5s linear infinite,
  move 5s linear infinite;
  display: block;
  z-index: -1;
  -webkit-animation: sliderShape 5s linear infinite, move 40s linear infinite;
  filter: blur(5px);
}

@keyframes sliderShape{
  0%,100%{
    border-radius: 42% 58% 70% 30% / 45% 45% 55% 55%;
    transform: translate3d(0,0,0) rotateZ(0.01deg);
  }
  34%{
    border-radius: 70% 30% 46% 54% / 30% 29% 71% 70%;
    transform:  translate3d(0,5px,0) rotateZ(0.01deg);
  }
  50%{
    transform: translate3d(0,0,0) rotateZ(0.01deg);
  }
  67%{
    border-radius: 100% 60% 60% 100% / 100% 100% 60% 60% ;
    transform: translate3d(0,-3px,0) rotateZ(0.01deg);
  }
}

@keyframes move {
  0% { top: 10%; left: 10%;}
  25% { top: 70%; left: 30%;}
  50% { top: 50%; left: 70%;}
  75% { top: 10%; left: 90%;}
  100% { top: 80%; left: 10%;}
  75% { top: 10%; left: 90%;}
  50% { top: 50%; left: 70%;}
  25% { top: 70%; left: 30%;}
  0% { top: 10%; left: 10%;}
}

.home-logout {
  color: navy;
  height: 2em;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin-right: 20px;
  text-decoration: none;
  font-family: 'Poppins', sans-serif;
  font-size: 30px;
  
  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);

  backdrop-filter: blur(10px);
  transition: transform 0.3s ease-in-out;
}

.menu-items {
  display: flex;
  justify-content: space-between;
  width: 10%;
}

.home-router {
font-size: 0.9em;
color: navy;
padding-left: 20px;
text-shadow: -2px 2px 5px rgba(0, 0, 0, 0.2);
}

.logout-router {
font-size: 0.9em;
color: navy;
padding-left: 30px;
text-shadow: -2px 2px 5px rgba(0, 0, 0, 0.2);
}


</style>
