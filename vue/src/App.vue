<template>
  <div id="app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    </div>
    <router-view />
  </div>
</template>



<style>
html {
  height: 100%;
  margin: 0;
}

body {
  min-height: 100vh;
  margin: 0;
  width: 100vw;
  overflow-x: hidden;
  color: #fff;
  background: linear-gradient(45deg, purple, pink, blue, green);
  background-size: 400% 400%;
  animation: gradientBG 15s ease infinite;
}

@keyframes gradientBG {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
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
</style>
