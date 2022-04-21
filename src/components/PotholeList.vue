<template>
  <div id="table-container">
    <table>
      <thead>
        <tr>
          <th id="sort-toggle" v-on:click="changeSortToggle(), sortList()">
            Pothole ID
          </th>
          <th>Address</th>
          <th class="coordinate">Latitude</th>
          <th class="coordinate">Longitude</th>
          <th id="sort-toggle" v-on:click="changeSortToggle(), sortList()">
            Date Reported
          </th>
          <th>Progress Status</th>
        </tr>
        <tr>
          <th></th>
          <th></th>
          <th class="coordinate"></th>
          <th class="coordinate"></th>
          <th></th>
          <th>
            <select
              id="statusFilter"
              v-model="filterStatus"
              v-show="$store.state.user.authorities[0].name == 'ROLE_ADMIN'"
            >
              <option value="">Show All</option>
              <option value="1">Show Reported</option>
            </select>
          </th>
        </tr>
      </thead>
      <tbody class="ListRows">
        <tr
          v-for="pothole in filteredList"
          v-bind:key="pothole.potholeId"
          v-on:click="
            setPotholeDetail(pothole.potholeId)">
          <td>{{ pothole.potholeId }}</td>
          <td id="address">{{ pothole.streetAddress }}</td>
          <td class="coordinate">{{ pothole.latitude.substring(0, 9) }}</td>
          <td class="coordinate">{{ pothole.longitude.substring(0, 9) }}</td>
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
      filterStatus: "",
      sortToggle: false,
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
        this.$router.push({
          name: "pothole-detail",
          params: { id: potholeId },
        });
      });
    },
    changeSortToggle() {
      this.sortToggle = !this.sortToggle;
    },
  },
  created() {
    this.getPotholes();
    if (this.$store.state.user.authorities[0].name == "ROLE_ADMIN") {
      this.filterStatus = 1;
    }
  },
  computed: {
    filteredList() {
      let filteredPotholes = this.$store.state.potholes;
      if (this.filterStatus != "") {
        filteredPotholes = filteredPotholes.filter(
          (pothole) => pothole.progressStatusId == this.filterStatus
        );
      }
      return filteredPotholes;
    },
    sortList() {
      let sortedPotholes = this.$store.state.potholes;

      if (this.sortToggle) {
        sortedPotholes = sortedPotholes.sort((a, b) => {
          return a.potholeId - b.potholeId;
        });
      } else if (!this.sortToggle) {
        sortedPotholes = sortedPotholes.sort((a, b) => {
          return b.potholeId - a.potholeId;
        });
      }
      return sortedPotholes;
    },
  },
};
</script>

<style scoped>
#table-container {
  height: 29vh;
  overflow-y: scroll;
}
#table-container::-webkit-scrollbar {
  width: 10px;
}

#table-container::-webkit-scrollbar-track {
  background-color: rgb(212, 212, 212);
}

#table-container::-webkit-scrollbar-thumb {
  background-color: #00b2ff;
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
#sort-toggle:hover {
  background-color: #00b2ff;
  color: white;
  cursor: pointer;
  border-radius: 5px;
}

@media (max-width: 425px) {
  .coordinate {
    display: none;
  }
  #table-container {
    height: 26.5vh;
  }
}
</style>