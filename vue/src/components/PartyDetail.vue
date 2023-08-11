<script>
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
            <div class="party-detail-users">
              <p>People playing:</p>
              <div class="scrolling-users">
                <p class="username" v-for="user in party.users" :key="user.id">{{ user.username }}</p>
              </div>
            </div>
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