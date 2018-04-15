<template>
    <div class="userListMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="popup__title">Список пользователей</div>
            <ul class="userListMenu__list">
                <li class="userListMenu__item" v-for="user in userList">
                    <a href="" class="userListMenu__link" @click.prevent="selectUser(user)">{{user.username}}</a>
                </li>
            </ul>

        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            userList: "",
            invitedUserList: "",
            currentUser:"",
            currentBoardUsers:"",
        }
    },

    beforeCreate(){
        var self = this;
        if(getCookie("access_token")){
            axios.get('http://'+host+':'+port+'/api/boards/'+getCookie("current_board")+'/users/')
                .then(function(response){
                    self.currentBoardUsers = response.data;
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

    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        },
        selectUser(user){
            alert("Пользователь успешно приглашен");
            this.$root.$emit('onUserSelect', user);
            this.$emit('wrapperClick');
        }
    },
    created(){
        var self = this;
        axios.get('http://'+host+':'+port+'/api/users/?access_token=' + getCookie("access_token")).then(function(response){
                self.userList = response.data;
        });
    }
}
</script>

<style lang="scss" scoped>
    .userListMenu__item{
        margin: 5px;
    }
    .userListMenu__link{
        display: inline-block;
        width: 100%;
        padding: 5px;
        text-decoration: none;
        color: black;
        border: 1px solid black;
        border-radius: 10px;
        background: #ebebeb;
        &:hover{
            background: #ffffff;
        }
    }

    .popup__body{
        min-height: 290px;
    }

</style>
