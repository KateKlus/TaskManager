import Vue from 'vue'
import VueRouter from 'vue-router'
import {routes} from './routes'
import pageContent from './Content.vue'

import board from './Board.vue'
import leftMenu from './components/LeftMenu.vue'
import statusList from './components/StatusList.vue'
import taskList from './components/TaskList.vue'
import contentList from './components/TaskContentList.vue'
import taskMenu from './components/TaskMenu.vue'
import newBoardMenu from './components/NewBoardMenu.vue'
import boardListMenu from './components/BoardListMenu.vue'
import userInviteMenu from './components/UserInviteMenu.vue'
import newStatusMenu from './components/NewStatusMenu.vue'
import newTaskMenu from './components/NewTaskMenu.vue'
import statusMenu from './components/StatusMenu.vue'
import boardMenu from './components/BoardMenu.vue'
import userMenu from './components/UserMenu.vue'
import templateMenu from './components/TemplateMenu.vue'
import newTemplateMenu from './components/NewTemplateMenu.vue'
import delTemplateMenu from './components/DelTemplateMenu.vue'
import editTemplateMenu from './components/EditTemplateMenu.vue'
import invitedUserMenu from './components/InvitedUserMenu.vue'

Vue.component('board', board);
Vue.component('leftMenu', leftMenu);
Vue.component('statusList', statusList);
Vue.component('taskList', taskList);
Vue.component('contentList', contentList);
Vue.component('taskMenu', taskMenu);
Vue.component('newBoardMenu', newBoardMenu);
Vue.component('boardListMenu', boardListMenu);
Vue.component('userInviteMenu', userInviteMenu);
Vue.component('newStatusMenu', newStatusMenu);
Vue.component('newTaskMenu', newTaskMenu);
Vue.component('statusMenu', statusMenu);
Vue.component('boardMenu', boardMenu);
Vue.component('userMenu', userMenu);
Vue.component('templateMenu', templateMenu);
Vue.component('newTemplateMenu', newTemplateMenu);
Vue.component('delTemplateMenu', delTemplateMenu);
Vue.component('editTemplateMenu', editTemplateMenu);
Vue.component('invitedUserMenu', invitedUserMenu);
Vue.use(VueRouter);
const router = new VueRouter({
    routes
});

new Vue({
    el: '#content',
    router,
    render: h => h(pageContent)
})
