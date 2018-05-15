<template>
    <div class="userMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="popup__title">Меню пользователя {{currentUser.username}}, id={{currentUser.id}}</div>
            <label for="" class="popup__label">
                <div class="popup__text">Изменить имя:</div>
                <input type="text" class="popup__input" v-model="currentUser.fullname">
            </label>
            <label for="" class="popup__label">
                <div class="popup__text">Изменить e-mail:</div>
                <input type="text" class="popup__input" v-model="currentUser.email">
            </label>
            <button class="popup__submit" @click.prevent="saveUser">Сохранить изменения</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    props:['currentUser'],
    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        },
        saveUser(){
            var self = this;
            axios({
                method: 'put',
                url: host+'/api/users/'+self.currentUser.id+'/?access_token='+getCookie("access_token"),
                data: self.currentUser
            }).then(function (response) {
                self.$root.$emit('updateBoard');
                self.$emit('wrapperClick');
                self.$root.$emit('showDialog',"Данные успешно изменены!",'showMessage');
            }).catch(function (error) {
                self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
            });
        },
    }
}
</script>
