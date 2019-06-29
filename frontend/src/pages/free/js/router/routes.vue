<script>
export default [
  {
    path: "/",
    component: () => import(`_free/sub-page/Home.vue`)
  },
  {
    path: "/about",
    component: () => import(`_free/sub-page/About.vue`)
  },
  {
    path: "/login",
    component: () => import(`_free/sub-page/Login.vue`)
  }
];
</script>