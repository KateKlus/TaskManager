import boardContent from './Board.vue';
import registration from './Registration.vue';
export const routes = [
    {path: '/', component: registration},
    {path: '/board', component: boardContent},
    {path: '/registration', component: registration},
];
