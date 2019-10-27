<template>
  <div>
    <h1>Login</h1>
    <!-- <form action="/login" method="post"> -->
    <div>
      <label>
        Username:
        <input type="text" v-model="username" />
      </label>
    </div>
    <div>
      <label>
        Password:
        <input type="password" v-model="password" />
      </label>
    </div>
    <div>
      <input v-on:click="login" type="button" value="Sign In" />
    </div>
    <!-- </form> -->
  </div>
</template>

<script>
import { AXIOS } from "@/assets/js/Axios";

export default {
  name: "Login",
  data() {
    return {
      username: null,
      password: null
    };
  },
  created: function() {
    console.log("Значение username: " + this.username);
  },
  methods: {
    login: function() {
      console.log("login start");
      let v = this;
      AXIOS.post("/guest/login", {
        username: v.username,
        password: v.password
      })
        .then(function(response) {
          console.log("success response");
          console.log(response);
          console.log("---");
          console.log(response.data.token);
          // document.cookie = "Authorization=" + response.data.token;
        })
        .catch(function(error) {
          console.log("error response");
          console.log(error);
        });
      console.log("login finish");
    }
  }
};
</script>