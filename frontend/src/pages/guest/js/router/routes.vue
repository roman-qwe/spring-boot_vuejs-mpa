<script>
export default [
  {
    path: "/",
    component: () => import(`_guest/sub-page/Home.vue`)
  },
  {
    path: "/about",
    component: () => import(`_guest/sub-page/About.vue`)
  },
  {
    path: "/login",
    component: () => import(`_guest/sub-page/Login.vue`)
  }
];
</script>