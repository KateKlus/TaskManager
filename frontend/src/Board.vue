<template>
    <div class="wrapper">
        <transition name='fade'>
          <div v-bind:class="messageClass" v-if="showMessage">{{messageContent}}</div>
        </transition>
        <header v-if="selectedBoardID" id="board__header" class="board__header">
            <leftMenu :currentUser="currentUser" :statusList="statusList" :templateList="templateList" :currentBoard="currentBoard"></leftMenu>
            <a href="" class="board__title" @click.prevent="showBoardMenu = !showBoardMenu">{{currentBoard.boardName}}</a>
            <div class="board__user">
                <a href="" class="user__logout" @click.prevent="logOut">Выйти</a>
                <a href="" class="user__name" @click.prevent="showUserMenu = !showUserMenu">{{currentUser.username}}</a>
                <div class="user__avatar">
                    <img src="./assets/avatar.png" alt="User" class="user__avatar-img">
                </div>
            </div>
        </header>
        <statusList v-if="selectedBoardID" key="statusList" :statusList="statusList" :taskList="taskList"></statusList>
        <transition-group name='fade'>
            <taskMenu v-if="showTaskMenu" key="taskMenu" :taskItem="clickedTask" :statusList="statusList" @wrapperClick="showTaskMenu = !showTaskMenu"></taskMenu>
            <statusMenu v-if="showStatusMenu" key="statusMenu" :statusItem="clickedStatus" :taskList="taskList" @wrapperClick="showStatusMenu = !showStatusMenu"></statusMenu>
            <boardMenu v-if="showBoardMenu" key="boardMenu" :currentBoard="currentBoard" @wrapperClick="showBoardMenu = !showBoardMenu"></boardMenu>
            <userMenu v-if="showUserMenu" key="userMenu" :currentUser="currentUser" @wrapperClick="showUserMenu = !showUserMenu"></userMenu>
            <boardListMenu v-if="!selectedBoardID&&!showNewBoardMenu" key="boardListMenu"></boardListMenu>
            <newBoardMenu v-if="!selectedBoardID&&showNewBoardMenu" key="newBoardMenu" :currentUser="currentUser"></newBoardMenu>
        </transition-group>

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
    body{
        background-color: #58c791;
    }
    .popup__wrapper{
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: rgba(#000,.5);
        z-index: 0;
        min-width:800px;
    }
    .popup__body{
        text-align: center;
        position: fixed;
        z-index: 100;
        padding: 10px 30px;
        top: 30px;
        left: 50%;
        margin-left: -400px;
        max-height: 600px;
        width: 800px;
        overflow-y: auto;
        background-color: #f1f1f1;
        @include border-radius(4px);
        @include box-shadow(1px, 1px, 5px, black);
    }
    .popup__executor{
        font-weight: bold;
        font-size: 24px;
        margin-bottom: 10px;
        text-align: center;
        color: #757575;
    }
    .popup__title{
        font-weight: bold;
        font-size: 24px;
        margin-bottom: 10px;
        text-align: center;
        color: #757575;
    }
    .popup__label{
        display: block;
    }
    .popup__text{
        color: #757575;
        font-weight: 300;
        margin: 5px 0;
    }
    .popup__input, .popup__area,.popup__select {
        height: 40px;
        display: block;
        margin: 0 auto 5px;
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
    .popup__area{
        width: 400px;
        min-height: 100px;
        resize: none;
    }
    .popup__select-invite{
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

    .popup__text-title{
        text-align: center;
        font-size: 22px;
        color: #757575;
        font-weight: 300;
    }
    .popup__button{
        display: inline-block;
        padding: 7px;
        text-decoration: none;
        color: #757575;
        border-radius: 5px;
        background: #fff;
        margin-bottom: 7px;
        box-shadow: 1px 1px 4px rgba(black, .5);
        transition: box-shadow .3s;
        &:hover{
            box-shadow: none;
        }
    }
    .popup__submit{
        display: inline-block;
        margin: 10px auto;
        position: relative;
        min-width: 120px;
        height: 40px;
        margin: 10px auto 10px;
        @include border-radius(5px);
        color: white;
        background-color: #58c791;
        border: none;
        @include box-shadow(0, 5px, 0, #3aad73);
        &.delete{
            background-color: #c75858;
            @include box-shadow(0, 5px, 0, #b12d2d);
            &:hover{
                @include box-shadow(0, 3px, 0, #b12d2d);
            }
        }
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
    .board__title, .user__name, .user__logout,.status__name{
        font-family: $fonts;
        text-decoration: none;
        color:#757575;
        padding: 5px 15px;
        border-radius: 7px;
        font-weight: 400;
        font-size: 22px;
        transition: box-shadow .5s;
        box-shadow: 0px 1px 5px rgba(0,0,0,0.25);
        background-color: #fff;
        &:hover{
            box-shadow: none;
        }
    }
    .user__name,.user__logout{
        display: inline-block;
        font-size: 18px;
        font-weight: bold;
        vertical-align: middle;
        margin-right: 5px;
    }
    .board__header{
        display: flex;
        justify-content: space-between;
        padding: 8px;
        align-items: center;
        background: #f1f1f1;
        border-bottom: 2px dashed #757575;
        min-width: 400px;
        position: fixed;
        width: 100%;
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
    .board__button-img{
        opacity: .6;
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
        transition: .2s;
    }
    .fade-enter-to,.fade-leave{
        opacity: 1;
        transition: .5s;
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


</style>
