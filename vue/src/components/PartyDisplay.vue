<script>
import {mapState} from "vuex";
import PartyService from "@/services/PartyService";

export default {
  name: "PartyDisplay",
  props: ['party'],

  data() {
    return {
      emojies: ['🎸', '🥁', '❤️', '🎆', '🎉', '🎊', '🎈', '🎵', '❄️', '⚡️', '🔥', '🥳', '🦾', '👀', '🚀', '🌄', '🌠', '🌌'],
      selectedEmoji: '',
      defaultDropDownMessage: 'Select an emoji'
    }
  },

  created() {
  },

  methods: {
    setEmoji(party, selectedEmoji) {
      party.emoji = selectedEmoji;
      PartyService.updateParty(party);
    }
  },

  computed: {
    ...mapState({
      userAuthorities: state => state.user.authorities
    }),
    hasRoleDJ() {
      return this.userAuthorities.some(auth => auth.name === 'ROLE_DJ');
    }
  }
}
</script>

<template>
  <div class="display-container">
    <div class="name-container">
      <router-link class="router-link" style="text-decoration: none; color: white" :to="{ name: 'party-details', params: { id: party.id } }">
        <h1>{{ party.party_name }}</h1>
      </router-link>
      <div class="selected-emoji">{{ party.emoji ? party.emoji : selectedEmoji }}</div>
    </div>
    <select v-if="hasRoleDJ" v-model="selectedEmoji" @change="setEmoji(party, selectedEmoji)">
      <option disabled value="">Please select an emoji</option>
      <option v-for="(emoji, index) in this.emojies" :key="index">{{ emoji }}</option>
    </select>
    <div class="playlist-container">
      <p class="playlist">Playlist:</p>
      <div class="scrolling-playlist">
        <div class="song" v-for="song in party.playlist.songs" :key="song.id">
          <div class="song-data">
            <div class="song-name song-data-display">{{ song.song_name }}</div>
            <div class="song-artist song-data-display">{{ song.artist }}</div>
            <div class="song-genre song-data-display">{{ song.user_genre }}</div>
          </div>
          <div class="album-art">
            <img :src="song.album_art" alt="album art">
          </div>
        </div>
      </div>
    </div>
    <div class="user-container">
      <p class="users-title">People playing:</p>
      <div class="scrolling-users">
        <p class="username" v-for="user in party.users" :key="user.id">{{ user.username }}</p>
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
}

.users-title {
  font-family: 'Poppins', sans-serif;
  font-weight: 700;
  font-size: 2em;
  text-shadow: -2px 3px 4px rgba(0, 0, 0, 0.2);
}

.router-link {
  transition: transform 0.3s ease-in-out;
}

.router-link:hover {
  transform: scale(1.1);
}

router-link {
  text-decoration: none;
  color: white;
}

select {
  margin-left: 1em;
  margin-bottom: 3em;
  padding: 0.2em;
  height: 2em;
  font-family: 'Poppins', sans-serif;
  color: white;
  border-radius: 8px;
  border: 1px solid rgba(255, 255, 255, 0.2);
  background-color: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);
}

select:hover {
  background-color: rgba(255, 255, 255, 0.4);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.4);
}

option {
  font-family: 'Poppins', sans-serif;
}

.name-container {
  display: flex;
  justify-content: center;
  width: 100%;
  margin-bottom: 1em;
}

.selected-emoji {
  font-size: 3.5em;
  margin-left: .5em;
  margin-top: -0.3em;
}

.display-container {
  display: flex;
  justify-content: center;
  align-items: center;
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
  width: 10em;
  padding: 0.2em;
  font-family: 'Poppins', sans-serif;
  font-weight: 300;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);
}

.playlist-container {
  width: 100%;
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
  justify-content: space-around;
  align-items: center;
  width: 100%;
  height: 20em;
  padding: 0.2em;
  margin: 0.5em;

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
  height: 60%;
  width: 35%;
  padding: 0.2em;
  text-shadow: -2px 3px 4px rgba(0, 0, 0, 0.2);
}

.song-data-display {
  font-family: 'Poppins', sans-serif;
  font-size: 1.5em;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0.3em;
  width: 20em;
}

.album-art {
  height: 90%;
  width: 40%;
  box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.6);
  border-radius: 0.5em;
  overflow: hidden;
}

img {
  height: 100%;
  width: 100%;
  object-fit: cover;
  object-position: center center;
}
</style>