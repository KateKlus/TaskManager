<template>
    <nav class="menu">
        <a href="" class="board__button" @click.prevent="showMenu = !showMenu">
           <img src="../assets/menu.png" alt="MNU" class="board__button-img">
        </a>
        <div class="menu__block" v-if="showMenu" >
            <div class="menu__title">Меню</div>
            <a href="" class="board__button board__button-close"><img src="../assets/menu.png" alt="MNU" class="board__button-img" @click.prevent="showMenu = !showMenu"></a>
            <ul class="menu__list">
                <li class="menu__item"><a href="" class="menu__link" @click.prevent="showNewBoardMenu = !showNewBoardMenu">Создать доску</a></li>
                <li class="menu__item"><a href="" class="menu__link" @click.prevent="showNewStatusMenu = !showNewStatusMenu">Добавить статус</a></li>
                <li class="menu__item"><a href="" class="menu__link" @click.prevent="showNewTaskMenu = !showNewTaskMenu">Добавить задачу</a></li>
                <li class="menu__item"><a href="" class="menu__link" @click.prevent="showTemplateMenu = !showTemplateMenu">Управление шаблонами</a></li>
                <li class="menu__item"><a href="" class="menu__link" @click.prevent="showBoardListMenu = !showBoardListMenu">Мои доски</a></li>
            </ul>
        </div>
        <newBoardMenu v-if="showNewBoardMenu" @wrapperClick="showNewBoardMenu = !showNewBoardMenu" :currentUser="currentUser"></newBoardMenu>
        <newStatusMenu v-if="showNewStatusMenu" @wrapperClick="showNewStatusMenu = !showNewStatusMenu" :currentUser="currentUser"></newStatusMenu>
        <newTaskMenu v-if="showNewTaskMenu" @wrapperClick="showNewTaskMenu = !showNewTaskMenu" :currentUser="currentUser" :statusList="statusList" :templateList="templateList"></newTaskMenu>
        <boardListMenu v-if="showBoardListMenu" @wrapperClick="showBoardListMenu = !showBoardListMenu"></boardListMenu>
        <templateMenu v-if="showTemplateMenu" @wrapperClick="showTemplateMenu = !showTemplateMenu" :templateList="templateList"></templateMenu>
    </nav>

</template>


<script>
export default{
    data(){
        return{
            showMenu : false,
            showNewBoardMenu: false,
            showNewStatusMenu:false,
            showNewTaskMenu:false,
            showBoardListMenu: false,
            showTemplateMenu: false
        }
    },
    props:['currentUser','statusList','templateList']
}
</script>

<style lang="scss" scoped>
    .menu__block{
        position: absolute;
        border: 1px solid black;
        top: 0;
        bottom: 0;
        left: 0;
        background: #bebebe;
        text-align: center;
        padding: 8px;
        min-width: 250px;
    }
    .menu__hide{
        display: none;
    }
    .menu__title{
        width: 80%;
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
        .board__button-img{
            width: 100%;
        }
    }
    .menu__item{
        margin: 5px;
    }
    .menu__link{
        display: inline-block;
        width: 100%;
        padding: 5px;
        text-decoration: none;
        color: black;
        border: 1px solid black;
        border-radius: 10px;
        background: #ebebeb;
        &:hover{
            background: #ffffff;
        }
    }

</style>
