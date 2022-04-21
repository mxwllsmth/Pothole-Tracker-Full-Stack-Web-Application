<template>
<div id="table-container">
  <table>
    <thead>
      <tr>
        <th>Pothole ID</th>
        <th>Address</th>
        <th class="coordinate">Latitude</th>
        <th class="coordinate">Longitude</th>
        <th>Date Reported</th>
        <th>Progress Status</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="pothole in filteredList" v-bind:key="pothole.potholeId">
        <td>{{ pothole.potholeId }}</td>
        <td id="address">{{ pothole.streetAddress }}</td>
        <td class="coordinate">{{ pothole.latitude.substring(0,9) }}</td>
        <td class="coordinate">{{ pothole.longitude.substring(0,9) }}</td>
        <td>{{ pothole.reportedDate }}</td>
        <td v-if="pothole.progressStatusId === 1">Reported</td>
        <td v-else-if="pothole.progressStatusId === 2">Inspected</td>
        <td v-else-if="pothole.progressStatusId === 3">Repaired</td>
        <td v-else-if="pothole.progressStatusId === 4">
          Does Not Require Repair
        </td>
      </tr>
    </tbody>
  </table>
  </div>
</template>

<script>
import PotholeService from "@/services/PotholeService.js";

export default {
  name: "pothole-list",
  data() {
    return {
      filterStatus: '',
    };
  },
  methods: {
    getPotholes() {
      PotholeService.list().then((response) => {
        this.$store.commit("SET_POTHOLES", response.data);
      });
    },
    setPotholeDetail(potholeId) {
      PotholeService.get(potholeId).then((response) => {
        this.$store.commit("SET_ACTIVE_POTHOLE", response.data);
      });
    },
  },
  created() {
    this.getPotholes();
    if(this.$store.state.user.authorities[0].name == 'ROLE_ADMIN'){
      this.filterStatus = 1;
    }
  },
  computed: {
    filteredList() {
      let filteredPotholes = this.$store.state.potholes;
      if (this.filterStatus != '') {
        filteredPotholes = filteredPotholes.filter((pothole) => 
            pothole.progressStatusId == this.filterStatus
        );
      }
      return filteredPotholes;
    }
  }
};
</script>

<style scoped>

  #table-container {
    height: 30vh;
    overflow-y: scroll;
    overflow-x: hidden;
  }
  #table-container::-webkit-scrollbar {
    width: 10px;
}

#table-container::-webkit-scrollbar-track {
    background-color: rgb(212, 212, 212);
}

#table-container::-webkit-scrollbar-thumb {
    background-color: #00B2FF;
    border-radius: 5px 5px;
}

  table {
    text-align: center;
    width: 98vw;
    
  }
  td {
    padding: 4px;
  }
  tbody tr:nth-child(even) {
    background-color: #dddddd;
  }
  .ListRows tr:hover {
    background-color: #59cdffcc;
    cursor: pointer;
  }
  tbody {
    text-align: center;
  }
  #address {
    text-align: left;
  }
  
@media(max-width: 425px) {
 .coordinate {
   display: none;
 }
}
</style>