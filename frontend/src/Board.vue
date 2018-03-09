<template>
    <div class="wrapper">
        <header id="board__header" class="board__header">
            <leftMenu :currentUser="currentUser"></leftMenu>
            <div class="board__title">{{boardTitle}}</div>
            <div class="board__user">
                <a href="" class="user__logout" @click.prevent="logOut">выйти</a>
                <div class="user__name">{{currentUser.username}}</div>
                <div class="user__avatar">
                    <img src="./assets/avatar.jpg" alt="User" class="user__avatar-img">
                </div>
            </div>
        </header>
        <statusList :statusItems="statusItems"></statusList>
        <taskMenu v-if="showTaskMenu" :taskItem="clickedTask" :statusItems="statusItems" @wrapperClick="showTaskMenu = !showTaskMenu"></taskMenu>

    </div>

</template>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            showTaskMenu: false,
            clickedTask:"",
            currentUser:"",
            boardTitle: 'Название доски',
            statusItems:[
                {statusId: 0,
                 statusName: "Название статуса",
                 taskItems:[
                     {taskId:0,
                      taskName: "Имя задачи",
                      statusId: 0,
                      taskDate: "28.02.2018",
                      taskTime: "12h",
                      taskExecutor:"German",
                      customFields:[
                          {fieldId: 0, fieldName: "Название поля", fieldContent: "Содержание поля"},
                          {fieldId: 1, fieldName: "ipsum", fieldContent: "Lorem ipsum dolor sit amet"}
                      ]},
                     {taskId:1,
                      taskName: "impedit",
                      statusId: 0,
                      taskDate: "12.02.2018",
                      taskTime: "4h",
                      taskExecutor:"Exec",
                      customFields:[
                          {fieldId: 3, fieldName: "dolor", fieldContent: "Контееент!"},
                          {fieldId: 4, fieldName: "sit", fieldContent: "Абракадабра"}
                      ]}
                 ]},
                {statusId: 1,
                 statusName: "Deserunt",
                 taskItems:[
                     {taskId:2,
                      taskName: "Task 3",
                      statusId: 1,
                      taskDate: "01.01.2018",
                      taskTime: "36h",
                      taskExecutor:"Thomas Check",
                      customFields:[
                          {fieldId: 5, fieldName: "amet", fieldContent: "field 5"},
                          {fieldId: 6, fieldName: "consectetur", fieldContent: "eius dolor impedit"},
                          {fieldId: 7, fieldName: "adipisicing", fieldContent: "field 7"},
                          {fieldId: 8, fieldName: "elit", fieldContent: "dolor sit amet"}
                      ]},
                     {taskId:3,
                      taskName: "voluptates",
                      statusId: 1,
                      taskDate: "28.01.2017",
                      taskTime: "1h",
                      taskExecutor:"Fill Kill",
                      customFields:[
                          {fieldId: 9, fieldName: "Eaque", fieldContent: "deserunt explicabo voluptates"},
                          {fieldId: 10, fieldName: "officia", fieldContent: "Eaque ad officia"}
                      ]}
                 ]},
                {statusId: 2,
                 statusName: "Explicabo",
                 taskItems:[
                   {taskId:4,
                    taskName: "beatae",
                    statusId: 2,
                    taskDate: "21.08.2018",
                    taskTime: "5h",
                    taskExecutor:"Rus Lang",
                    customFields:[
                        {fieldId: 11, fieldName: "magnam", fieldContent: "field 11"}
                    ]}
                ]}
            ]
        }
    },
    beforeCreate(){
        var self = this;
        if(getCookie("access_token")){
            axios.get("http://localhost:8080/api/getUserId?access_token=" + getCookie("access_token"))
                .then(function(response){
                    axios.get("http://localhost:8080/api/users/"+response.data)
                        .then(function(response){
                        self.currentUser = response.data;
                    })
                })
                .catch(function(error){
                    delete_cookie("access_token");
                    return error;
                });
        }
        else{
            document.location.replace("/auth");
        }
    },
    mounted(){
        var self = this;
        this.$root.$on('clickOnTaskName', function(task){
            self.clickedTask = task;
            self.showTaskMenu = !self.showTaskMenu;
        });
        this.$root.$on('changeStatus', function(selectedTask, newStatus){
            self.statusItems.forEach(function(statusItem){
                statusItem.taskItems.forEach(function(taskItem){
                    if (taskItem == selectedTask){
                        statusItem.taskItems.splice(statusItem.taskItems.indexOf(taskItem),1);
                    };
                });
            });
            self.statusItems.forEach(function(statusItem){
                if(statusItem.statusId == newStatus){
                    selectedTask.statusId = newStatus;
                    statusItem.taskItems.push(selectedTask);
                };
            });
        });
    },
    methods:{
        logOut(){
            axios.get("http://localhost:8080/api/logouts?access_token="+getCookie("access_token"))
                .then(function(response){
                    delete_cookie("access_token");
                    document.location.replace("/auth");
                })
        }
    }
}
</script>

<style lang="scss" scoped>
    .board__header{
        display: flex;
        justify-content: space-between;
        padding: 8px;
        align-items: center;
        background: rgba(#333,.2);
        border: 1px solid black;
        min-width: 400px;
    }
    .user__avatar{
        max-width: 50px;
    }
    .user__avatar-img{
        width: 100%;
        border-radius: 50%;
    }
    .board__button,.user__avatar, .user__name{
        display: inline-block;
        vertical-align: middle;
    }
    .user__name{
        margin-right: 10px;
    }
    .user__logout{
        text-decoration: none;
        color: black;
        padding: 5px 10px;
        margin-right: 10px;
        border: 1px solid black;
        border-radius: 10px;
        background: #ebebeb;
        &:hover{
            background: #fff;
        }

    }

</style>
