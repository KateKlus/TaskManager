<template>
    <div class="wrapper">
        <transition name='fade'>
          <div v-bind:class="messageClass" v-if="showMessage">{{messageContent}}</div>
        </transition>
        <header v-if="selectedBoardID" id="board__header" class="board__header">
            <leftMenu :currentUser="currentUser" :statusList="statusList" :templateList="templateList" :currentBoard="currentBoard"></leftMenu>
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
            currentRole:"",
            roleList:"",
            currentBoard:"",
            boardList:"",
            statusList:"",
            taskList:"",
            templateList:"",
            selectedBoardID:"",
            showNewBoardMenu: false,
            messageContent: '',
            messageClass: '',
            showMessage:false,

        }
    },
    beforeCreate(){
        var self = this;
        if(getCookie("access_token")){
            axios.get(host+'/api/getUserId/?access_token='+getCookie("access_token"))
                .then(function(response){
                    axios.get(host+'/api/users/'+response.data+'/?access_token='+getCookie("access_token")).then(function(response){
                        self.currentUser = response.data;
                        axios.get(host+'/api/users/'+self.currentUser.id+'/boards/?access_token='+getCookie("access_token"))
                            .then(function(response){
                            if(response.data.length == 0){
                                self.showNewBoardMenu = true;
                            }else{
                                self.boardList = response.data;
                            }
                        })
                    })
                }).catch(function(error){
                    delete_cookie("access_token");
                    delete_cookie("current_board");
                    window.location = "#/auth";
                });
        }
        else{
            window.location = "#/auth";
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
        this.$root.$on('showDialog', function(content, type){
            self.showDialogMessage(content,type);
        })
        this.$root.$on('permissionStatus',(element,done) =>{
            if(self.roleList){
                if(self.currentRole){
                    self.checkPermissions(self.currentRole, element, done);
                }
            }else{
                axios.get(host+'/api/getUserId/?access_token='+getCookie("access_token")).then(function(response){
                    axios.get(host+'/api/users/'+response.data+'/members/?access_token='+getCookie("access_token"))
                        .then(function(response){
                            self.roleList = response.data;
                            for(var i=0; i<response.data.length; i++){
                                if(self.currentBoard.id == response.data[i].board.id){
                                    self.currentRole = response.data[i].role;
                                    self.checkPermissions(self.currentRole, element, done);
                                }
                            }
                        }).catch(function(error){
                            if(error.request.status != 401){
                                showDialogMessage(error,'showError');
                            }
                        })
                })
            }

        });
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
                window.location = "#/board";
            }
        }
    },
    methods:{
        logOut(){
            axios.get(host+'/api/logouts/?access_token='+getCookie("access_token"))
                .then(function(response){
                    delete_cookie("access_token");
                    delete_cookie("current_board");
                    window.location = "#/auth";
                })
        },
        updateBoard(){
            var self = this;
            if (this.selectedBoardID){
                axios.get(host+'/api/boards/'+self.selectedBoardID+'/?access_token='+getCookie("access_token")).then(function(response){
                    self.currentBoard = response.data;
                }).catch(function(error){
                    if(error.request.status != 401){
                        showDialogMessage(error.response.data.error+"; "+error.response.data.message, 'showError');
                    }
                }).then(function(){
                    axios.get(host+'/api/boards/'+self.currentBoard.id+'/statuses/?access_token='+getCookie("access_token")).then(function(response){
                        self.statusList = response.data;
                    }).catch(function(error){
                        if(error.request.status != 401){
                            showDialogMessage(error.response.data.error+"; "+error.response.data.message, 'showError');
                        }
                    })
                }).then(function(){
                    axios.get(host+'/api/boards/'+self.currentBoard.id+'/tasks/?access_token='+getCookie("access_token")).then(function(response){
                        self.taskList = response.data;
                    }).catch(function(error){
                        if(error.request.status != 401){
                            showDialogMessage(error.response.data.error+"; "+error.response.data.message, 'showError');
                        }
                    })
                }).then(function(){
                    axios.get(host+'/api/tasktemplates/?access_token='+getCookie("access_token")).then(function(response){
                        self.templateList = response.data;
                    }).catch(function(error){
                        if(error.request.status != 401){
                            showDialogMessage(error.response.data.error+"; "+error.response.data.message, 'showError');
                        }
                    })
                })
            }
        },
        checkPermissions(currentRole, element, done){
            if(currentRole == 'OWNER'){
                return done(true);
            }
            if(currentRole == 'ADMIN'){
                if (element == 'deleteBoard'){
                    return done(false);
                }
                return done(true);
            }
            if(currentRole == 'MODERATOR'){
                if (element == 'deleteBoard'){
                    return done(false);
                }
                if(element == 'inviteAndEdit'){
                    return done(false);
                }
                return done(true);
            }
            if(currentRole == 'DEVELOPER'){
                if (element == 'deleteBoard'){
                    return done(false);
                }
                if(element == 'inviteAndEdit'){
                    return done(false);
                }
                if(element == 'templates'){
                    return done(false);
                }
                if(element == 'deleteTask'){
                    return done(false);
                }
                return done(true);
            }
            if(currentRole == 'VIEWER'){
                if (element == 'deleteBoard'){
                    return done(false);
                }
                if(element == 'inviteAndEdit'){
                    return done(false);
                }
                if(element == 'templates'){
                    return done(false);
                }
                if(element == 'deleteTask'){
                    return done(false);
                }
                if(element == 'editTasks'){
                    return done(false);
                }
                if(element == 'editBoard'){
                    return done(false);
                }
                if(element == 'editTask'){
                    return done(false);
                }
                return done(true);
            }
        },
        showDialogMessage(content, type){
            var self = this;
            this.messageContent= content;
            this.messageClass = type;
            this.showMessage = !this.showMessage;
            setTimeout(function(){
                self.showMessage = !self.showMessage;
            },2000);
        }
    }
}
</script>

