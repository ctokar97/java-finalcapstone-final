<script>
import axios from "axios";

export default {
  name: "PartyDetail",
  props: ['id'],

  computed: {
    party() {
      return this.$store.getters.getPartyById(this.id);
    }
  },
  created() {
    this.$store.dispatch('fetchParty');
  },
  methods: {
    async setSpotifyIDs() {
      for (const song of this.party.playlist.songs) {
        console.log('Song:', song);  // Add this line
        const query = 'track:' + song.song_name + ' artist:' + song.artist;
        console.log('Query:', query);  // Add this line
        const spotifySong = await this.searchTrack(query);
        console.log('Spotify data:', spotifySong);  // Add this line
        if (spotifySong && spotifySong.id) {   // Accessing id directly now
          this.$set(song, 'spotifyId', spotifySong.id);
        }
      }
    },
    async searchTrack(query) {
      try {
        console.log('Query:', query);  // Add this line
        const response = await axios.get('http://localhost:9000/api/search', {params: {q: query}});
        console.log('Response:', response); // Add this line
        return response.data;
      } catch (error) {
        console.error(error);
        return null;
      }
    },
  }
}
</script>

<template>
  <div>
    <div class="party-detail-view" v-if="party">
      <div class="party-detail-container">
        <div class="party-detail-name">
          <h1>{{ party.party_name }}</h1>
        </div>
        <div class="detail-view">
          <div class="details">
            <div class="iframe">
              <div class="party-detail-playlist">
                <p class="playlist">Playlist:</p>
                <div class="scrolling-playlist">
                  <!-- Some codes are omitted for brevity -->
                  <div class="song" v-for="(song) in party.playlist.songs" :key="song.id">
                    <div class="album-art"></div>
                    <div class="song-data">
                      <iframe class="song-data-display" :src="'https://open.spotify.com/embed/track/' + song.spotifyId"
                              frameborder="0"
                              allowtransparency="true"
                              allow="encrypted-media">
                      </iframe>
                    </div>
                  </div>
                </div>
              </div>
              <p class="people-playing">People playing:</p>
              <div class="party-detail-users">
                <div class="scrolling-users">
                  <p class="username" v-for="user in party.users" :key="user.id">{{ user.username }}</p>
                </div>
              </div>
            </div>
            <div class="party-detail-users">
              <p>People playing:</p>
              <div class="scrolling-users">
                <p class="username" v-for="user in party.users" :key="user.id">{{ user.username }}</p>
              </div>
            </div>
            <div class="non-iframe">
              <div class="party-detail-playlist">
                <p>Playlist:</p>
                <div class="scrolling-playlist">
                  <div class="song" v-for="song in party.playlist.songs" :key="song.id">
                    <div class="song-data">
                      <div class="song-name song-data-display">{{ song.song_name }}</div>
                      <div class="song-artist song-data-display">{{ song.artist }}</div>
                      <div class="song-genre song-data-display">{{ song.user_genre }}</div>
                    </div>
                    <div class="album-art"></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="request-view">

          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

.party-detail-name {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 1em;
}

.party-detail-view {
  display: flex;
  flex-direction: column;
}

.party-detail-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.detail-view {
  display: flex;
  justify-content: space-around;;
}

.details {
  display: flex;
  flex-direction: column;
  width: 30%;
  height: 50em;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);

  backdrop-filter: blur(10px);

  overflow: scroll;
}

.request-view {
  display: flex;
  flex-direction: column;
  width: 30%;
  height: 50em;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);

  backdrop-filter: blur(10px);

  overflow: scroll;
}


</style>