<script>
import axios from "axios";
import SongService from "@/services/SongService";
import RequestService from "@/services/RequestService";

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
      }
    }
  },

  computed: {
    party() {
      return this.$store.getters.getPartyById(this.id);
    }
  },
  async created() {
    await this.$store.dispatch('fetchParty');
    await this.setSpotifyIDs();
    await this.setSpotifyIDsForRequests();

  },
  methods: {
    async setSpotifyIDsForRequests() {
      await Promise.all(
          this.party.requests.map(async (request) => {
            if (request.song) {
              const query = `track:${request.song.song_name} artist:${request.song.artist}`;
              const spotifySong = await this.searchTrack(query);
              if (spotifySong && spotifySong.data.id) {
                this.$set(request.song, 'spotify_id', spotifySong.data.id);
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
            if (spotifySong && spotifySong.data.id) {
              this.$set(song, 'spotify_id', spotifySong.data.id);
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
    }
  }
}
</script>

<template>
  <!-- Language: HTML -->
  <div v-if="party" class="party-detail-view">
    <div class="party-detail-container">

      <!-- Party Name should be in header for semantics -->
      <header class="party-detail-name">
        <h1>{{ party.party_name }}</h1>
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
                    class="song-data-display"
                    :src="'https://open.spotify.com/embed/track/' + song.spotify_id"
                    frameborder="0"
                    allowtransparency="true"
                    allow="encrypted-media">
                </iframe>
              </div>
            </div>


            <!-- Users -->
            <div class="user-information">
              <h2 class="people-playing">People playing:</h2>
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
            <h2 class="playlist">Playlist:</h2>

            <div class="scrolling-request">
              <!-- Song Requests -->
              <div
                  v-for="(request) in party.requests"
                  :key="request.id"
                  class="song">
                <iframe
                    class="song-data-display"
                    :src="'https://open.spotify.com/embed/track/' + request.song.spotify_id"
                    frameborder="0"
                    allowtransparency="true"
                    allow="encrypted-media">
                </iframe>
              </div>
            </div>

            <!-- Song Request Form -->
            <div class="request-form">
              <form @submit.prevent="submitForm">
                <label for="song_name"> Song Name</label>
                <input type="text" id="song_name" v-model="song.song_name" required>

                <label for="artist"> Artist</label>
                <input type="text" id="artist" v-model="song.artist" required>

                <label for="user_genre"> Genre</label>
                <input type="text" id="user_genre" v-model="song.genre" required>

                <input type="submit" value="Submit">
              </form>
            </div>

          </div>
        </section>
      </div>

    </div>
  </div>
</template>

<style scoped>

* {
  border: 1px solid red;
}

.party-detail-view {
  display: flex;
}

.party-detail-container {
  display: flex;
  flex-direction: column;
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
  width: 40%
}

.playlist-users-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
}

.scrolling-playlist {
  display: flex;
  flex-wrap: wrap;
  overflow-y: scroll;
  justify-content: space-around;
}

.user-information {
  display: flex;
  flex-direction: column;
}

.scrolling-users {
  display: flex;
  flex-wrap: wrap;
  overflow-y: scroll;
  justify-content: center;
}

.request-view {
  display: flex;
  width: 40%
}

.request-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
}

.scrolling-request {
  display: flex;
  flex-wrap: wrap;
  overflow-y: scroll;
  justify-content: space-around;
}

.song {
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

</style>