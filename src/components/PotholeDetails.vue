<template>
<div class="pothole">
  <div class="details">
    <p>Pothole ID: <span>{{ activePothole.potholeId }}</span></p>
    <p>Address: <span>{{ activePothole.streetAddress }}</span></p>
    <p>Latitude: <span>{{ activePothole.latitude }}</span></p>
    <p>Longitude: <span>{{ activePothole.longitude }}</span></p>
    <p>Date Reported: <span>{{ activePothole.reportedDate }}</span></p>
    <p>Date Inspected: <span>{{ activePothole.inspectedDate }}</span></p>
    <p>Date Repaired: <span>{{ activePothole.repairedDate }}</span></p>
    <p id="p">Progress Status: <span v-if="activePothole.progressStatusId === 1 ">Reported</span>
      <span v-else-if="activePothole.progressStatusId === 2">Inspected</span>
      <span v-else-if="activePothole.progressStatusId === 3">Repaired</span>
      <span v-else-if="activePothole.progressStatusId === 4">Does Not Require Repair</span></p>
    <p>Severity: <span v-if="activePothole.severityId === 1">Mild</span>
      <span v-else-if="activePothole.severityId === 2">Moderate</span>
      <span v-else-if="activePothole.severityId === 3">Severe</span></p>
    <p>Notes: <span>{{ activePothole.notes }}</span></p>
  </div>
  <div id="map-buttons">
  <img class="pic" :src="`https://api.mapbox.com/styles/v1/mapbox/streets-v11/static/pin-s+ff0000(${activePothole.latitude},${activePothole.longitude})/${activePothole.latitude},${activePothole.longitude},15,0/500x300?access_token=pk.eyJ1IjoiY2Fwc3RvbmUyMDIyIiwiYSI6ImNsMXZpNGhpODMyMGMzZWp4bHlnZXgwcHYifQ.-R9gcADgsFp36mSwSwrr-g`">
  <div class="button">
    <button class="btn" type="update" v-on:click="changeUpdateForm" v-show="$store.state.user.authorities[0].name == 'ROLE_ADMIN'">Update</button>
    <button class="btn" type="btnDeletePothole" v-on:click="deletePothole(activePothole.potholeId)" v-show="$store.state.user.authorities[0].name == 'ROLE_ADMIN'">Delete</button>
    <div v-if="showUpdateForm">
    <update-pothole/>
  </div>
  </div>
  </div>
</div>
  
</template>

<script>
import PotholeService from "../services/PotholeService.js";
import UpdatePothole from "../components/UpdatePothole.vue"

export default {
    name: "pothole-detail",
    data() {
      return {
        showUpdateForm: false
      }
    },
    components: {
      UpdatePothole,
    },
    methods: {
      getPotholeDetail() {
        PotholeService.get(this.$route.params.id).then(response => {
          this.$store.commit("SET_ACTIVE_POTHOLE", response.data);
        });
      },
      deletePothole(id) {
      PotholeService.deletePothole(id)
      .then(response =>{
        if(response.status===200){
          this.$store.commit("DELETE_POTHOLE", id);
          this.$router.push("/");
        }
      })
      .catch(error => {
        if (error.response.status == 404) {
          this.$router.push("/not-found");
        }
      });
    },
    mounted() {
      this.getPotholeDetail();
    },
    changeUpdateForm(){
      this.showUpdateForm = !this.showUpdateForm
    }
  },
  computed: {
      activePothole() {
        return this.$store.state.activePothole;
      }
    }
}
</script>

<style scoped>
.details {
 font-weight: bold;
}

span {
  font-weight: normal;
}

.pothole {
  display: flex;
  padding-left: 20px;
  justify-content: space-evenly;
  align-items: flex-start;
  padding-top: 20px;
}

#map-buttons {
  display: flex;
  flex-direction: column;
  padding-right: 20px;
}

.pic {
  padding-top: 35px;
}

@media(max-width: 1024px) { 
  
}
  
@media(max-width: 425px) {
 .pothole {
   flex-direction: column;
   padding: 0px;
 }
 .pic {
   max-width: 100vw;
   padding: 0;
 }
 #map-buttons {
   padding: 20px 0 0 0;
 }
 .details {
   padding: 10px;
 }
}
</style>