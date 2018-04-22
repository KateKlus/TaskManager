<template>
    <div class="newStatusMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="popup__title">Добавить новый статус</div>
            <label for="" class="popup__label">
                <div class="popup__text">Введите имя статуса:</div>
                <input type="text" v-model="newStatus.statusName">
            </label>
            <button class="popup__submit" @click.prevent="addNewStatus">Добавить статус</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    props:['currentUser'],
    data(){
        return{
            newStatus:{
                statusName:""
            }
        }
    },
    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        },
        addNewStatus(){
            var self = this;
            axios({
                method: 'post',
                url: 'http://'+host+':'+port+'/api/statuses/?access_token='+getCookie("access_token"),
                data:self.newStatus
            }).then(function (response) {
                self.addNewBoardStatus(response.data);
            }).catch(function (error) {
                alert("Error! "+ error)
            });

        },
        addNewBoardStatus(taskstatus){
            var self = this;
            axios({
                method: 'post',
                url: 'http://'+host+':'+port+'/api/boards/'+getCookie("current_board")+'/statuses/?access_token='+getCookie("access_token"),
                data:taskstatus
            }).then(function (response) {
                self.$root.$emit('updateBoard');
                self.$emit('wrapperClick');
            }).catch(function (error) {
                alert("Error! "+ error)
            });
        }
    }
}
</script>
