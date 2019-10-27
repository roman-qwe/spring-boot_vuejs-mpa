<template>
  <div>
    <sidebar />
    <b-container fluid>
      <middle />
    </b-container>
    <asidebar />
  </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Middle from "./Middle";
import Asidebar from "./Asidebar";

export default {
  name: "Body",
  components: {
    Sidebar,
    Middle,
    Asidebar
  }
};
</script>

<style>
</style>
