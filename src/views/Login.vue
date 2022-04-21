<template>
  <div class="login-page">
    <div id="login" class="text-center" v-if="this.$store.state.showLoginForm">
      <form class="form-signin" @submit.prevent="login">
        <h3 class="h3 mb-3 font-weight-normal">Please Sign In To View More Pothole Info</h3>
        <div
          class="alert alert-danger"
          role="alert"
          v-if="invalidCredentials"
        >Invalid username and password!</div>
        <div
          class="alert alert-success"
          role="alert"
          v-if="this.$route.query.registration"
        >Thank you for registering, please sign in.</div>
        <div class="flex">
          <div class="media-flex">
            <label for="username" class="sr-only">Username</label>
            <input
              type="text"
              id="username"
              class="form-control"
              placeholder="Username"
              v-model="user.username"
              required
              autofocus
            />
          </div>
          <div class="media-flex">
            <label for="password" class="sr-only">Password</label>
            <input
              type="password"
              id="password"
              class="form-control"
              placeholder="Password"
              v-model="user.password"
              required
            />
          </div>
          <div class="media-flex">
          <button type="submit" class="btn sign-in-button" >Sign in</button>
            </div>
          <div class="media-flex">
            <router-link :to="{ name: 'register' }" class="sr-only">Need an account?</router-link>
          </div>
        </div>
      </form>
    </div>
    <h1 class="h1">Look At All Those Potholes</h1>
    <div id="login-list-map">
    <pothole-home />
    <base-map />
    </div>
  </div>
</template>

<script>
import authService from "../services/AuthService";
import PotholeHome from "../components/PotholeHome.vue";
import BaseMap from "../components/BaseMap.vue";

export default {
  name: "login",
  components: { PotholeHome, BaseMap },
  data() {
    return {
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
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
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>

  .sr-only {
    padding: 10px;
  }
  .sign-in-button {
    margin-left: 10px;
  }
  #login-list-map {
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 81.6vh;
    width: 98.9vw;
  }

  .h1 {
    margin-left: 10px;
  }

  .h3 {
    margin-left: 10px;
  }
  .alert {
    margin-left: 10px;
  }

  .flex {
    display: flex;
  }

@media(max-width: 425px) {
 .media-flex {
   display: flex;
   flex-direction: column;
   max-width: 50vw;
 }
 #login-list-map {
   height: 70vh;
 }
 .h1 {
   font-size: 22px;
   margin-left: 0;
 }
}
</style>
