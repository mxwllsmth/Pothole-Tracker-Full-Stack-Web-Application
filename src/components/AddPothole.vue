<template>
  <div id="mapContainer" class="basemap">
    <div class="flex">
      <div class="map-holder">
        <div id="map"></div>
      </div>
        <div class="display-area">
          <div class="coordinates-header">
            <h3>Pothole Coordinates</h3>
            <p>Latitude: {{ center[0] }}</p>
            <p>Longitude: {{ center[1] }}</p>
          </div>
          <div class="coordinates-header">
            <h3>Pothole Location</h3>
            <textarea 
              class="location-box" 
              placeholder="Use Map Search Bar to Enter a Location (Required)" 
              v-model="location" 
              required 
              readonly
            ></textarea>
            <h3>Notes</h3>
            <textarea
                type="text"
                class="location-control"
                v-model="notes"
                placeholder="Enter Notes (Optional)" 
            ></textarea>
            <button class="btn" id="submit-button" type="submit" v-show="location != null" v-on:click="addPothole()">Submit Report</button>
          </div>
        </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import mapboxgl from "mapbox-gl";
import MapboxGeocoder from "@mapbox/mapbox-gl-geocoder";
import "@mapbox/mapbox-gl-geocoder/dist/mapbox-gl-geocoder.css";
import PotholeService from "../services/PotholeService.js";

export default {
  name: "add-pothole-map",
  data() {
    return {
      loading: false,
      location: null,
      access_token: 'pk.eyJ1IjoiY2Fwc3RvbmUyMDIyIiwiYSI6ImNsMXZpNGhpODMyMGMzZWp4bHlnZXgwcHYifQ.-R9gcADgsFp36mSwSwrr-g',
      center: [-75.15, 39.95],
      map: {},
      notes: ""
    };
  },
  mounted() {
  this.createMap()
},

methods: {
  async createMap() {
    try {
      mapboxgl.accessToken = this.access_token;
      this.map = new mapboxgl.Map({
        container: "map",
        style: "mapbox://styles/mapbox/streets-v11",
        center: this.center,
        location: this.location,
        zoom: 11,
      });
    
    let geocoder =  new MapboxGeocoder({
      accessToken: this.access_token,
      mapboxgl: mapboxgl,
      marker: false,
    });

    this.map.addControl(geocoder);

    geocoder.on("result", (e) => {
      const marker = new mapboxgl.Marker({
        draggable: true,
        color: "#D80739",
      })
        .setLngLat(e.result.center)
        .addTo(this.map);
      this.center = e.result.center;
      this.location = e.result.location;
      this.getLocation();
      marker.on("dragend", (e) => {
        this.center = Object.values(e.target.getLngLat());
        this.getLocation();
      });
    });
    } catch (err) {
      console.log("map error", err);
    }
  },
    async getLocation() {
  try {
    this.loading = true;
    const response = await axios.get(
      `https://api.mapbox.com/geocoding/v5/mapbox.places/${this.center[0]},${this.center[1]}.json?access_token=${this.access_token}`
    );
    this.loading = false;
    this.location = response.data.features[0].place_name;
  } catch (err) {
    this.loading = false;
    console.log(err);
  }
  },
  copyLocation() {
    if (this.location) {
      navigator.clipboard.writeText(this.location);
    alert("Location Copied")
  }
  return;
},
addPothole() {
      const pothole = {
        userId: this.$store.state.user.id,
        progressStatusId: 1,
        severityId: null,
        latitude: this.center[0],
        longitude: this.center[1],
        streetAddress: this.location,
        reportedDate: "",
        inspectedDate: "",
        repairedDate: "",
        notes: this.notes,
      };
      let isDuplicate = false;
      this.$store.state.potholes.forEach((ph) => {
        if (ph.latitude == pothole.latitude && ph.longitude == pothole.longitude) {
          isDuplicate = true;
          alert("Pothole already exists");
        }
        });
        if (isDuplicate === false) {
          PotholeService
            .addPothole(pothole)
            .then((response) => {
              if (response.status == 200) {
                this.$router.push('/');
              }
            })
            .catch((error) => {
              if (error.response.status == 404) {
                this.$router.push('/');
              }
            });
        }
      }
    }
}
</script>

<style scoped>
.baseMap {
  padding: 45px 50px;
}
.flex {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
.map-holder {
  width: 65%;
}
#map {
  height: 70vh;
}
.display-area {
  background: #ffffff;
  box-shadow: 0px -3px 10px rgba(0, 58, 78, 0.1);
  border-radius: 5px;
  padding: 20px 30px;
  width: 25%;
}
.coordinates-header {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
}
.coordinates-header h3 {
  color: #153f99;
  font-weight: 600;
}
.coordinates-header p {
  color: rgb(59, 59, 59);
  font-weight: 600;
  font-size: 0.875rem;
}
.form-group {
  position: relative;
}
.location-box {
  background: #ffffff;
  border: 1px solid rgba(31, 42, 83, 0.25);
  box-shadow: 0px 0px 10px rgba(73, 165, 198, 0.1);
  border-radius: 4px;
  width: 20em;
  height: 5em;
  word-wrap: break-word;
  cursor: default;
}
.location-box:focus {
  outline: none;
}
.location-control {
  background: #ffffff;
  border: 1px solid rgba(31, 42, 83, 0.25);
  box-shadow: 0px 0px 10px rgba(73, 165, 198, 0.1);
  border-radius: 4px;
  width: 20em;
  height: 10em;
  word-wrap: break-word;
}
.location-control:focus {
  outline: none;
}
#submit-button {
  margin-top: 50px;
  max-width: 150px;
}
@media(max-width: 425px) {
 .map-holder {
  width: 100vw;
 }
 #map {
   height: 40vh;
 }
 .display-area {
   width: 100vw;
   padding-top: 0;
 }
 p {
   margin: 0;
 }
 
}
</style>