<template>
  <form v-on:submit.prevent>
    <div class="field">
      <label for="progressStatus">Progress Status: </label>
      <select id="progressStatus" v-model="progressStatusId">
        <option value="null" selected disabled hidden>Select an option</option>
        <option value="1">Reported</option>
        <option value="2">Inspected</option>
        <option value="3">Repaired</option>
        <option value="4">Does not require repair</option>
      </select>
    </div>
    <div class="field">
      <label for="severity">Severity:</label>
      <select id="severity" v-model="severityId">
        <option value="null" selected disabled hidden>Select an option</option>
        <option value="1">Mild</option>
        <option value="2">Moderate</option>
        <option value="3">Severe</option>
      </select>
    </div>
    <div class="field">
      <label for="inspectedDate">Inspected Date: </label>
      <input type="date" v-model="inspectedDate">
    </div>
    <div class="field">
      <label for="repairedDate">Repaired Date: </label>
      <input type="date" v-model="repairedDate">
    </div>
    <div class="field">
      <label for="notes">Notes: </label>
      <textarea id="notes-box" rows="5" cols="60" type="text" placeholder="Enter Notes" v-model="notes"></textarea>
    </div>
    <div class="actions">
      <button type="submit" v-on:click="updatePothole()" class="btn">Update Pothole</button>
    </div>
  </form>
</template>

<script>
import PotholeService from "../services/PotholeService.js";

export default {
  name: "update-pothole",
  data() {
    return {
      potholeId: null,
      progressStatusId: null,
      severityId: null,
      inspectedDate: "",
      repairedDate: "",
      notes: null,
    };
  },
  methods: {
    updatePothole() {
      const pothole = {
        potholeId: this.$store.state.activePothole.potholeId,
        userId: this.userId,
        progressStatusId: this.progressStatusId,
        severityId: this.severityId,
        latitude: this.latitude,
        longitude: this.longitude,
        streetAddress: this.streetAddress,
        reportedDate: this.reportedDate,
        inspectedDate: this.inspectedDate.toString(),
        repairedDate: this.repairedDate.toString(),
        notes: this.notes,
      };
      if(pothole.progressStatusId === null){
        pothole.progressStatusId = this.$store.state.activePothole.progressStatusId;
      }
      if(pothole.severityId === null){
        pothole.severityId = this.$store.state.activePothole.severityId;
      }
      if(pothole.inspectedDate === ""){
        pothole.inspectedDate = this.$store.state.activePothole.inspectedDate;
      }
      if(pothole.repairedDate === ""){
        pothole.repairedDate = this.$store.state.activePothole.repairedDate;
      }
      if(pothole.notes === null){
        pothole.notes = this.$store.state.activePothole.notes;
      }
      PotholeService
        .updatePothole(pothole)
        .then((response) => {
          if (response.status == 200) {
            this.$router.push("/");
          }
        })
        .catch((error) => {
          if (error.response.status == 404) {
            this.$router.push("/");
          }
        });
    },
  },
  mounted() {
    PotholeService
      .get(this.$route.params.id)
      .then((response) => {
        this.$store.commit("SET_ACTIVE_POTHOLE", response.data);
      })
      .catch((error) => {
        if (error.response.status == 404) {
          this.$router.push({ name: "NotFound" });
        }
      });
  },
};
</script>

<style scoped>
  form {
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
    max-width: 40vw;
    font-weight: bold;
  }

  .field {
    display: flex;
    flex-direction: row;
    padding: 10px;
    flex-wrap: wrap;
  }

  .field > label {
    padding-right: 10px;
  }

  #notes-box {
    width: 20em;
    height: 10em;
    word-wrap: break-word;
  }

  .actions {
    display: flex;
    padding: 5px;
    justify-content: center;
  }

</style>