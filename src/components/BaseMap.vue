<template>
  <div id="mapContainer" class="basemap">
    <div class="flex">
      <div class="map-holder">
        <div id="map" v-on:click="loadMarkers()"></div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import mapboxgl from "mapbox-gl";
import MapboxGeocoder from "@mapbox/mapbox-gl-geocoder";
import "@mapbox/mapbox-gl-geocoder/dist/mapbox-gl-geocoder.css";


export default {
  name: "base-map",
  data() {
    return {
      loading: false,
      location: "",
      access_token: 'pk.eyJ1IjoiY2Fwc3RvbmUyMDIyIiwiYSI6ImNsMXZpNGhpODMyMGMzZWp4bHlnZXgwcHYifQ.-R9gcADgsFp36mSwSwrr-g',
      center: [-75.15, 39.95],
      map: {},
      notes: ""
    };
  },
  mounted() {
  this.createMap();
  this.loadMarkers();
},

methods: {
  async createMap() {
    try {
      mapboxgl.accessToken = this.access_token;
      this.map = new mapboxgl.Map({
        container: "map",
        style: "mapbox://styles/mapbox/streets-v11",
        center: this.center,
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
        color: "#D80739",
      })
        .setLngLat(e.result.center);
      this.center = e.result.center;
      marker.on("dragend", (e) => {
        this.center = Object.values(e.target.getLngLat());
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
    async loadMarkers() {
        this.$store.state.potholes.forEach((pothole) => {
          const coordinate = [Number(pothole.latitude), Number(pothole.longitude)];
          
          new mapboxgl.Marker({
            color: "#D80739",
          })
          .setLngLat(coordinate)
          .addTo(this.map);
        });
      }
  },
}
</script>

<style scoped>
.baseMap {
  padding: 45px 45px;
}
#mapContainer {
  display: flex;
  flex-direction: column;
  width: 100vw;
}
.flex {
  display: flex;
  flex-direction: column;
  justify-content: space-between;;
}
.map-holder {
  width: 100vw;
  padding-top: 10px;
}
#map {
  height: 50vh;
}
.dislpay-arena {
  background: #ffffff;
  box-shadow: 0px -3px 10px rgba(0, 58, 78, 0.1);
  border-radius: 5px;
  padding: 20px 30px;
  width: 25%;
}
.coordinates-header {
  margin-bottom: 50px;
}
.coordinates-header h3 {
  color: #1f2a53;
  font-weight: 600;
}
.coordinates-header p {
  color: rgba(13, 16, 27, 0.75);
  font-weight: 600;
  font-size: 0.875rem;
}
.form-group {
  position: relative;
}
.location-control {
  height: 30px;
  background: #ffffff;
  border: 1px solid rgba(31, 42, 83, 0.25);
  box-shadow: 0px 0px 10px rgba(73, 165, 198, 0.1);
  border-radius: 4px;
  padding: 0px 10px;
  width: 90%;
}
.location-control:focus {
  outline: none;
}

.location-btn:focus {
  outline: none;
}

.disabled {
  background: #db7990;
  cursor: not-allowed;
}

@media(max-width: 425px) {
  #mapContainer {
    justify-content: center;
    align-items: center;
    height: 30vh;
    padding-top: 84px;
  }
}

</style>