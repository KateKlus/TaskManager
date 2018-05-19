<template>
    <div class="taskMenu">
        <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">

            <div class="taskMenu__header">
                <input type="text" v-model="taskItem.taskName" placeholder="Имя задачи" class="taskMenu__taskName popup__input">
                <div class="popup__text">TaskMgr-{{taskItem.id}}</div>
            </div>
            <div class="taskMenu__body">
                <div class="taskMenu__left">
                  <div class="popup__text">Автор задачи</div>
                    <input type="text" v-model="taskItem.author.username" placeholder="Автор" class="popup__input">
                   <div class="popup__text">Описание задачи</div>
                    <textarea v-model="taskItem.description" placeholder="Описание задачи" class="popup__area"></textarea>
                </div>
                <div class="taskMenu__right">

                    <div class="popup__text">Статус</div>
                    <select name="statusList" class="popup__select" @change="changeStatus" v-model="selected">
                        <option v-for="statusItem in statusList"
                        v-bind:value="statusItem.taskStatus.id">{{statusItem.taskStatus.statusName}}</option>
                    </select>
                    <div class="popup__text">Исполнитель: <div class="popup__executor">{{executor}}</div></div>
                </div>
            </div>
            <div class="taskMenu__custom">
                <div class="popup__text-title">Дополнительные поля</div>
                <ul class="custom__list">
                    <li class="custom__item" v-for="customField in customFieldsList">
                        <div class="popup__text">{{customField.attribute.attributeName}}</div>
                        <input :type="customField.attribute.attributeType" class="taskMenu__taskName popup__input" v-model="customField.stringValue">
                    </li>
                </ul>
            </div>
        <button class="popup__submit" v-if="showTaskEdit" @click=saveChanges>Сохранить</button>
        <button class="popup__submit delete" v-if="showTaskDelete" @click=deleteTask>Удалить</button>
        </div>
    </div>

</template>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            selected:this.taskItem.currentStatus.id,
            customFieldsList:[],
            showTaskDelete: true,
            showTaskEdit: true,
            executor: '',
        }
    },
    props:['taskItem','statusList'],
    mounted(){
        var self = this;
        this.getCustomFieldsList(this.taskItem.id);
        this.$root.$emit('permissionStatus','deleteTask', function(callback){
            self.showTaskDelete = callback;
        });
        this.$root.$emit('permissionStatus','editTask', function(callback){
            self.showTaskEdit = callback;
        });
        axios.get(host+'/api/tasks/'+self.taskItem.id+'/users/?access_token='+getCookie("access_token")).then(function(response){
                if(response.data.length == 2){
                    self.executor = response.data[1].username;
                }else{
                    self.executor = 'None';
                }
            }).catch(function(error){
                self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
            })
    },
    methods:{
        closeMenu(){
            this.$emit('wrapperClick')
        },
        changeStatus(){
            this.taskItem.currentStatus.id = this.selected;
        },
        saveChanges(){
            var self = this;
            axios({
                method: 'put',
                url: host+'/api/tasks/'+self.taskItem.id+'/?access_token='+getCookie("access_token"),
                data:self.taskItem
            }).then(function (response) {
                self.editCustomFields();
            }).catch(function (error) {
                self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
            });
        },
        deleteTask(){
            var self = this;
            axios({
                method: 'delete',
                url: host+'/api/tasks/'+self.taskItem.id+'/?access_token='+getCookie("access_token")
            }).then(function (response) {
                self.$root.$emit('updateBoard');
                self.$emit('wrapperClick');
            }).catch(function (error) {
                self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
            });
        },
        getCustomFieldsList(taskItemId){
            var self = this;
            axios.get(host+'/api/customfields/').then(function(response){
                response.data.forEach(function(customField){
                    if(customField.task.id == taskItemId){
                        self.customFieldsList.push(customField);
                    }
                });
            }).catch(function(error){
                self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
            })
        },
        editCustomFields(){
            var self = this;
            var postError = false;
            this.customFieldsList.forEach(function(customField){
                axios({
                    method: 'put',
                    url: host+'/api/customfields/'+customField.id+'/?access_token='+getCookie("access_token"),
                    data:customField
                }).catch(function (error) {
                    postError = error;
                });
            });
            if (!postError){
            self.$root.$emit('updateBoard');
            self.$emit('wrapperClick');
            self.$root.$emit('showDialog',"Задача успешно изменена!",'showMessage');
            }
            else{
                self.$root.$emit('showDialog',postError,'showError');
            }
        }
    }
}
</script>

<style lang="scss" scoped>
    .taskMenu__header{
        display: flex;
        justify-content: space-around;
        padding: 20px;
        border-bottom: 2px dashed rgba(black, .5);
    }
    .taskMenu__body{
        display: flex;
        justify-content: space-between;
        margin: 0 30px;
    }
    .taskMenu__taskName{
        margin: 0 auto;
    }
    .taskMenu__left,.taskMenu__right{
        padding: 10px 0;
    }
    .taskMenu__right{
        margin: 0 auto;
    }
    .taskMenu__left{
        min-width: 450px;
        border-right: 2px dashed rgba(black, .5);
    }
    .taskMenu__custom{
        border-top: 2px dashed rgba(black, .5);
        padding: 10px;
    }
</style>
