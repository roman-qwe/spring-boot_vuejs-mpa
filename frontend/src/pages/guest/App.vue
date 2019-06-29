<template>
  <div id="app">
    <div>
      <local-navbar />
      <common-body />
    </div>
  </div>
</template>


<script>
import LocalNavbar from "./components/navbar/Navbar";
import CommonBody from "@/components/ui/body/Body";

export default {
  name: "app",
  components: {
    LocalNavbar,
    CommonBody
  }
};
</script>