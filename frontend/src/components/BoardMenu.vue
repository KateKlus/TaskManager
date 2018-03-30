<template>
    <div class="boardMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="popup__title">Меню управления доской</div>
            <label for="" class="popup__label">
                <div class="popup__text">Изменить имя доски:</div>
                <input type="text" v-model="currentBoard.boardName">
            </label>
            <label for="" class="popup__label">
                <div class="popup__text">Изменить описание доски:</div>
                <input type="text" v-model="currentBoard.description">
            </label>
            <button class="popup__submit" @click.prevent="editBoard">Изменить доску</button>
            <button class="popup__submit" @click.prevent="deleteBoard">Удалить доску</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    props:['currentBoard'],
    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        },
        editBoard(){
            var self = this;
            axios({
                method: 'put',
                url: 'http://'+host+':'+port+'/api/boards/'+self.currentBoard.id+'/',
                data: self.currentBoard
            }).then(function (response) {
                console.log(response);
                self.$root.$emit('updateBoard');
                self.$emit('wrapperClick');
            }).catch(function (error) {
                alert("Error! "+ error)
            });
        },
        deleteBoard(){
            var self = this;
            axios({
                method: 'DELETE',
                url: 'http://'+host+':'+port+'/api/boards/'+self.currentBoard.id+'/'
            }).then(function (response) {
                self.$emit('wrapperClick');
                delete_cookie("current_board");
                document.location.replace("/board");
            }).catch(function (error) {
                if(error.response.status == '500'){
                    alert("Невозможно удалить доску, которая содержит данные!");
                }
                else{
                    alert("Error! "+ error);
                }
            });
        },
    }
}
</script>
