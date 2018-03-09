<template>
    <div class="newBoardMenu">
       <div class="newBoardMenu__wrapper" @click="closeMenu"></div>
        <div class="newBoardMenu__body">
            <div class="newBoardMenu__title">Создать новую доску</div>
            <label for="" class="newBoardMenu__label">
                <div class="newBoardMenu__text">Введите имя для доски:</div>
                <input type="text" v-model="newBoard.boardName">
            </label>
            <label for="" class="newBoardMenu__label">
                <div class="newBoardMenu__text">Введите описание доски:</div>
                <input type="text" v-model="newBoard.description">
            </label>
            <button class="newBoardMenu__submit" @click.prevent="createNewBoard">Создать доску</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    props:['currentUser'],
    data(){
        return{
            newBoard:{
                boardName:"",
                description:""
            }
        }
    },
    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        },
        createNewBoard(){
            var self = this;
            axios({
                method: 'post',
                url: 'http://localhost:8080/api/users/'+self.currentUser.id+'/boards',
                data:self.newBoard
            }).then(function (response) {
                alert("Success!");
                self.$emit('wrapperClick');
            }).catch(function (error) {
                alert("Error! "+ error)
            });

        }
    }
}
</script>

<style lang="scss" scoped>
    .newBoardMenu__wrapper{
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(#000,.5);
        z-index: 0;
    }
    .newBoardMenu__body{
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
    .newBoardMenu__title{
        font-weight: bold;
        font-size: 18px;
        margin-bottom: 10px;
    }
    .newBoardMenu__submit{
        display: block;
        margin: 10px auto;
    }

</style>
