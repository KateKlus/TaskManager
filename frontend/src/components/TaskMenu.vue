<template>
    <div class="taskMenu">
        <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="taskMenu__header">
                <input type="text" v-model="taskItem.taskName" placeholder="Имя задачи" class="taskMenu__input taskMenu__taskName">
                <div class="taskMenu__text">TaskMgr-{{taskItem.id}}</div>
            </div>
            <div class="taskMenu__body">
                <div class="taskMenu__left">
                   <div class="popup__text">Описание задачи</div>
                    <textarea v-model="taskItem.description" placeholder="Описание задачи" class="taskMenu__textarea"></textarea>
                </div>
                <div class="taskMenu__right">
                    <div class="popup__text">Автор задачи</div>
                    <input type="text" v-model="taskItem.author.username" placeholder="Автор" class="taskMenu__input">
                    <div class="popup__text">Статус</div>
                    <select name="statusList"  @change="changeStatus" v-model="selected">
                        <option v-for="statusItem in statusList"
                        v-bind:value="statusItem.taskStatus.id">{{statusItem.taskStatus.statusName}}</option>
                    </select>
                </div>
            </div>
            <div class="taskMenu__custom">
                <div class="popup__text-title">Дополнительные поля</div>
                <ul class="custom__list">
                    <li class="custom__item" v-for="customField in customFieldsList">
                        <div class="popup__text">{{customField.attribute.attributeName}}</div>
                        <input type="text" class="taskMenu__input taskMenu__taskName" v-model="customField.stringValue">
                    </li>
                </ul>
            </div>
        <button class="popup__submit" @click=saveChanges>Сохранить</button>
        <button class="popup__submit" @click=deleteTask>Удалить</button>
        </div>
    </div>

</template>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            selected:this.taskItem.currentStatus.id,
            customFieldsList:[]
        }
    },
    props:['taskItem','statusList'],
    mounted(){
        this.getCustomFieldsList(this.taskItem.id);
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
                url: 'http://'+host+':'+port+'/api/tasks/'+self.taskItem.id+'/',
                data:self.taskItem
            }).then(function (response) {
                self.editCustomFields();
            }).catch(function (error) {
                alert("Error! "+ error)
            });
        },
        deleteTask(){
            var self = this;
            axios({
                method: 'delete',
                url: 'http://'+host+':'+port+'/api/tasks/'+self.taskItem.id+'/'
            }).then(function (response) {
                self.$root.$emit('updateBoard');
                self.$emit('wrapperClick');
            }).catch(function (error) {
                alert("Error! "+ error)
            });
        },
        getCustomFieldsList(taskItemId){
            var self = this;
            axios.get('http://'+host+':'+port+'/api/customfields/').then(function(response){
                response.data.forEach(function(customField){
                    if(customField.task.id == taskItemId){
                        self.customFieldsList.push(customField);
                    }
                });
            }).catch(function(error){
                alert(error);
            })
        },
        editCustomFields(){
            var self = this;
            var postError = false;
            this.customFieldsList.forEach(function(customField){
                axios({
                    method: 'put',
                    url: 'http://'+host+':'+port+'/api/customfields/'+customField.id+'/',
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
        display: flex;
        justify-content: space-around;
        padding: 20px;
    }
    .taskMenu__body{
        display: flex;
        justify-content: space-between;
        margin: 0 30px;
    }
    .taskMenu__taskName{
        margin: 0 auto;
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
