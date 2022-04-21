<template>
  <div>
    <div class="img-container" v-if="$store.state.isLoading">
      <img class="loading" id="wheel" src="../../public/PotholeTrackerLogo.png" alt="wheel logo" />
    </div>
    <div v-else>
      <div id="sub-header">
        <h1>Look At All Those Potholes</h1>
        <router-link :to="{ name: 'add-pothole' }" v-show="$store.state.user.authorities[0].name == 'ROLE_USER'">
          <button class="btn" type="submit">Report Pothole</button>
        </router-link>
      </div>
      <div class="home">
        <pothole-list />
        <base-map />
      </div>
    </div>
  </div>
</template>

<script>
import BaseMap from "../components/BaseMap.vue";
import PotholeList from "../components/PotholeList.vue";

export default {
  name: "home",
  components: {
    BaseMap,
    PotholeList,
  },
  created() {
    this.$store.state.isLoading = true;
    setTimeout(() => (this.$store.state.isLoading = false), 1500);
  },
};
</script>

<style>
.home {
  display: flex;
  flex-direction: column;
  height: 81.6vh;
  width: 98.9vw;
  align-items: center;
}

h1 {
  padding: 5px;
}

#sub-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.btn {
  margin-right: 5px;
  border-radius: 6px;
  font-weight: 400;
  background-color: #00b2ff;
  color: white;
  text-align: center;
  vertical-align: middle;
  border: 1px solid;
  font-size: 1rem;
  line-height: 1.5;
  border-radius: 0.25rem;
  cursor: pointer;
}
.loading {
  -webkit-animation-name: spin;
  -webkit-animation-duration: 1550ms;
  -webkit-animation-iteration-count: infinite;
  -webkit-animation-timing-function: linear;
  -moz-animation-name: spin;
  -moz-animation-duration: 1550ms;
  -moz-animation-iteration-count: infinite;
  -moz-animation-timing-function: linear;
  -ms-animation-name: spin;
  -ms-animation-duration: 1550ms;
  -ms-animation-iteration-count: infinite;
  -ms-animation-timing-function: linear;
  -o-transition: rotate(3600deg);
}
#wheel{
  width: 200px;
  height: 200px;
  margin: 20% 44% 20% 44%;
}
@-moz-keyframes spin {
    from { -moz-transform: rotate(0deg); }
    to { -moz-transform: rotate(360deg); }
}
@-webkit-keyframes spin {
    from { -webkit-transform: rotate(0deg); }
    to { -webkit-transform: rotate(360deg); }
}
@keyframes spin {
    from {transform:rotate(0deg);}
    to {transform:rotate(360deg);}
}
@media(max-width: 425px) {
 #wheel {
  width: 150px;
  height: 150px;
  margin-right: 70%;
 }
.img-container {
  width: 65vw;
  margin-top: 45%;
}
h1 {
  margin: 4px;
  font-size: 22px;
}
.home {
  height: 72.5vh;
}
}
</style>
