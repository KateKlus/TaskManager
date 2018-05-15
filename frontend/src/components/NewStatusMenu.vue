<template>
    <div class="newStatusMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="popup__title">Добавить новый статус</div>
            <label for="" class="popup__label">
                <input type="text" class="popup__input" v-model="newStatus.statusName" placeholder="Название нового статуса">
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
                url: host+'/api/statuses/?access_token='+getCookie("access_token"),
                data:self.newStatus
            }).then(function (response) {
                self.addNewBoardStatus(response.data);
            }).catch(function (error) {
                self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
            });

        },
        addNewBoardStatus(taskstatus){
            var self = this;
            axios({
                method: 'post',
                url: host+'/api/boards/'+getCookie("current_board")+'/statuses/?access_token='+getCookie("access_token"),
                data:taskstatus
            }).then(function (response) {
                self.$root.$emit('updateBoard');
                self.$emit('wrapperClick');
            }).catch(function (error) {
                self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
            });
        }
    }
}
</script>
