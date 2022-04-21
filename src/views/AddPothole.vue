<template>
  <div>
    <div class="img-container" v-if="$store.state.isLoading">
      <img  class="loading" id="wheel" src="../../public/PotholeTrackerLogo.png" alt="wheel logo" />
    </div>
    <div v-else>
      <add-pothole/>
    </div>
  </div>
</template>

<script>
import AddPothole from '../components/AddPothole.vue'

export default {
  components: { AddPothole },
  created() {
    this.$store.state.isLoading = true;
    setTimeout(() => (this.$store.state.isLoading = false), 1500);
  },
}
</script>

<style scoped>
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
}
}
</style>