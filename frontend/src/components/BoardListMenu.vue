<template>
    <div class="boardListMenu">
       <div class="boardListMenu__wrapper" @click="closeMenu"></div>
        <div class="boardListMenu__body">
            <div class="boardListMenu__title">Список досок</div>
            <ul class="boardListMenu__list">
                <li class="boardListMenu__item" v-for="board in boardList"><a href="" class="boardListMenu__link">{{board.boardName}}</a></li>
            </ul>

        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            boardList: ""
        }
    },
    props:['currentUser'],
    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        }
    },
    beforeCreate(){
        self = this;
        axios.get("http://localhost:8080/api/boards/")
            .then(function(response){
            self.boardList = response.data;
        })
    }
}
</script>

<style lang="scss" scoped>
    .boardListMenu__wrapper{
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(#000,.5);
        z-index: 0;
    }
    .boardListMenu__body{
        text-align: center;
        position: absolute;
        z-index: 100;
        padding: 10px 30px;
        top: 50px;
        left: 20%;
        right: 20%;
        max-height: 560px;
        width: 800px;
        overflow-y: auto;
        border: 2px solid black;
        background-color: #cccccc;
    }
    .boardListMenu__title{
        font-weight: bold;
        font-size: 18px;
        margin-bottom: 10px;
    }
    .boardListMenu__item{
        margin: 5px;
    }
    .boardListMenu__link{
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
