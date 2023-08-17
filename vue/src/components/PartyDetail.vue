<script>
import axios from "axios";
import SongService from "@/services/SongService";
import RequestService from "@/services/RequestService";
import PlaylistService from "@/services/PlaylistService";
import {mapState} from "vuex";

export default {
  name: "PartyDetail",
  props: ['id'],

  data() {
    return {
      request: {
        party_id: '',
        song_id: ''
      },
      song: {
        song_name: '',
        artist: '',
        genre: '',
      },
      playlist: {
        playlist_id: '',
      },
    }
  },

  watch: {
    'song.genre'() {
      if (this.song.genre.toLowerCase().includes('country')) {
        this.song.song_name = '';
        this.song.artist = '';
        this.song.genre = '';
      }
    }
  },

  computed: {
    party() {
      return this.$store.getters.getPartyById(this.id);
    },
    ...mapState({
      userAuthorities: state => state.user.authorities
    }),
    hasRoleDJ() {
      return this.userAuthorities.some(auth => auth.name === 'ROLE_DJ');
    }
  },
  async created() {
    await this.$store.dispatch('fetchParty');
    await this.setSpotifyIDs();
    await this.setSpotifyIDsForRequests();
  },
  methods: {
    addSongToPlaylist(songId, requestId) {
      this.playlist.playlist_id = this.party.playlist.playlist_id;
      PlaylistService.addSongToPlaylist(this.playlist.playlist_id, songId)
      RequestService.deleteRequest(requestId);

      this.$store.dispatch('fetchParty');
    },
    async setSpotifyIDsForRequests() {
      await Promise.all(
          this.party.requests.map(async (request) => {
            if (request.song) {
              const query = `track:${request.song.song_name} artist:${request.song.artist}`;
              const spotifySong = await this.searchTrack(query);
              if (spotifySong && spotifySong.data.id) {
                this.$set(request.song, 'spotify_id', spotifySong.data.id);
                this.$set(request.song, 'album_art', spotifySong.data.album.images[0].url);
                await SongService.updateSong(request.song.song_id, request.song);
              }
            }
          })
      );
    },
    async setSpotifyIDs() {
      await Promise.all(
          this.party.playlist.songs.map(async (song) => {
            const query = `track:${song.song_name} artist:${song.artist}`;
            const spotifySong = await this.searchTrack(query);
            console.log("heres the spotify song ", spotifySong);
            if (spotifySong && spotifySong.data.id) {
              this.$set(song, 'spotify_id', spotifySong.data.id);
              this.$set(song, 'album_art', spotifySong.data.album.images[0].url);
              await SongService.updateSong(song.song_id, song);
            }
          })
      );
    },
    async setSpotifyId(songToEdit) {
      console.log("heres the song to edit", songToEdit);
      const query = 'track:' + songToEdit.song_name + ' artist:' + songToEdit.artist;
      console.log(query);
      const spotifySong = await this.searchTrack(query);
      console.log("heres the spotify song ", spotifySong);
      if (spotifySong && spotifySong.data.id) {
        this.$set(songToEdit, 'spotify_id', spotifySong.data.id);
        this.$set(songToEdit, 'album_art', spotifySong.data.album.images[0].url);
      }
      return songToEdit;
    },
    async searchTrack(query) {
      try {
        const response = await axios.get('http://localhost:9000/api/search', {params: {q: query}});
        console.log("Here is the response ", response);
        return response;
      } catch (error) {
        console.error(error);
        return null;
      }
    },
    async submitForm() {
      try {
        let songData = await SongService.createSong(this.song);

        let songWithId = await this.setSpotifyId(songData.data);
        console.log("Here is the song data ", songWithId);

        const updatedSong = await SongService.updateSong(songWithId.song_id, songData.data);

        console.log("Here is the updated song ", updatedSong);

        this.request.song_id = songData.data.song_id;
        this.request.party_id = this.party.id;
        await RequestService.createRequest(this.request);

        await this.$store.dispatch('fetchParty');
      } catch (error) {
        console.error(error);
      }
    },
  }
}
</script>

