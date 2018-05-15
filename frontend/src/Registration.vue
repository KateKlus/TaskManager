<template>
    <div class="wrapper__form">
        <form action="" class="form clearfix">
           <div class="form__title">Регистрация</div>
            <label class="form__label">
                <input type="text" class="form__input" v-model="user.fullname" placeholder="Ваше имя?" required>
            </label>
              <label class="form__label">
                <input type="text" class="form__input" v-model="user.username" placeholder="Придумайте логин" required>
            </label>
            <label class="form__label">
                <input type="password" class="form__input pass" v-model="user.password" placeholder="Придумайте пароль" required>
            </label>
            <label class="form__label">
                <input type="password" class="form__input pass" v-model="user.passwordConfirm" placeholder="Подтвердите пароль" required>
            </label>
            <label class="form__label">
                <input type="email" class="form__input" v-model="user.email" placeholder="Ваш e-mail" required>
            </label>
            <button class="form__submit" type="submit" @click.prevent="register">Зарегистрироваться</button>
            <button @click.prevent="toLogin" class="form__submit">Назад</button>
        </form>
        <transition name="fade">
            <div v-bind:class="messageClass" v-if="showMessage">{{messageContent}}</div>
        </transition>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            user:{
                username:'',
                fullname:'',
                password:'',
                passwordConfirm:'',
                email:''
            },
            showMessage: false,
            messageContent:'',
            messageClass:''
        }
    },
    methods:{
        showDialogMessage(content, type){
            var self = this;
            this.messageContent= content;
            this.messageClass = type;
            this.showMessage = !this.showMessage;
            setTimeout(function(){
                self.showMessage = !self.showMessage;
            },2000);
        },
        register(){
            var self = this;
            if(this.user.password == this.user.passwordConfirm){
                axios({
                    method:'post',
                    url:host+'/api/register/',
                    data:{
                        username: this.user.username,
                        fullname: this.user.fullname,
                        password: this.user.password,
                        passwordConfirmation: this.user.passwordConfirm,
                        email: this.user.email
                    }
                }).then(function(response){

                    if(response.data == "User created"){
                        self.showDialogMessage('Пользователь успешно создан!','showMessage');
                        setTimeout(function(){
                            window.location = "#/board";
                        },2500);

                    }
                }).catch(function(error){
                    if(error.response.status == 500){
                        self.showDialogMessage('Такой email уже занят, пожалуйста, введите другой!','showError')
                    }
                    if(error.response.status == 406){
                        self.showDialogMessage('Проверьте правильность введённых данных!','showError')
                    }
                })
            }else{
                self.showDialogMessage('Введённые пароли не совпадают!','showError')
            }

        },
        toLogin(){
            window.location = "#/auth";
        }
    }
}
</script>

<style lang="scss" scoped>
    .wrapper__form{
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        text-align: center;
        background: #eaeaea;
        &:before{
            content: "";
            display: inline-block;
            height: 100%;
            width: 0;
            vertical-align: middle;
        }
    }
    .form{
        vertical-align: middle;
        display: inline-block;
        text-align: center;
    }
    .showError{
        position: absolute;
        top: 40%;
        left: 50%;
        width: 600px;
        padding: 40px;
        text-align: center;
        margin-left: -300px;
        background: rgba(#f54172, .8);
        box-shadow: 0, 1px, 5px, rgba(0,0,0,0.25);
            border: 1px solid black;
        border-radius: 20px;
    }
    .showMessage{
        position: absolute;
        top: 40%;
        left: 50%;
        width: 600px;
        padding: 40px;
        text-align: center;
        margin-left: -300px;
        background: rgba(#58a9c7, .8);
        box-shadow: 0, 1px, 5px, rgba(0,0,0,0.25);
            border: 1px solid black;
        border-radius: 20px;
    }
    .fade-enter,.fade-leave-to{
        opacity: 0;
        transition: 1s;
    }
    .fade-enter-to,.fade-leave{
        opacity: 1;
        transition: 1s;
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
