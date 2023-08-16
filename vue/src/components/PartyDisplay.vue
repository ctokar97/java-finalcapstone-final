<script>
export default {
  name: "PartyDisplay",
  props: ['party'],

  data() {
    return {
      emojies: ['🎸', '🥁', '❤️', '🎆', '🎉', '🎊', '🎈', '🎵', '❄️', '⚡️', '🔥', '🥳', '🦾', '👀', '🚀', '🌄', '🌠', '🌌'],
      selectedEmoji: ''
    }
  },

  created() {
  },

  methods: {
  }
}
</script>

<template>
  <div class="display-container">
    <div class="name-container">
      <router-link :to="{ name: 'party-details', params: { id: party.id } }">
        <h1>{{ party.party_name }}</h1>
      </router-link>
      <div>{{ selectedEmoji }}</div>
      <select v-model="selectedEmoji">
        <option v-for="(emoji, index) in this.emojies" :key="index">{{ emoji }}</option>
      </select>
    </div>
    <div class="user-container">
      <p>People playing:</p>
      <div class="scrolling-users">
        <p class="username" v-for="user in party.users" :key="user.id">{{ user.username }}</p>
      </div>
      <div class="playlist-container">
        <p>Playlist:</p>
        <div class="scrolling-playlist">
          <div class="song" v-for="song in party.playlist.songs" :key="song.id">
            <div class="song-data">
              <div class="song-name song-data-display">{{ song.song_name }}</div>
              <div class="song-artist song-data-display">{{ song.artist }}</div>
              <div class="song-genre song-data-display">{{ song.user_genre }} </div>
            </div>
            <div class="album-art"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.display-container {
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid red;
  width: 100%;
  overflow: scroll;
}

.user-container {
  margin-top: 1em;
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

.scrolling-playlist {
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: center;
  align-content: space-around;
  padding: 1em;
  overflow: scroll;
  background-color: rgba(255, 255, 255, 0.2);

  box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.6);

  border-radius: 8px;
  border: 1px solid rgba(255, 255, 255, 0.2);

}

.song {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin: 0.5em;
  width: 100%;
  height: 7em;
  padding: 0.2em;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);
}

.song-data {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
  width: 25%;
  padding: 0.2em;
}

.song-data-display {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0.3em;
}

.album-art {
  height: 80%;
  width: 70%;
  background-color: rgba(255, 255, 255, 1);
  box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.6);
  border-radius: 0.5em;
}
</style>