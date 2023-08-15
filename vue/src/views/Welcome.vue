<template>
  <div  class="welcome">
      <h1>Melody Moods</h1>
      <img src="public\MMLogo.png" alt="">
      <h2>Welcome To Melody Moods! Where all your party needs live!</h2>
      <button type="button">Click here to login!</button>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
*{
    border:1px solid red
}
.welcome {
  display: flex;
  flex-direction: column;
  width: 100vw;
  margin: auto;
  position: absolute;
  top: 50%;

}

h1 {
  color: purple;
  font-size: 100px;
  display: flex;
  justify-content: center;
  align-items: center;
  
}

h2{
    color:purple;
  display: flex;
  font-size: 25px;  
  justify-content: center;
  align-items: center;
}

.button{
   display: flex;
  justify-content: center;
  align-items: center;  
  width: 2px;
  height: 25px;
  padding: 15px 32px;
  text-align: center;
  font-size: 16px;
}

</style>