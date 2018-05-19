<template>
    <nav class="menu">
        <a href="" class="board__button" @click.prevent="showMenu = 'menu__block active'">
           <img src="../assets/menu.png" alt="MNU" class="board__button-img">
        </a>
        <div :class="showMenu" v-if="showMenu" >
            <div class="menu__title"></div>
            <a href="" class="board__button board__button-close"><img src="../assets/menu.png" alt="MNU" class="board__button-img" @click.prevent="showMenu = 'menu__block'"></a>
            <ul class="menu__list">
                <li class="menu__item"><a href="" class="menu__link" @click.prevent="showNewBoardMenu = !showNewBoardMenu">Создать доску</a></li>
                <li class="menu__item"><a href="" class="menu__link" v-if="showEditTasks" @click.prevent="showNewStatusMenu = !showNewStatusMenu">Добавить статус</a></li>
                <li class="menu__item"><a href="" class="menu__link" v-if="showEditTasks" @click.prevent="showNewTaskMenu = !showNewTaskMenu">Добавить задачу</a></li>
                <li class="menu__item"><a href="" class="menu__link" v-if="showTemplates" @click.prevent="showTemplateMenu = !showTemplateMenu">Управление шаблонами</a></li>
                <li class="menu__item"><a href="" class="menu__link" @click.prevent="showBoardListMenu = !showBoardListMenu">Мои доски</a></li>
            </ul>
        </div>
        <transition-group name='fade'>
            <newBoardMenu v-if="showNewBoardMenu" key="newBoard" @wrapperClick="showNewBoardMenu = !showNewBoardMenu" :currentUser="currentUser"></newBoardMenu>
            <newStatusMenu v-if="showNewStatusMenu" key="newStatus" @wrapperClick="showNewStatusMenu = !showNewStatusMenu" :currentUser="currentUser"></newStatusMenu>
            <newTaskMenu v-if="showNewTaskMenu" key="newTask" @wrapperClick="showNewTaskMenu = !showNewTaskMenu" :currentUser="currentUser" :statusList="statusList" :templateList="templateList"></newTaskMenu>
            <boardListMenu v-if="showBoardListMenu" key="boardList" @wrapperClick="showBoardListMenu = !showBoardListMenu"></boardListMenu>
            <templateMenu v-if="showTemplateMenu" key="templateMenu" @wrapperClick="showTemplateMenu = !showTemplateMenu" :templateList="templateList"></templateMenu>
        </transition-group>
    </nav>

</template>


<script>
export default{
    data(){
        return{
            showMenu : "menu__block",
            showNewBoardMenu: false,
            showNewStatusMenu:false,
            showNewTaskMenu:false,
            showBoardListMenu: false,
            showTemplateMenu: false,
            showTemplates: true,
            showEditTasks: true,
        }
    },
    props:['currentUser','statusList','templateList','currentBoard'],
    watch:{
        currentBoard : function(){
            var self = this;
            this.$root.$emit('permissionStatus','templates', function(callback){
                self.showTemplates = callback;
            });
            this.$root.$emit('permissionStatus','editTasks', function(callback){
                self.showEditTasks = callback;
            });
        },
    },
}
</script>

<style lang="scss" scoped>
    .menu__block{
        position: absolute;
        top: 0;
        height: 2000px;
        left: -999px;
        text-align: center;
        padding: 8px;
        min-width: 250px;
        background: #58c791;
        border-radius: 4px;
        box-shadow: 1px 1px 5px rgba(black, .5);
        transition: left .5s;
        &.active{
            left: 0;
        }
    }
    .menu__hide{
        display: none;
    }
    .menu__title{
        width: 80%;
    }
    .menu__list{
        margin-top: 15px;
    }
    .menu__title, .board__button{
        display: inline-block;
        vertical-align: middle;
    }
    .board__button,.user__avatar, .user__name{
    display: inline-block;
    }
    .board__button{
        max-width: 25px;
        margin-top: 10px;
        .board__button-img{
            width: 100%;
        }
    }
    .menu__item{
        margin: 5px;
    }
    .menu__link{
        display: block;
        padding: 7px;
        text-decoration: none;
        color: #757575;
        font-weight: bold;
        border-radius: 5px;
        background: #fff;
        margin-bottom: 7px;
        box-shadow: 1px 1px 4px rgba(black, .5);
        transition: box-shadow .3s;
        &:hover{
            box-shadow: none;
        }
    }

</style>
