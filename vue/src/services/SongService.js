import axios from 'axios';

export default {

    /**
     * Retrieves all the songs from the server.
     *
     * @returns {Promise} A promise that resolves to the songs retrieved from the server.
     */
    getAll() {
        return axios.get('/song');
    },

    /**
     * Retrieves a song by its name.
     *
     * @param {string} songName - The name of the song to retrieve.
     * @return {Promise} - A Promise that resolves with the information of the song.
     */
    getSongByName(songName) {
        return axios.get(`/song/${songName}`);
    },

    /**
     * Creates a new song by making a POST request to '/song' endpoint.
     *
     * @param {Object} song - The data object representing the new song.
     * @return {Promise} - A Promise that resolves to the created song.
     */
    createSong(song) {
        return axios.post('/song', song);
    },

    /**
     * Update the genre of a song.
     *
     * @param songId
     * @param {Object} song - The song object to be updated.
     * @param {number} song.id - The id of the song to be updated.
     * @param {string} song.genre - The new genre for the song.
     *
     * @return {Promise} - A promise that resolves with the updated song object from the server.
     *
     * @throws {Error} - If an error occurs during the update process.
     */
    updateSong(songId, song) {
        return axios.put(`/song/${songId}`, song);
    }
}