<template>
  <!-- Language: HTML -->
  <div v-if="party" class="party-detail-view">
    <div class="party-detail-container">

      <!-- Party Name should be in header for semantics -->
      <header class="party-detail-name">
        <h1>{{ party.party_name }} </h1>
      </header>

      <div class="party-detail-and-request">
        <!-- Playlist and People -->
        <section class="detail-view">
          <div class="playlist-users-container">

            <!-- Party Playlist -->
            <h2 class="playlist">Playlist:</h2>
            <div class="scrolling-playlist">
              <div
                  v-for="(song) in party.playlist.songs"
                  :key="song.id"
                  class="song">
                <iframe
                    :src="'https://open.spotify.com/embed/track/' + song.spotify_id"
                    width="300"
                    height="380"
                    frameborder="0"
                    allowtransparency="true"
                    allow="encrypted-media">
                </iframe>
              </div>
            </div>


            <!-- Users -->
            <div class="user-information">
              <h2 class="people-playing">Active Users:</h2>
              <div class="party-detail-users">
                <div class="scrolling-users">
                  <p
                      v-for="user in party.users"
                      :key="user.id"
                      class="username">{{ user.username }}
                  </p>
                </div>
              </div>
            </div>

          </div>
        </section>

        <!-- Requests -->
        <section class="request-view">
          <div class="request-container">
            <h2 class="playlist">Request:</h2>

            <!-- Song Request Form -->
            <div class="request-form">
              <form @submit.prevent="submitForm">
                <label for="song_name"> Song Name</label>
                <input class="input" type="text" id="song_name" v-model="song.song_name" required>

                <label for="artist"> Artist</label>
                <input class="input" type="text" id="artist" v-model="song.artist" required>

                <label for="user_genre"> Genre</label>
                <input class="input" type="text" id="user_genre" v-model="song.genre" required>

                <input class="submit-button" type="submit" value="Submit">
              </form>
            </div>

            <div class="scrolling-request">
              <!-- Song Requests -->
              <div
                  v-for="(request) in party.requests"
                  :key="request.id"
                  class="song">
                <div class="iframe-container">
                  <iframe

                      :src="'https://open.spotify.com/embed/track/' + request.song.spotify_id"
                      width="300"
                      height="380"
                      frameborder="0"
                      allowtransparency="true"
                      allow="encrypted-media">
                  </iframe>
                </div>
                <div class="button-container">
                  <button class="move" v-if="hasRoleDJ" @click.prevent="addSongToPlaylist(request.song_id, request.id)">Move</button>
                </div>
              </div>
            </div>

          </div>
        </section>
      </div>

    </div>
  </div>
</template>

<style scoped>

@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,400;0,500;1,900&display=swap');

h1 {
  font-family: 'Poppins', sans-serif;
  font-weight: 900;
  font-size: 2.5em;
  text-shadow: -2px 3px 4px rgba(0, 0, 0, 0.2);
}

.playlist {
  font-family: 'Poppins', sans-serif;
  font-weight: 700;
  font-size: 2em;
  text-shadow: -2px 3px 4px rgba(0, 0, 0, 0.2);
  margin-top: 1em;
  margin-bottom: 1em;
}

.party-detail-view {
  display: flex;
}

.party-detail-container {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  height: 100vh;
  width: 100%;
}

.party-detail-and-request {
  display: flex;
  justify-content: space-around;
}

.party-detail-name {
  display: flex;
  justify-content: center;
}

.detail-view {
  display: flex;
  height: 55em;
  width: 45%;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);

  backdrop-filter: blur(10px);
}

.playlist-users-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  width: 100%;
}

.scrolling-playlist {
  display: flex;
  flex-wrap: wrap;
  overflow-y: scroll;
  justify-content: space-around;
  height: 75%;
}

.user-information {
  display: flex;
  flex-direction: column;
}

.scrolling-users {
  display: flex;
  flex-wrap: wrap;
  overflow-y: scroll;
  justify-content: space-around;
  padding: 1em;

  background-color: rgba(255, 255, 255, 0.2);

  box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.6);

  border-radius: 8px;
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.request-view {
  display: flex;
  height: 55em;
  width: 45%;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);

  backdrop-filter: blur(10px);
}

.request-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
}

.scrolling-request {
  display: flex;
  flex-wrap: wrap;
  overflow-y: scroll;
  justify-content: space-around;
  height: 75%;
}

.song {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 20em;
}

.request-songs {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 20em;
}

.song-data-display {
  margin-top: 10em;
  height: 300px;
  width: 300px;
}

.username {
  display: flex;
  justify-content: center;
  margin: 0.5em;
  width: 10em;
  padding: 0.2em;
  font-family: 'Poppins', sans-serif;
  font-weight: 300;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);
}

.people-playing {
  font-family: 'Poppins', sans-serif;
  font-weight: 700;
  font-size: 2em;
  text-shadow: -2px 3px 4px rgba(0, 0, 0, 0.2);
}

.iframe-container {
  width: 100%;
}

.button-container {
  display: flex;
  justify-content: center;
  width: 100%;
}

.song {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: auto;
}

button {
  width: 10em;
  height: 2em;
  color: white;
  margin: 2em;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);

  backdrop-filter: blur(10px);
  transition: transform 0.3s ease-in-out;
}

button:hover {
  background: rgba(255, 255, 255, 0.4);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.4);
  transform: scale(1.2);
}

.request-form {
  margin-bottom: 2em;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 50%;
  width: 100%;
}

form {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-around;
  height: 100%;
  width: 70%;
  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);

  backdrop-filter: blur(10px);

}

label {
  font-family: 'Poppins', sans-serif;
  font-weight: 700;
  font-size: 1.5em;
  text-shadow: -2px 3px 4px rgba(0, 0, 0, 0.2);
}

.input {
  margin-bottom: 1em;
  padding: 0.2em;
  height: 2em;
  border-radius: 8px;
  border: 1px solid rgba(255, 255, 255, 0.2);
  background-color: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);
}

.submit-button {
  width: 10em;
  height: 2em;
  color: white;
  margin: 2em;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);

  backdrop-filter: blur(10px);
  transition: transform 0.3s ease-in-out;
}

.submit-button:hover {
  background: rgba(255, 255, 255, 0.4);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.4);
  transform: scale(1.2);
}

</style>