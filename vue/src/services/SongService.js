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
     * Creates a new song.
     *
     * @param {Object} song - The song object to be created.
     * @param {string} song.title - The title of the song.
     * @param {string} song.artist - The artist of the song.
     * @param {string} song.album - The album of the song.
     * @param {number} song.year - The year when the song was released.
     * @param {string} song.genre - The genre of the song.
     *
     * @returns {Promise} - The Promise object representing the created song.
     */
    createSong(song) {
        return axios.post('/song', song);
    },

    /**
     * Update the genre of a song.
     *
     * @param {Object} song - The song object to be updated.
     * @param {number} song.id - The id of the song to be updated.
     * @param {string} song.genre - The new genre for the song.
     *
     * @return {Promise} - A promise that resolves with the updated song object from the server.
     *
     * @throws {Error} - If an error occurs during the update process.
     */
    updateGenre(song) {
        return axios.put(`/song/${song.id}`, song);
    }
}