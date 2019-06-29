<template>
  <b-navbar toggleable="lg" type="dark" variant="dark">
    <slot name="root"></slot>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

    <b-collapse id="nav-collapse" is-nav>
      <slot name="left"></slot>
      <slot name="right"></slot>
    </b-collapse>
  </b-navbar>
</template>

<script>
export default {
  name: "Navbar"
};
</script>