<style lang="scss">
    body{
        background-color: #58c791;
    }
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
    .showError{
        position: absolute;
        top: 40%;
        left: 50%;
        width: 600px;
        padding: 40px;
        margin-left: -300px;
        background: rgba(#f54172, .8);
        box-shadow: 0, 1px, 5px, rgba(0,0,0,0.25);
            border: 1px solid black;
        border-radius: 20px;
        z-index: 9999;
        text-align: center;
    }
    .showMessage{
        position: absolute;
        top: 40%;
        left: 50%;
        width: 600px;
        padding: 40px;
        margin-left: -300px;
        background: rgba(#58a9c7, .8);
        box-shadow: 0, 1px, 5px, rgba(0,0,0,0.25);
            border: 1px solid black;
        border-radius: 20px;
        z-index: 9999;
        text-align: center;
    }
    .fade-enter,.fade-leave-to{
        opacity: 0;
        transition: 1s;
    }
    .fade-enter-to,.fade-leave{
        opacity: 1;
        transition: .5s;
    }
    $fonts: "Open Sans", Helvetica, sans-serif;

    @mixin border-radius($radius) {
        -webkit-border-radius: $radius;
        -moz-border-radius: $radius;
        -ms-border-radius: $radius;
        border-radius: $radius;
    }

    @mixin box-shadow($top, $left, $blur, $color) {
        -webkit-box-shadow: $top $left $blur $color;
        -moz-box-shadow: $top $left $blur $color;
        box-shadow: $top $left $blur $color;
    }

    ::selection {
        background-color: #b5e2e7;
    }

    ::-moz-selection {
        background-color: #8ac7d8;
    }
    .company{
        display: inline-block;
        font-size: 40px;
        margin-top: -50px;
        background: white;
        width: 320px;
        background-color: #fff;
        @include border-radius(20px);
        @include box-shadow(0, 1px, 5px, rgba(0,0,0,0.25));
        color: #757575;

    }

    .wrapper__form {
        background: #58c791;
    }
    .form {
        margin: 50px auto;
        width: 340px;
        background-color: #fff;
        @include border-radius(4px);
        @include box-shadow(0, 1px, 5px, rgba(0,0,0,0.25));
    }

    .form__title {
        margin-top: 20px;
        text-align: center;
        font-size: 175%;
        color: #757575;
        font-weight: 300;
    }

    .form__title, input {
        font-family: $fonts;
    }

    .form__input {
        width: 75%;
        height: 50px;
        display: block;
        margin: 0 auto 15px;
        padding: 0 15px;
        border: none;
        border-bottom: 2px solid #ebebeb;
        transition: border-bottom .5s;
        &:focus {
            outline: none;
            border-bottom-color: #58c791 !important;
        }
        &:hover {
            border-bottom-color: #cbcbcb;
        }
        &:invalid {
            box-shadow: none;
        }
    }

    .pass:-webkit-autofill {
        -webkit-box-shadow: 0 0 0 1000px white inset;
    }

    .form__submit {
        position: relative;
        width: 85%;
        height: 50px;
        display: block;
        margin: 30px auto 30px;
        @include border-radius(5px);
        color: white;
        background-color: #58c791;
        border: none;
        @include box-shadow(0, 5px, 0, #3aad73);
        &:hover {
            top: 2px;
            @include box-shadow(0, 3px, 0, #3aad73);
        }
        &:active {
            top: 5px;
            box-shadow: none;
        }
        &:focus {
            outline: none;
        }
    }


</style>
