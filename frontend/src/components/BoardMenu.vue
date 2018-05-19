<template>
    <div class="boardMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="popup__title">Меню управления доской</div>
            <label for="" class="popup__label">
                <div class="popup__text">Изменить имя доски:</div>
                <input type="text" class="popup__input" v-model="currentBoard.boardName">
            </label>
            <label for="" class="popup__label">
                <div class="popup__text">Изменить описание доски:</div>
                <input type="text" class="popup__input" v-model="currentBoard.description">
            </label>
                <label for="" class="popup__label">
                   <button class="popup__submit" @click.prevent="editBoard" v-if="showEditBoard">Изменить доску</button>
                   <button class="popup__submit delete" @click.prevent="deleteBoard" v-if="showDeleteBoard">Удалить доску</button>
                </label>
                <label for="" class="popup__label">
                   <button class="popup__submit" @click.prevent="showUserInviteMenu = !showUserInviteMenu" v-if="showInviteAndEdit">Пригласить пользователя</button>
                   <button class="popup__submit" @click.prevent="showInvitedUserMenu = !showInvitedUserMenu" v-if="showInviteAndEdit">Приглашенные пользователи</button>
                </label>
        </div>
        <transition-group name='fade' :duration="100">
            <userInviteMenu v-if="showUserInviteMenu" key="userInvite" @wrapperClick="showUserInviteMenu = !showUserInviteMenu" :currentBoard="currentBoard"></userInviteMenu>
            <invitedUserMenu v-if="showInvitedUserMenu" key="invitedUser" @wrapperClick="showInvitedUserMenu = !showInvitedUserMenu" :currentBoard="currentBoard"></invitedUserMenu>
        </transition-group>
    </div>

</template>
<script>

import axios from 'axios'
export default{
    data() {
        return {
            showUserInviteMenu: false,
            showInvitedUserMenu: false,
            showDeleteBoard: true,
            showInviteAndEdit: true,
            showEditBoard: true,
        }
    },
    created(){
        var self = this;
        this.$root.$emit('permissionStatus','deleteBoard', function(callback){
            self.showDeleteBoard = callback;
        });
        this.$root.$emit('permissionStatus','inviteAndEdit', function(callback){
            self.showInviteAndEdit = callback;
        });
        this.$root.$emit('permissionStatus','editBoard', function(callback){
            self.showEditBoard = callback;
        });
    },
    props:['currentBoard'],
    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        },
        editBoard(){
            var self = this;
            axios({
                method: 'put',
                url: host+'/api/boards/'+self.currentBoard.id+'/?access_token='+getCookie("access_token"),
                data: self.currentBoard
            }).then(function (response) {
                self.$root.$emit('updateBoard');
                self.$emit('wrapperClick');
                self.$root.$emit('showDialog','Доска успешно обновлена!','showMessage');
            }).catch(function (error) {
                self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
            });
        },
        deleteBoard(){
            var self = this;
            axios({
                method: 'DELETE',
                url: host+'/api/boards/'+self.currentBoard.id+'/?access_token='+getCookie("access_token")
            }).then(function (response) {
                self.$emit('wrapperClick');
                delete_cookie("current_board");
                window.location.reload();
            }).catch(function (error) {
                if(error.response.status == '500'){
                    self.$root.$emit('showDialog','Невозможно удалить доску, которая содержит данные!','showError');
                }
                else{
                    self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
                }
            });
        },
    }
}
</script>
