import Vue from 'vue'
import App from './App'
import router from './js/router';
import BootstrapVue from 'bootstrap-vue'
import '@/assets/sass/variables.scss'

Vue.use(BootstrapVue)

Vue.config.productionTip = false

new Vue({
    render: h => h(App),
    router
}).$mount('#app')