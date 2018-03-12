<template>
    <div class="taskMenu">
        <div class="menu__wrapper" @click="closeMenu"></div>
        <div class="menu__body">
            <div class="taskMenu__header">
                <input type="text" v-model="taskItem.taskName" placeholder="Имя задачи" class="taskMenu__input taskMenu__taskName">
                <div class="taskMenu__text">TaskMgr-{{taskItem.id}}</div>
            </div>
            <div class="taskMenu__body">
                <div class="taskMenu__left">
                   <div class="taskMenu__text">Описание задачи</div>
                    <textarea v-model="taskItem.description" placeholder="Описание задачи" class="taskMenu__textarea"></textarea>
                    <div class="taskMenu__text">Дата создания задачи</div>
                    <input type="text" v-model="taskItem.taskDate" placeholder="Дата" class="taskMenu__input">
                </div>
                <div class="taskMenu__right">
                    <div class="taskMenu__text">Автор задачи</div>
                    <input type="text" v-model="taskItem.author.username" placeholder="Автор" class="taskMenu__input">
                    <div class="taskMenu__text">Статус</div>
                    <select name="statusList"  @change="changeStatus" v-model="selected">
                        <option v-for="statusItem in statusList"
                        v-bind:value="statusItem.taskStatus.id">{{statusItem.taskStatus.statusName}}</option>
                    </select>
                    <div class="taskMenu__text">Исполнители</div>
                    <input type="text" v-model="taskItem.taskExecutor" placeholder="Исполнители" class="taskMenu__input">
                    <div class="taskMenu__text">Затраченное время</div>
                    <input type="text" v-model="taskItem.taskTime" placeholder="Затраченное время" class="taskMenu__input">
                </div>
            </div>
        <button class="taskMenu__save" @click=saveChanges>Сохранить</button>
        </div>
    </div>

</template>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            selected:this.taskItem.currentStatus.id
        }
    },
    props:['taskItem','statusList'],
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
                url: 'http://localhost:8080/api/tasks/'+self.taskItem.id,
                data:self.taskItem
            }).then(function (response) {
                self.$emit('wrapperClick');
            }).catch(function (error) {
                alert("Error! "+ error)
            });
        },
    },
    beforeCreate(){
            var self = this;
            axios.get("http://localhost:8080/api/tasks/4")
            .then(function(response){
            self.taskEditedItem = response.data;
        })
    }
}
</script>

<style lang="scss" scoped>
    .taskMenu__header{
        display: flex;
        justify-content: space-around;
    }
    .taskMenu__body{
        display: flex;
        justify-content: space-around;
    }
    .taskMenu__input{
        display: block;
    }
    .taskMenu__header{
        padding:20px;
    }
    .taskMenu__left,.taskMenu__right{
        padding: 20px;
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
    .taskMenu__save{
        display: block;
        margin: 20px auto;
    }
</style>
