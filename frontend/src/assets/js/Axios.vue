<script>
import axios from "axios";

// const CSRF_TOKEN = document.cookie.match(new RegExp(/XSRF-TOKEN=([^;]+)/i))[1];
const CSRF_TOKEN = document.head.querySelector("[name~=_csrf][content]")
  .content;
const CSRF_HEADER = document.head.querySelector("[name~=_csrf_header][content]")
  .content;

// "X-XSRF-TOKEN"
export const AXIOS = axios.create({
  headers: { [CSRF_HEADER]: CSRF_TOKEN },
  baseURL: `/api`
});
</script>