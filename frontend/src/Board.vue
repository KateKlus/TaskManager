<template>
    <div class="wrapper">
        <header v-if="selectedBoardID" id="board__header" class="board__header">
            <leftMenu :currentUser="currentUser" :statusList="statusList"></leftMenu>
            <div class="board__title">{{currentBoard.boardName}}</div>
            <div class="board__user">
                <a href="" class="user__logout" @click.prevent="logOut">выйти</a>
                <div class="user__name">{{currentUser.username}}</div>
                <div class="user__avatar">
                    <img src="./assets/avatar.jpg" alt="User" class="user__avatar-img">
                </div>
            </div>
        </header>
        <statusList v-if="selectedBoardID" :statusList="statusList" :taskList="taskList"></statusList>
        <taskMenu v-if="showTaskMenu" :taskItem="clickedTask" :statusList="statusList" @wrapperClick="showTaskMenu = !showTaskMenu"></taskMenu>
        <boardListMenu v-if="!selectedBoardID&&!showNewBoardMenu"></boardListMenu>
        <newBoardMenu v-if="!selectedBoardID&&showNewBoardMenu" :currentUser="currentUser"></newBoardMenu>
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
            currentBoard:"",
            boardList:"",
            statusList:"",
            taskList:"",
            selectedBoardID:"",
            showNewBoardMenu: false
        }
    },
    beforeCreate(){
        var self = this;
        if(getCookie("access_token")){
            axios.get("http://localhost:8080/api/getUserId?access_token=" + getCookie("access_token"))
                .then(function(response){
                    axios.get("http://localhost:8080/api/users/"+response.data).then(function(response){
                        self.currentUser = response.data;
                        axios.get("http://localhost:8080/api/users/"+self.currentUser.id+"/boards")
                            .then(function(response){
                            if(response.data.length == 0){
                                self.showNewBoardMenu = true;
                            }else{
                                self.boardList = response.data;
                            }
                        })
                    })
                })
                .catch(function(error){
                    delete_cookie("access_token");
                    alert(error);
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
        this.$root.$on('onBoardSelect', function(board){
            self.selectedBoardID = board.id;
            set_cookie("current_board", board.id);
        })
        this.$root.$on('updateBoard',function(){
            self.updateBoard();
        })
        if(getCookie("current_board")){
                this.selectedBoardID = getCookie("current_board");
            }
    },
    watch:{
        selectedBoardID : function(){
            this.updateBoard();
        },
        showNewBoardMenu: function(value){
            if(value == "false"){
                document.location.replace("/board");
            }
        }
    },
    methods:{
        logOut(){
            axios.get("http://localhost:8080/api/logouts?access_token="+getCookie("access_token"))
                .then(function(response){
                    delete_cookie("access_token");
                    delete_cookie("current_board");
                    document.location.replace("/auth");
                })
        },
        updateBoard(){
            var self = this;
            if (this.selectedBoardID){
                axios.get("http://localhost:8080/api/boards/"+self.selectedBoardID).then(function(response){
                    self.currentBoard = response.data;
                }).catch(function(error){
                    alert(error);
                }).then(function(){
                    axios.get("http://localhost:8080/api/boards/"+self.currentBoard.id+"/statuses").then(function(response){
                        self.statusList = response.data;
                        console.log(self.statusList);
                    }).catch(function(error){
                        alert(error);
                    })
                }).then(function(){
                    axios.get("http://localhost:8080/api/boards/"+self.currentBoard.id+"/tasks").then(function(response){
                        self.taskList = response.data;
                    }).catch(function(error){
                        alert(error);
                    })
                })
            }
        }
    }
}
</script>

<style lang="scss">
    .menu__wrapper{
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(#000,.5);
        z-index: 0;
    }
    .menu__body{
        text-align: center;
        position: absolute;
        z-index: 100;
        padding: 10px 30px;
        top: 50px;
        left: 20%;
        right: 20%;
        max-height: 560px;
        width: 800px;
        overflow-y: auto;
        border: 2px solid black;
        background-color: #cccccc;
    }
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
