import axios from "axios";

export default {

    /**
     * Retrieves all user data from the server.
     *
     * @function getAll
     * @returns {Promise} A promise that resolves to the user data retrieved from the server.
     */
    getAll() {
        return axios.get('/user');
    },
    
    /**
     * Retrieves user information by username.
     *
     * @param {string} username - The username of the user to retrieve.
     *
     * @return {Promise<any>} - A Promise that resolves to the user information.
     */
    getUserByUsername(username) {
        return axios.get(`/user/${username}`);
    },

    changeUserRole(userId, role) {
        return axios.put(`/user/${userId}`, {role: role});
    }
}