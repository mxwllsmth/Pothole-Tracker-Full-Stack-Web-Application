<template>
  <div id="app">
    <div id="header">
      <img id="logo" src="../public/PotholeTrackerLogo.png" />
      <h1>PHL Pothole Tracker</h1>
      <div id="nav" v-if="$store.state.token != ''">
        <router-link class="link" v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
        <router-link class="link" v-bind:to="{ name: 'logout' }">Logout</router-link>
      </div>
      <button class="btn" id="login-button" v-if="$store.state.token == ''" v-on:click="changeLoginForm()">Login</button>
    </div>
    <router-view />
  </div>
</template>

<script>
export default{
  data() {
    return {
      showLoginForm: false
    }
  },
  methods: {
    changeLoginForm(){
      this.$store.state.showLoginForm = !this.$store.state.showLoginForm 
    }
  }
}
</script>


<style scoped>
  * {
    font-family: Tahoma, Verdana, sans-serif;
  }

  #header {
    display: flex;
    color: white;
    background-image: linear-gradient(
    to bottom right, #6dd3ff 0%, #00B2FF 40%, #0485ff 60%, #0051ff 100%);
    background-size: 200% 200%;
    animation: movingGradient 7.5s linear infinite alternate;
    justify-content: space-between;
    align-items: center;
    border-bottom-style: solid;
    width: 100vw;
  }
  @keyframes movingGradient {
    from {background-position: 0 0;}
    to {background-position: 100% 100%;}
  }

  #logo {
    height: 100px;
    width: 100px;
    border-style: solid;
    border-radius: 100%;
    border-width: 5px;
    border-color: white;
    margin: 5px;
  }

  #nav {
    color: white;
    padding-right: 15px;
  }

  #login-button {
    margin-right: 15px;
    border-style: solid;
    border-width: 2px;
    border-color: white;
  }

  .link {
    color: white;
    text-decoration: none;
  }

  .link:hover {
    text-decoration: underline;
  }
    .btn {
    margin-right: 5px;
    border-radius: 6px;
    font-weight: 400;
    background-color: #00B2FF;
    color: white;
    text-align: center;
    vertical-align: middle;
    border: 1px solid;
    font-size: 1rem;
    line-height: 1.5;
    border-radius: .25rem;
    cursor: pointer;
  }
  @media(max-width: 425px) {
    #nav {
      display: flex;
    }
    h1 {
      font-size: 28.5px;
      padding-left: 5px;
      max-width: 40%;
    }
  }
</style>