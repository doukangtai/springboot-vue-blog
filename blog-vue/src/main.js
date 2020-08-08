import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import {getRequest} from "./utils/api";
import {postRequest} from "./utils/api";
import {putRequest} from "./utils/api";
import {deleteRequest} from "./utils/api";
import {uploadFileRequest} from "./utils/api";
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import 'normalize.css/normalize.css'

Vue.config.productionTip = false

Vue.prototype.postRequest = postRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.uploadFileRequest = uploadFileRequest;

Vue.use(mavonEditor);

router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth) {
    if (store.state.user == undefined || store.state.user == '' || store.state.user.username == '' || store.state.user.username == undefined || store.state.user.nickname == '' || store.state.user.nickname == undefined) {
      next({path: '/login'});
    } else {
      next();
    }
  } else {
    next();
  }
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
