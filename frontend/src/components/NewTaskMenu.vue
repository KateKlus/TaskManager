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
                    <select name="statusList"  @change="selectStatus" v-model="selected">
                        <option v-for="statusItem in statusList"
                        v-bind:value="statusItem.taskStatus.id">{{statusItem.taskStatus.statusName}}</option>
                    </select>
                </div>
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
            selected:"",
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
                }
            },
            taskk:""
        }
    },
    props:['currentUser','statusList'],
    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        },
        addNewTask(){
            var self = this;
            this.taskItem.board.id = getCookie("current_board");
            axios({
                method: 'post',
                url: 'http://'+host+':'+port+'/api/users/'+self.currentUser.id+'/tasks',
                data:self.taskItem
            }).then(function (response) {
                self.$emit('wrapperClick');
                self.$root.$emit('updateBoard');
            }).catch(function (error) {
                alert("Error! "+ error)
            });
        },
        selectStatus(){
            this.taskItem.currentStatus.id = this.selected;
        }
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
</style>
