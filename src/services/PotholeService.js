import axios from 'axios';
import store from '../store/index.js';

const http = axios.create({
    baseURL: "http://localhost:8080"
});

const headers = {
    headers: {
        Authorization: 'Bearer ' + store.state.token
    }
}

export default {

    list() {
        return http.get('/potholes', headers);
    },
    get(id) {
        return http.get(`/potholes/${id}`, headers);
    },
    addPothole(pothole) {
        return http.post('/potholes/createPothole', pothole, headers);
    },
    updatePothole(pothole) {
        return http.put(`/potholes/updatePothole/${pothole.potholeId}`, pothole, headers);
    },
    deletePothole(id) {
        return http.delete(`/potholes/deletePothole/${id}`, headers);
    },
    getByUserID(id) {
        return http.get(`/potholes/user/${id}`, headers);
    }
}
