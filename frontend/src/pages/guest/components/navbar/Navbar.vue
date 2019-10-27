<template>
  <common-navbar>
    <template v-slot:root>
      <root></root>
    </template>
    <template v-slot:left>
      <left></left>
    </template>
    <template v-slot:right>
      <right/>
    </template>
  </common-navbar>
</template>

<script>
import Root from "./Root.vue";
import Left from "./Left.vue";
import Right from "./Right.vue";
import CommonNavbar from "@/components/ui/Navbar.vue";

export default {
  name: "NavBar",
  components: {
    CommonNavbar,
    Root,
    Left,
    Right
  }
};
</script>