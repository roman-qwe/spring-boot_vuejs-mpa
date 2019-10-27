<template>
  <div class="t" :class="{'t-hide' : tHide}">
    <middle-menu />
    <b-button class="t-button-toggle" v-on:click="tHide=!tHide">
      <font-awesome-icon :icon="tButtonIcon" />
    </b-button>
  </div>
</template>

<script>
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { faAngleDoubleLeft } from "@fortawesome/free-solid-svg-icons";
import { faAngleDoubleRight } from "@fortawesome/free-solid-svg-icons";

import MiddleMenu from "./MiddleMenu";

export default {
  name: "Sidebar",
  data() {
    return {
      tHide: true
    };
  },
  components: {
    FontAwesomeIcon,
    MiddleMenu
  },
  computed: {
    tButtonIcon: function() {
      return this.tHide ? faAngleDoubleRight : faAngleDoubleLeft;
    }
  }
};
</script>

<style lang="scss" scoped>
$t-width: 15rem;

.t {
  min-height: 100vh;
  max-width: $t-width;
  margin-left: 0rem;
  -webkit-transition: margin 0.25s ease-out;
  -moz-transition: margin 0.25s ease-out;
  -o-transition: margin 0.25s ease-out;
  transition: margin 0.25s ease-out;
}
.t-hide {
  margin-left: -$t-width;
}
.t-button-toggle {
  position: fixed;
  margin-left: $t-width + 1;
}
</style>
