<template>
    <div class="userListMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="popup__title">Приглашенные пользователи</div>
            <label for="" class="popup__label">
                <div class="popup__text">Выберите пользователя:</div>
                <select name="userList" v-model="selectedUser" size="10">
                   <option value="" selected disabled>Выберите пользователя</option>
                    <option v-for="user in userList" v-bind:value="user">{{user.username}}</option>
                </select>
            </label>
            <label for="" class="popup__label">
                <div class="popup__text">Выберите роль:</div>
                <select name="roleList" v-model="selectedRole">
                   <option value="" selected disabled>Выберите роль</option>
                    <option v-for="role in roleList" v-bind:value="role">{{role.roleName}}</option>
                </select>
            </label>
            <label for="" class="popup__label">
                <button @click="editRole" class="popup__submit">Изменить роль</button>
            </label>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            userList: "",
            selectedUser:"",
            selectedRole:"",
            roleList:""
        }
    },
    props:['currentBoard'],
    created(){
        var self = this;
        axios.get('http://'+host+':'+port+'/api/boards/'+self.currentBoard.id+'/users/')
            .then(function(response){
                self.userList = response.data;
            })
            .catch(function(error){
                alert(error);
            });
        axios.get('http://'+host+':'+port+'/api/roles/')
            .then(function(response){
                self.roleList = response.data;
            })
            .catch(function(error){
                alert(error);
            });

    },

    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        },
        editRole(){
            var self = this;
            if(this.selectedUser){
                if(this.selectedRole){
                    this.selectedRole.board = this.currentBoard;
                    this.selectedRole.user = this.selectedUser;
                    this.roleList.forEach(function(role){
                        if((role.user.id == self.selectedUser.id)&&(role.board.id == self.currentBoard.id)){
                            axios({
                                method: 'put',
                                url: 'http://'+host+':'+port+'/api/roles/'+role.id+'/?access_token='+getCookie("access_token"),
                                data:self.selectedRole
                            }).then(function (response) {
                                alert("Роль пользователя "+self.selectedUser.username+" успешно изменена на "+self.selectedRole.roleName);
                                self.$emit('wrapperClick');
                            }).catch(function (error) {
                                alert("Error! "+ error)
                            });
                        }
                    })
                }
                else{
                    alert("Выберите роль для пользователя!");
                }
            }
            else{
                alert("Выберите пользователя!");
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
