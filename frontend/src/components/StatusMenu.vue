<template>
    <div class="statusMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="popup__title">Статус меню</div>
            <label for="" class="popup__label">
                <div class="popup__text">Изменить имя статуса:</div>
                <input type="text" class="popup__input" v-model="statusItem.taskStatus.statusName">
            </label>
            <button class="popup__submit" @click.prevent="editStatus">Изменить статус</button>
            <button class="popup__submit" @click.prevent="deleteStatus">Удалить статус</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    props:['statusItem','taskList'],
    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        },
        editStatus(){
            var self = this;
            axios({
                method: 'put',
                url: host+'/api/statuses/'+self.statusItem.taskStatus.id+'/?access_token='+getCookie("access_token"),
                data: self.statusItem.taskStatus
            }).then(function (response) {
                self.$root.$emit('updateBoard');
                self.$emit('wrapperClick');
            }).catch(function (error) {
                self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
            });
        },
        deleteStatus(){
            var self = this;
            this.taskList.forEach(function(task){
                if(task.currentStatus.id == self.statusItem.taskStatus.id){
                    axios({
                        method: 'delete',
                        url: host+'/api/tasks/'+task.id+'/?access_token='+getCookie("access_token")
                    }).catch(function (error) {
                        self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
                    });
                }
            })
            axios({
                method: 'delete',
                url: host+'/api/statuses/'+self.statusItem.taskStatus.id+'/?access_token='+getCookie("access_token")
            }).then(function (response) {
                self.$root.$emit('updateBoard');
                self.$emit('wrapperClick');
            }).catch(function (error) {
                self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
            });
        },
    }
}
</script>
