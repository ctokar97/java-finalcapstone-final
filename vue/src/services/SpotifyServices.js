import axios from "axios";

export default {
    getSpotifyUserLogin() {
        return axios.get('/api/login');
    }
}