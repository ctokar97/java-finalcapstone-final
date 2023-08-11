<script>
import PartyDetail from "@/components/PartyDetail.vue";

export default {
  name: "PartyDetails",
  components: {PartyDetail},
}
</script>

<template>
  <div>
    <party-detail :id="$route.params.id" />
  </div>
</template>

<style scoped>

</style>