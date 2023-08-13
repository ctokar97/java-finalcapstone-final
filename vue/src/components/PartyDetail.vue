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
        song_id:''
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
                    <iframe class="song-data-display" :src="'https://open.spotify.com/embed/track/' + song.spotify_id"
                            frameborder="0"

                            allowtransparency="true"
                            allow="encrypted-media">
                    </iframe>
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
            <!--            <div class="non-iframe">-->
            <!--              <div class="party-detail-users">-->
            <!--                <p>People playing:</p>-->
            <!--                <div class="scrolling-users">-->
            <!--                  <p class="username" v-for="user in party.users" :key="user.id">{{ user.username }}</p>-->
            <!--                </div>-->
            <!--              </div>-->
            <!--              <div class="party-detail-playlist">-->
            <!--                <p>Playlist:</p>-->
            <!--                <div class="scrolling-playlist">-->
            <!--                  <div class="song" v-for="song in party.playlist.songs" :key="song.id">-->
            <!--                    <div class="song-data">-->
            <!--                      <div class="song-name song-data-display">{{ song.song_name }}</div>-->
            <!--                      <div class="song-artist song-data-display">{{ song.artist }}</div>-->
            <!--                      <div class="song-genre song-data-display">{{ song.user_genre }}</div>-->
            <!--                    </div>-->
            <!--                    <div class="album-art"></div>-->
            <!--                  </div>-->
            <!--                </div>-->
            <!--              </div>-->
            <!--            </div>-->
          </div>
          <div class="request-view">
            <div class="request-container">
              <div class="request-form">
                <div class="song" v-for="(request) in party.requests" :key="request.id">
                  <iframe class="song-data-display"
                          :src="'https://open.spotify.com/embed/track/' + request.song.spotify_id"
                          frameborder="0"
                          allowtransparency="true"
                          allow="encrypted-media">
                  </iframe>
                </div>
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
  margin-bottom: -5em;
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
  justify-content: center;
  align-items: center;
  margin: 0.5em 0.5em 1em;
  width: 45%;
  height: 20em;
  padding: 1em;
  overflow: hidden;
  position: relative;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);
}

.song-data {
  display: flex;
  align-items: center;
  justify-content: center;
}

.song-data-display {
  position: absolute;
  top: 2.7em;
  width: 80%;
  height: 90%;
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
  width: 8em;
  padding: 0.2em;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);
}

</style>