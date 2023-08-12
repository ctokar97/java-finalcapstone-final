<script>
import axios from "axios";

export default {
  name: "PartyDetail",
  props: ['id'],
  data() {
    return {
      party: {},
      spotifyIDs: [], // use array instead
    }
  },

  async created() {
    await this.$store.dispatch('fetchParty');
    this.party = this.$store.getters.getPartyById(this.id);
    console.log('Party:', this.party);  // Add this line
    if (this.party && this.party.playlist) {
      await this.setSpotifyIDs();
    }
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
  },
}

</script>

<template>
  <div>
    <div class="party-detail-view" v-if="party && party.playlist && party.playlist.songs">
      <div class="party-detail-container">
        <div class="party-detail-name">
          <h1>{{ party.party_name }}</h1>
        </div>
        <div class="detail-view">
          <div class="details">
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
          <div class="request-view">
            <div class="party-detail-requests">
              <p>Requests:</p>
              <div class="scrolling-playlist">
                <div class="song" v-for="song in party.requests" :key="song.id">
                  <div class="album-art"></div>
                  <div class="song-data">
                    <div class="song-name song-data-display">{{ song.song_name }}</div>
                    <div class="song-artist song-data-display">{{ song.artist }}</div>
                    <div class="song-genre song-data-display">{{ song.user_genre }}</div>
                  </div>
                </div>
              </div>
            </div>
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

.party-detail-users {
  padding: 0 1em 1em;
}

.detail-view {
  display: flex;
  justify-content: space-around;;
}

.playlist {
  font-size: 2em;
  margin-left: .2em;
}

.people-playing {
  font-size: 2em;
  margin-left: .2em;
}

.scrolling-playlist {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;
  align-items: center;

}

.details {
  display: flex;
  flex-direction: column;
  width: 40%;
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
  width: 40%;
  height: 50em;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);

  backdrop-filter: blur(10px);

  overflow: scroll;
}

.song {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  margin: 0.5em 0.5em 1em;
  width: 45%;
  height: 20em;
  padding: 1em;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);
}

.song-data {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.album-art {
  height: 70%;
  width: 70%;
  background-color: rgba(255, 255, 255, 1);
  box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.6);
  border-radius: 0.5em;
}

.scrolling-users {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  padding: 1em;

  background-color: rgba(255, 255, 255, 0.2);

  box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.6);

  border-radius: 8px;
  border: 1px solid rgba(255, 255, 255, 0.2);

}

.username {
  display: flex;
  justify-content: center;
  margin: 0.5em;
  width: 7em;
  padding: 0.2em;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);
}

</style>