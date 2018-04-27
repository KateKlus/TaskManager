<template>
    <div class="taskMenu">
        <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="taskMenu__header">
                <input type="text" v-model="taskItem.taskName" placeholder="Имя задачи" class="taskMenu__input taskMenu__taskName">

            </div>
            <div class="taskMenu__body">
                <div class="taskMenu__left">
                   <div class="popup__text">Описание задачи</div>
                    <textarea v-model="taskItem.description" placeholder="Описание задачи" class="taskMenu__textarea"></textarea>
                </div>
                <div class="taskMenu__right">
                    <div class="popup__text">Статус</div>
                    <select name="statusList"  @change="selectStatus" v-model="selectedStatus">
                        <option v-for="statusItem in statusList"
                        v-bind:value="statusItem.taskStatus.id">{{statusItem.taskStatus.statusName}}</option>
                    </select>
                    <div class="popup__text">Шаблон</div>
                    <select name="templateList"  @change="selectTemplate" v-model="selectedTemplate">
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
                        <input :type="customField.attribute.attributeType" v-model="customField.stringValue" class="taskMenu__input taskMenu__taskName">
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
            attributeList:"",
            customFieldsList:[],
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
                taskTemplate: ""
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
                url: 'http://'+host+':'+port+'/api/users/'+self.currentUser.id+'/tasks/?access_token='+getCookie("access_token"),
                data:self.taskItem
            }).then(function (response) {
                self.sendCustomFields(response.data);
            }).catch(function (error) {
                alert("Error! "+ error)
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
            axios.get('http://'+host+':'+port+'/api/tasktemplates/'+templateId+'/attributes/?access_token='+getCookie("access_token")).then(function(response){
                self.attributeList = response.data;
                self.$root.$emit('updateBoard');
                self.addNewCustomField();
            }).catch(function(error){
                alert(error);
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
                    url: 'http://'+host+':'+port+'/api/customfields/?access_token='+getCookie("access_token"),
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
                alert(postError);
            }
        }
    }
}
</script>

<style lang="scss" scoped>
    .taskMenu__header{
        width: 100%;
        padding: 20px;
    }
    .taskMenu__taskName{
        margin: 0 auto;
    }
    .taskMenu__body{
        display: flex;
        justify-content: space-between;
        margin: 0 30px;
    }
    .taskMenu__input{
        display: block;
    }
    .taskMenu__left,.taskMenu__right{
        padding: 10px;
        border: 1px solid black;
    }
    .taskMenu__left{
        min-width: 450px;
    }
    .taskMenu__textarea{
        width: 400px;
        min-height: 100px;
        resize: none;
    }
    .taskMenu__custom{
        border: 1px solid black;
        margin: 20px 30px;
        padding: 10px;
    }
</style>
