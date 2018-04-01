<template>
    <div class="wrapper">
        <header v-if="selectedBoardID" id="board__header" class="board__header">
            <leftMenu :currentUser="currentUser" :statusList="statusList" :templateList="templateList"></leftMenu>
            <a href="" class="board__title" @click.prevent="showBoardMenu = !showBoardMenu">{{currentBoard.boardName}}</a>
            <div class="board__user">
                <a href="" class="user__logout" @click.prevent="logOut">выйти</a>
                <a href="" class="user__name" @click.prevent="showUserMenu = !showUserMenu">{{currentUser.username}}</a>
                <div class="user__avatar">
                    <img src="./assets/avatar.jpg" alt="User" class="user__avatar-img">
                </div>
            </div>
        </header>
        <statusList v-if="selectedBoardID" :statusList="statusList" :taskList="taskList"></statusList>
        <taskMenu v-if="showTaskMenu" :taskItem="clickedTask" :statusList="statusList" @wrapperClick="showTaskMenu = !showTaskMenu"></taskMenu>
        <statusMenu v-if="showStatusMenu" :statusItem="clickedStatus" :taskList="taskList" @wrapperClick="showStatusMenu = !showStatusMenu"></statusMenu>
        <boardMenu v-if="showBoardMenu" :currentBoard="currentBoard" @wrapperClick="showBoardMenu = !showBoardMenu"></boardMenu>
        <userMenu v-if="showUserMenu" :currentUser="currentUser" @wrapperClick="showUserMenu = !showUserMenu"></userMenu>
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
            showBoardMenu: false,
            showStatusMenu: false,
            showUserMenu: false,
            clickedTask:"",
            clickedStatus:"",
            currentUser:"",
            currentBoard:"",
            boardList:"",
            statusList:"",
            taskList:"",
            templateList:"",
            selectedBoardID:"",
            showNewBoardMenu: false
        }
    },
    beforeCreate(){
        var self = this;
        if(getCookie("access_token")){
            axios.get('http://'+host+':'+port+'/api/getUserId/?access_token=' + getCookie("access_token"))
                .then(function(response){
                    axios.get('http://'+host+':'+port+'/api/users/'+response.data+'/').then(function(response){
                        self.currentUser = response.data;
                        axios.get('http://'+host+':'+port+'/api/users/'+self.currentUser.id+"/boards/")
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
        })
        this.$root.$on('clickOnStatusName', function(statusItem){
            self.clickedStatus = statusItem;
            self.showStatusMenu = !self.showStatusMenu;
        })
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
            axios.get('http://'+host+':'+port+'/api/logouts/?access_token='+getCookie("access_token"))
                .then(function(response){
                    delete_cookie("access_token");
                    delete_cookie("current_board");
                    document.location.replace("/auth");
                })
        },
        updateBoard(){
            var self = this;
            if (this.selectedBoardID){
                axios.get('http://'+host+':'+port+'/api/boards/'+self.selectedBoardID+'/').then(function(response){
                    self.currentBoard = response.data;
                }).catch(function(error){
                    alert(error);
                }).then(function(){
                    axios.get('http://'+host+':'+port+'/api/boards/'+self.currentBoard.id+'/statuses/').then(function(response){
                        self.statusList = response.data;
                    }).catch(function(error){
                        alert(error);
                    })
                }).then(function(){
                    axios.get('http://'+host+':'+port+'/api/boards/'+self.currentBoard.id+'/tasks/').then(function(response){
                        self.taskList = response.data;
                    }).catch(function(error){
                        alert(error);
                    })
                }).then(function(){
                    axios.get('http://'+host+':'+port+'/api/tasktemplates/').then(function(response){
                        self.templateList = response.data;
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
    .popup__wrapper{
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(#000,.5);
        z-index: 0;
    }
    .popup__body{
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
    .popup__title{
        font-weight: bold;
        font-size: 18px;
        margin-bottom: 10px;
    }
    .popup__label{
        display: block;
    }
    .popup__text{

    }
    .popup__text-title{
        font-weight: bold;
    }
    .popup__button{
        display: inline-block;
        cursor: pointer;
        border: 1px solid black;
        min-width: 200px;
        margin: 10px;
        padding: 5px 10px;
        border-radius: 10px;
        background: #ebebeb;
        &:hover{
            background: #fff;
        }
    }
    .popup__submit{
        display: block;
        margin: 10px auto;
    }
    .board__title{
        text-decoration: none;
        color:black;
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
    .user__name{
        text-decoration: none;
        color:black;
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
