import axios from 'axios';

export default {

    /**
     * Retrieves all playlists from the server.
     *
     * @return {Promise} - A promise that resolves to the response object of the GET request.
     */
    getAll() {
        return axios.get('/playlist');
    },

    /**
     * Retrieves a playlist by its name.
     *
     * @param {string} playlistName - The name of the playlist to retrieve.
     * @return {Promise} - A promise that resolves to the playlist object once it is retrieved.
     */
    getPlaylistByName(playlistName) {
        return axios.get(`/playlist/${playlistName}`);
    },

    /**
     * Retrieves the songs in a playlist from the server.
     *
     * @param {string} playlistId - The ID of the playlist.
     *
     * @return {Promise} A Promise object representing the songs in the playlist.
     */
    getSongsInPlaylist(playlistId) {
        return axios.get(`/playlist/${playlistId}/songs`);
    },

    /**
     * Creates a new playlist.
     *
     * @param {Object} playlist - The playlist object to be created.
     * @returns {Promise} - A promise that resolves with the created playlist.
     *
     * @example
     * createPlaylist({
     *   name: 'My Playlist',
     *   description: 'This is my awesome playlist',
     *   songs: ['song1', 'song2', 'song3']
     * })
     *   .then((createdPlaylist) => {
     *     console.log('Playlist created:', createdPlaylist);
     *   })
     *   .catch((error) => {
     *     console.error('Failed to create playlist:', error);
     *   });
     */
    createPlaylist(playlist) {
        return axios.post('/playlist', playlist);
    },

    /**
     * Updates a playlist.
     *
     * @param {Object} playlist - The playlist object to be updated.
     *
     * @return {Promise} - A promise that will be resolved once the playlist is updated successfully.
     */
    updatePlaylist(playlist) {
        return axios.put(`/playlist/${playlist.id}`, playlist);
    },

    addSongToPlaylist(playlistId, songId) {
        return axios.post(`/playlist/${playlistId}/songs/${songId}`);
    }
}