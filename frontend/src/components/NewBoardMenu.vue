<template>
    <div class="newBoardMenu">
       <div class="menu__wrapper" @click="closeMenu"></div>
        <div class="menu__body">
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
                self.$emit('wrapperClick');
                document.location.replace("/board");
            }).catch(function (error) {
                alert("Error! "+ error)
            });

        }
    }
}
</script>

<style lang="scss" scoped>
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
