<template>
    <div class="userListMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
           <div class="popup__title">Пригласить пользователя</div>
            <label for="" class="popup__label">
                <div class="popup__text">Выберите пользователя:</div>
                <select name="userList" v-model="selectedUser" size="10" required="">
                   <option value="" selected disabled>Выберите пользователя</option>
                    <option v-for="user in userList" v-bind:value="user">{{user.username}}</option>
                </select>
            </label>
            <label for="" class="popup__label">
                <div class="popup__text">Выберите роль:</div>
                <select name="roleList" v-model="selectedRole" required="">
                   <option value="" selected disabled>Выберите роль</option>
                    <option v-for="role in roleList" v-bind:value="role">{{role.roleName}}</option>
                </select>
            </label>
            <label for="" class="popup__label">
                <button @click="sendInvite" class="popup__submit">Пригласить</button>
            </label>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            userList:"",
            inviteUser:{
                role:"",
                user:"",
                board:""
            },
            selectedUser:"",
            selectedRole:"",
            roleList:"",
            invitedUserList:[]
        }
    },
    props:['currentBoard'],
    created(){
        var self = this;
        axios.get('http://'+host+':'+port+'/api/roles/?access_token='+getCookie("access_token"))
            .then(function(response){
                self.roleList = response.data;
            })
            .catch(function(error){
                alert(error);
            });
        axios.get('http://'+host+':'+port+'/api/members/?access_token='+getCookie("access_token"))
            .then(function(response){
                self.invitedUserList = [];
                response.data.forEach(function(member){
                    if(member.board.id == self.currentBoard.id){
                        self.invitedUserList.push(member.user);
                    }
                })
            })
            .catch(function(error){
                alert(error);
            }).then(function(){
                axios.get('http://'+host+':'+port+'/api/users/?access_token='+getCookie("access_token")).then(function(response){
                self.userList = response.data;
                self.clearUserList();
            })
            .catch(function(error){
                alert(error);
            })
        })
    },

    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        },
        sendInvite(){
            var self = this;
            if(this.selectedUser){
                if(this.selectedRole){
                    this.inviteUser.role = this.selectedRole;
                    this.inviteUser.user = this.selectedUser;
                    this.inviteUser.board = this.currentBoard;
                    axios({
                        method: 'post',
                        url: 'http://'+host+':'+port+'/api/members/?access_token='+getCookie("access_token"),
                        data:self.inviteUser
                    }).then(function (response) {
                        alert("Пользователь "+self.selectedUser.username+" успешно приглашен c ролью "+self.selectedRole.roleName);
                        self.$emit('wrapperClick');
                    }).catch(function (error) {
                        alert("Error! "+ error)
                    });
                }
                else{
                    alert("Выберите роль для пользователя!");
                }
            }
            else{
                alert("Выберите пользователя!");
            }

        },
        clearUserList(){
            var self = this;
            var count = this.userList.length;
            for(var i = 0; i < count; i++){
                self.invitedUserList.forEach(function(invitedUser){
                    if(self.userList[i].id == invitedUser.id){
                        self.userList.splice(i,1);
                        count -= 1;
                    }
                })
            }
        }
    },
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
        min-height: 330px;
    }

</style>
