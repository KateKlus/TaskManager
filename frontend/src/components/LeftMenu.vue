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
                <li class="menu__item"><a href="" class="menu__link">Мои доски</a></li>
            </ul>
        </div>
        <newBoardMenu v-if="showNewBoardMenu" @wrapperClick="showNewBoardMenu = !showNewBoardMenu" :currentUser="currentUser"></newBoardMenu>
    </nav>

</template>


<script>
export default{
    data(){
        return{
            showMenu : false,
            showNewBoardMenu: false
        }
    },
    props:['currentUser']
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
        border: 1px solid black;
        border-radius: 10px;
        background: #ebebeb;
        &:hover{
            background: #ffffff;
        }
    }
    .menu__link{
        display: inline-block;
        width: 100%;
        padding: 5px;
        text-decoration: none;
        color: black;
    }

</style>
