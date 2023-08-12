<template>
  <div>
    <h1 v-for="(artist, index) in userTopArtists" :key="index">{{ artist.name }}</h1>
    <h1 v-if="loading">Loading...</h1>
    <iframe class="song" :src="'https://open.spotify.com/embed/track/' + this.id"
            frameborder="0"
            allowtransparency="true"
            allow="encrypted-media">
    </iframe>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      userTopArtists: [],
      loading: false,
      query: 'track:Take Forever artist:Colony Collapse',
      id: ''
    }
  },
  created() {
    this.fetchTopArtists();
    this.searchTrack()
  },
  methods: {
    fetchTopArtists() {
      this.loading = true;
      fetch("http://localhost:9000/api/user-top-artists")
          .then(response => response.json())
          .then(data => {
            this.userTopArtists = data;
            this.loading = false;
          });
    },
    async searchTrack() {
      try {
        const response = await axios.get('http://localhost:9000/api/search', {params: {q: this.query }});
        console.log(response);
        this.id = response.data.id;
        console.log(this.uri);
      } catch (error) {
        console.log(error);
      }
    }
  }
};
</script>

<style scoped>
.song {
  width: 20em;
  height: 10em;

  background: rgba(255, 255, 255, 0.2);
  box-shadow: 2px 2px 15px 4px rgba(0, 0, 0, 0.2);

  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);

  backdrop-filter: blur(10px);
}
</style>
```