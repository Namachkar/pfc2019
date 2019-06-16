import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router);

const index = () => import("@/components/index");

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: '/',
            component: index,
        }
    ]
});