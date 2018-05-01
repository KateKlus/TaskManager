<template>
    <div class="userListMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="popup__title">Приглашенные пользователи</div>
            <label for="" class="popup__label">
                <div class="popup__text">Выберите пользователя:</div>
                <select name="userList" v-model="selectedMember" size="10">
                   <option value="" selected disabled>Выберите пользователя</option>
                    <option v-for="member in memberList" v-bind:value="member">{{member.user.username}} [{{member.role.roleName}}]</option>
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
                <button @click="deleteRole" class="popup__submit">Удалить пользователя из доски</button>
            </label>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            selectedMember:"",
            selectedRole:"",
            roleList:"",
            memberList:[]
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
                self.memberList = [];
                response.data.forEach(function(member){
                    if(member.board.id == self.currentBoard.id){
                        if(member.user.id != self.currentBoard.boardOwner.id){
                            self.memberList.push(member);
                        }
                    }
                })
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
            if(this.selectedMember){
                if(this.selectedRole){
                    this.selectedMember.role = this.selectedRole;
                    axios({
                        method: 'put',
                        url: 'http://'+host+':'+port+'/api/members/?access_token='+getCookie("access_token"),
                        data: self.selectedMember
                    }).then(function (response) {
                        alert("Роль пользователя "+self.selectedMember.user.username+" успешно изменена на "+self.selectedRole.roleName);
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
        deleteRole(){
            var self = this;
            axios({
                method: 'DELETE',
                url: 'http://'+host+':'+port+'/api/users/'+self.selectedMember.user.id+'/boards/'+self.currentBoard.id+'/?access_token='+getCookie("access_token")
            }).then(function (response) {
                alert("Пользователь успешно удалён из доски!")
                self.$emit('wrapperClick');
                self.$root.$emit('updateBoard');
            }).catch(function (error) {
                alert("Error! "+ error);
            });
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
