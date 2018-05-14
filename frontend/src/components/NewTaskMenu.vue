<template>
    <div class="taskMenu">
        <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="taskMenu__header">
                <input type="text" v-model="taskItem.taskName" placeholder="Имя задачи" class="taskMenu__taskName popup__input">

            </div>
            <div class="taskMenu__body">
                <div class="taskMenu__left">
                    <textarea v-model="taskItem.description" placeholder="Описание задачи" class="popup__area"></textarea>
                    <div class="popup__text">Исполнитель</div>
                    <select name="userList" class="popup__select" v-model="selectedUser">
                        <option v-for="user in invitedUserList"
                        v-bind:value="user">{{user.username}}</option>
                    </select>
                </div>
                <div class="taskMenu__right">
                    <div class="popup__text">Статус</div>
                    <select name="statusList" class="popup__select" @change="selectStatus" v-model="selectedStatus">
                        <option v-for="statusItem in statusList"
                        v-bind:value="statusItem.taskStatus.id">{{statusItem.taskStatus.statusName}}</option>
                    </select>
                    <div class="popup__text">Шаблон</div>
                    <select name="templateList" class="popup__select" @change="selectTemplate" v-model="selectedTemplate">
                        <option v-for="templateItem in templateList"
                        v-bind:value="templateItem">{{templateItem.taskTemplateName}}</option>
                    </select>
                </div>
            </div>
            <div class="taskMenu__custom">
                <div class="popup__text-title">Дополнительные поля</div>
                <ul class="custom__list">
                    <li class="custom__item" v-for="customField in customFieldsList">
                        <div class="popup__text">{{customField.attribute.attributeName}}</div>
                        <input :type="customField.attribute.attributeType" v-model="customField.stringValue" class="taskMenu__taskName popup__input">
                    </li>
                </ul>
            </div>
        <button class="popup__submit" @click=addNewTask>Добавить</button>
        </div>
    </div>

</template>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            selectedStatus:"",
            selectedTemplate:"",
            selectedUser:"",
            attributeList:"",
            customFieldsList:[],
            invitedUserList:[],
            taskItem:{
                taskName:"",
                description:"",
                currentStatus:{
                    id:""
                },
                board:{
                    id:""
                },
                author:{
                    id:this.currentUser.id
                },
                taskTemplate: "",
            }
        }
    },
    props:['currentUser','statusList', 'templateList'],
    mounted(){
        var self = this;
        this.templateList.forEach(function(template){
            if(template.default){
                self.selectedTemplate = template;
                self.getListOfAttributes(template.id);
            }
        });
        axios.get(host+'/api/members/?access_token='+getCookie("access_token")).then(function(response){
            self.invitedUserList = [];
            response.data.forEach(function(member){
                if(member.board.id == getCookie("current_board")){
                    self.invitedUserList.push(member.user);
                }
            })
        }).catch(function(error){
            self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
        })
    },
    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        },
        addNewTask(){
            var self = this;
            this.taskItem.board.id = getCookie("current_board");
            this.taskItem.taskTemplate = this.selectedTemplate;
            axios({
                method: 'post',
                url: host+'/api/users/'+self.currentUser.id+'/tasks/?access_token='+getCookie("access_token"),
                data:self.taskItem
            }).then(function (response) {
                self.sendCustomFields(response.data);
                self.sendSelectedUser(response.data);
            }).catch(function (error) {
                self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
            });
        },
        selectStatus(){
            this.taskItem.currentStatus.id = this.selectedStatus;
        },
        selectTemplate(){
            this.getListOfAttributes(this.selectedTemplate.id);
        },
        getListOfAttributes(templateId){
            var self = this;
            axios.get(host+'/api/tasktemplates/'+templateId+'/attributes/?access_token='+getCookie("access_token")).then(function(response){
                self.attributeList = response.data;
                self.$root.$emit('updateBoard');
                self.addNewCustomField();
            }).catch(function(error){
                self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
            })
        },
        addNewCustomField(){
            var self = this;
            self.customFieldsList=[];
            this.attributeList.forEach(function(attribute){
                var customField = {
                attribute: "",
                stringValue: "",
                numericValue: "",
                dateValue: "",
                task:""
            }
                customField.attribute = attribute;
                self.customFieldsList.push(customField);
            });

        },
        sendCustomFields(taskItem){
            var self = this;
            var postError = false;
            this.customFieldsList.forEach(function(customField){
                customField.task = taskItem;
                axios({
                    method: 'post',
                    url: host+'/api/customfields/?access_token='+getCookie("access_token"),
                    data:customField
                }).catch(function (error) {
                    postError = error;
                });
            });
            if (!postError){
            self.$root.$emit('updateBoard');
            self.$emit('wrapperClick');
            }
            else{
                self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
            }
        },
        sendSelectedUser(taskItem){
            var self = this;
            axios({
                method: 'post',
                url: host+'/api/tasks/'+taskItem.id+'/users/?access_token='+getCookie("access_token"),
                data: self.selectedUser
            }).catch(function (error) {
                self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
            });
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
