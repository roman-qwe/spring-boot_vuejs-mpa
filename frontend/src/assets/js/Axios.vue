<script>
import axios from "axios";

const CSRF_TOKEN = document.cookie.match(new RegExp(/XSRF-TOKEN=([^;]+)/i))[1];

export const AXIOS = axios.create({
  headers: { "X-XSRF-TOKEN": CSRF_TOKEN },
  baseURL: `/api`
});
</script>