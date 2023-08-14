<script>
import UserService from "@/services/UserService";
import PartyService from "@/services/PartyService";

export default {
  name: "PartyCreator",
  data() {
    return {
      party: {
        party_name: "",
        party_owner: "",
        theme: ""
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
    <form class="form-submission" @submit.prevent="submitForm">
      <label for="party_name"> Party Name </label>
      <input type="text" id="party_name" v-model="party.party_name" required>
      <label for="party_owner"> Party Host </label>
      <input type="text" id="party_owner" v-model="party.party_owner" required>
      <label for="theme"> Party Theme </label>
      <input type="text" id="theme" v-model="party.theme" required>
      <button type="submit">Create Party</button>
    </form>
    <button class="close-form" @click="closeForm">Close Party Creator</button>
  </div>
</template>

<style scoped>


</style>