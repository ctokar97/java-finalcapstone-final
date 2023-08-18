import axios from "axios";

export default {
    /**
     * Retrieves all party data from the server.
     *
     * @return {Promise} A Promise object representing the result of the GET request.
     */
    getAll() {
        return axios.get('/party');
    },

    /**
     * Retrieves party details by party name.
     *
     * @param {string} partyName - The name of the party to retrieve details for.
     *
     * @returns {Promise} - A Promise that resolves to the party details.
     */
    getPartyByName(partyName) {
        return axios.get(`/party/${partyName}`);
    },

    /**
     * Get the users in a party.
     *
     * @param {string} partyId - The ID of the party.
     * @returns {Promise} A Promise that resolves to the list of users in the party.
     */
    getUsersInParty(partyId) {
        return axios.get(`/party/${partyId}/users`);
    },

    /**
     * Retrieves the playlist for a given party.
     *
     * @param {string} partyId - The unique identifier of the party.
     * @return {Promise} - A promise that resolves to the playlist data.
     */
    getPlaylistInParty(partyId) {
        return axios.get(`/party/${partyId}/playlist`);
    },

    /**
     * Creates a new party.
     *
     * @param {Object} party - The party object containing the details of the party.
     * @return {Promise} - A promise that resolves to the response from the server.
     */
    createParty(party) {
        return axios.post('/party', party);
    },

    /**
     * Updates the information of a party.
     *
     * @param {Object} party - The party object containing the updated information.
     * @return {Promise} - A Promise that resolves with the response from the server.
     */
    updateParty(party) {
        return axios.put(`/party/${party.id}`, party);
    },

    assignPartyToUser(partyId, userId) {
        return axios.put(`/party/${partyId}/owner/${userId}`);
    },

    addUserToParty(partyId, userId) {
        return axios.post(`/party/${partyId}/user/${userId}`);
    }
}