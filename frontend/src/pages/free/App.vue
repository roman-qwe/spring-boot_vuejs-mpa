<template>
  <div id="app">
    <navbar/>
    <b-container fluid>
      <router-view></router-view>
    </b-container>
  </div>
</template>


<script>
import Navbar from "./components/navbar/Navbar.vue";

export default {
  name: "app",
  components: {
    Navbar
  }
};
</script>