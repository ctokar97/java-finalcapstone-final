<script>
import UserService from "@/services/UserService";
import PartyService from "@/services/PartyService";
import PlaylistService from "@/services/PlaylistService";

export default {
  name: "PartyCreator",
  data() {
    return {
      party: {
        party_name: "",
        party_owner: "",
        theme: "",
        playlist: {
          playlist_name: "",
        }
      }
    };
  },
  methods: {
    async submitForm() {
      const user = await UserService.getUserByUsername(this.party.party_owner);
      console.log("User: ", user);
      if (user) {
        this.party.party_owner = user.data.id;

      } else {
        alert("User not found!");
      }
      try {
        const response = await PartyService.createParty(this.party);
        console.log("Response: ", response);

        const playlistResponse = await PlaylistService.createPlaylist(
            {
              ...this.party.playlist,
              partyId: response.data.id
            });
        console.log("Playlist Response: ", playlistResponse)

        const userResponse = await PartyService.assignPartyToUser(response.data.id, user.data.id)
        console.log("User Response: ", userResponse);

        const roleResponse = await UserService.changeUserRole(user.data.id, "HOST")
        console.log("Role Response: ", roleResponse);
        await this.$store.dispatch("fetchParty");
        this.$emit("partyCreated", response.data);
        this.$emit("close");
      } catch (e) {
        alert("Error creating party!");
      }
    },
    closeForm() {
      this.$emit("close");
    }
  }
}
</script>

<template>
  <div>
    <div class="party-creator">
      <form class="form-submission" @submit.prevent="submitForm">
        <label for="party_name"> Party Name </label>
        <input type="text" id="party_name" v-model="party.party_name" required>
        <label for="party_owner"> Party Host </label>
        <input type="text" id="party_owner" v-model="party.party_owner" required>
        <label for="theme"> Party Theme </label>
        <input type="text" id="theme" v-model="party.theme" required>
        <label for="playlist"> Playlist Name</label>
        <input type="text" id="playlist" v-model="party.playlist.playlist_name" required>
        <button type="submit">Create Party</button>
        <button class="close-form" @click="closeForm">Close Party Creator</button>
      </form>
    </div>
  </div>
</template>

<style scoped>

@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,400;0,500;1,900&display=swap');

.party-creator {
  display: flex;
  flex-direction: column;
  position: fixed;
  width: 100%;
  height: 100%;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.58);
}

.form-submission {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;
  background-color: rgba(255, 255, 255, 0.2);
  width: 50%;
  height: 50%;
  padding: 2em;
  border-radius: 8px;
  border: 2px solid rgba(255, 255, 255, 0.2);
  font-family: 'Poppins', sans-serif;
  text-shadow: -3px 4px 3px rgba(0, 0, 0, 0.2);
  font-size: 1.2em;
}

button {
  width: 10em;
  height: 2em;
  color: white;
  font-family: 'Poppins', sans-serif;

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

.close-form {
  height: 4em;
}

label {
  margin-bottom: -1em;
}

</style>