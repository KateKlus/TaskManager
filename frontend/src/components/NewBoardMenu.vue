<template>
    <div class="newBoardMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="popup__title">Создать новую доску</div>
            <label for="" class="popup__label">
                <input type="text" class="popup__input" v-model="newBoard.boardName" placeholder="Название новой доски">
            </label>
            <label for="" class="popup__label">
                <input type="text" class="popup__input" v-model="newBoard.description" placeholder="Описание доски">
            </label>
            <button class="popup__submit" @click.prevent="createNewBoard">Создать доску</button>
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
                url: host+'/api/users/'+self.currentUser.id+'/boards/?access_token='+getCookie("access_token"),
                data:self.newBoard
            }).then(function (response) {
                self.$root.$emit('onBoardSelect', response.data);
                self.$emit('wrapperClick');
                self.$root.$emit('updateBoard');
                window.location = "#/board";
            }).catch(function (error) {
                self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
            });

        }
    }
}
</script>
