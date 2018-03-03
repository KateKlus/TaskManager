import Vue from 'vue'
import pageContent from './Content.vue'

import board from './Board.vue'
import leftMenu from './components/LeftMenu.vue'
import statusList from './components/StatusList.vue'
import taskList from './components/TaskList.vue'
import contentList from './components/TaskContentList.vue'

Vue.component('board', board);
Vue.component('leftMenu', leftMenu);
Vue.component('statusList', statusList);
Vue.component('taskList', taskList);
Vue.component('contentList', contentList);

new Vue({
    el: '#content',
    render: h => h(pageContent)
})
