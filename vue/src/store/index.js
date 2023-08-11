import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import partyService from "@/services/PartyService";
import playlistService from "@/services/PlaylistService";
import RequestService from "@/services/RequestService";

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    token: currentToken || '',
    user: currentUser || {},
    parties: []
  },
  mutations: {
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    },
    SET_PARTIES(state, parties) {
        state.parties = parties;
    }
  },
  actions: {
    async fetchParty({commit}) {
      let response = await partyService.getAll();
      const parties = response.data;

      await Promise.all(parties.map(async (party) => {
        let response = await partyService.getUsersInParty(party.id);
        party.users = response.data;
      }));

      await Promise.all(parties.map(async (party) => {
        let response = await partyService.getPlaylistInParty(party.id);
        const playlist = response.data;

        if (playlist && playlist.playlist_id) {
          party.playlist = playlist;
          response = await playlistService.getSongsInPlaylist(playlist.playlist_id);
          party.playlist.songs = response.data;
        } else {
          party.playlist = { songs: [] };
        }
      }));

      await Promise.all(parties.map(async (party) => {
        let response = await RequestService.getAllRequests();
        let requests = response.data;
        requests = requests.filter(request => request.party_id === party.id);
        party.requests = requests;
      }));

      commit('SET_PARTIES', parties);
    }
  },
  getters: {
    getParties: state => {
        return state.parties;
    },
    getPartyById: (state) => (id) => {
      return state.parties.find(party => party.id === Number(id));
    }
  }
})
