<template>
    <nav class="menu">
        <a href="" class="board__button" id="leftMenuShow" @click="CloseMenu($event)">
           <img src="../assets/menu.png" alt="MNU" class="board__button-img">
        </a>
        <div class="menu__block" :class="{menu__hide : hide}" >
            <div class="menu__title">{{leftMenu.menuTitle}}</div>
            <a href="" class="board__button board__button-close"><img src="../assets/menu.png" alt="MNU" class="board__button-img" @click="CloseMenu($event)"></a>
            <ul class="menu__list">
                <li class="menu__item" v-for="menuItem in leftMenu.menuItems"><a :href="menuItem.menuItemLink" class="menu__link">{{menuItem.menuItemName}}</a></li>
            </ul>
        </div>

    </nav>

</template>


<script>
export default{
    data(){
        return{
            hide : true
        }
    },
    props:['leftMenu'],
    methods:{
        CloseMenu(event){
            event.preventDefault();
            this.hide = !this.hide;
        }
    }
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
        padding: 5px;
        margin: 5px;
        border: 1px solid black;
        border-radius: 10px;
        background: #ebebeb;
        &:hover{
            background: #ffffff;
        }
    }
    .menu__link{
        text-decoration: none;
        color: black;
    }

</style>
