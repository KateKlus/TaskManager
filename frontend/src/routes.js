import boardContent from './Board.vue';
import registration from './Registration.vue';
import autorisation from './Login.vue';

export const routes = [
    {path: '/', component: autorisation},
    {path: '/board', component: boardContent},
    {path: '/registration', component: registration},
    {path: '/auth', component: autorisation}
];
