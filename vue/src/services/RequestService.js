import axios from "axios";

export default {

    /**
     * Retrieves all requests from the server.
     *
     * @returns {Promise} A promise that resolves to the response object containing the requests.
     */
    getAllRequests() {
        return axios.get('/request');
    },
    /**
     * Retrieves requests by party ID.
     *
     * @param {string} partyId - The ID of the party.
     *
     * @return {Promise} A promise that resolves with the request data.
     */
    getRequestsByPartyId(partyId) {
        return axios.get(`/request/${partyId}`);
    },
    /**
     * Creates a new request using the given request data.
     *
     * @param {object} request - The request data to be sent.
     * @return {Promise} - A promise that resolves to the response of the request.
     */
    createRequest(request) {
        return axios.post('/request', request);
    },
    /**
     * Deletes a request with the specified ID.
     *
     * @param {number} requestId - The ID of the request to delete.
     *
     * @return {Promise} - A promise that resolves when the request is successfully deleted.
     */
    deleteRequest(requestId) {
        return axios.delete(`/request/${requestId}`);
    }
}