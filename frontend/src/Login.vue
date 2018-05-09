<template>
    <div class="wrapper__form">
        <form action="" class="form">
           <div class="company">Compito</div>
            <div class="form__avatar">
                <img src="./assets/avatar.png" alt="" class="form__avatar-img">
            </div>
            <label class="form__label">
                <input type="text" class="form__input" v-model="user.login" placeholder="Ваш логин" required>
            </label>
              <label class="form__label">
                <input type="password" class="form__input" v-model="user.password" placeholder="Ваш пароль" required>
            </label>
            <div class="form__links">
                <a href="" class="link__forgot">Забыли пароль?</a>
                <a href="" class="link__registration" @click.prevent="toRegistration">Регистрация</a>
            </div>
            <button class="form__submit" type="submit" @click.prevent="login">Войти</button>
        </form>
        <transition name="fade">
            <div class="form__error" v-if="show">Ошибка! Вы ввели неверные данные.</div>
        </transition>

    </div>
</template>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            user:{
                login:"",
                password:""
            },
            show: false
        }
    },
    mounted(){
        if(getCookie("access_token")){
            window.location = "#/board";
        }
    },
    methods: {
        login(){
            var self = this;
            var params = new URLSearchParams();
            params.append('grant_type', 'password');
            params.append('username', this.user.login);
            params.append('password', this.user.password);
            axios({
                method: 'post',
                url: host+'/oauth/token',
                auth: {username: 'my-trusted-client', password: 'secret'},
                headers: {"Content-type": "application/x-www-form-urlencoded; charset=utf-8"},
                data:params
            }).then(function (response) {
                set_cookie("access_token", response.data.access_token);
                window.location = "#/board";
            }).catch(function (error) {
                self.show = !self.show;
                setTimeout(function(){
                    self.show = !self.show;
                },2000);
            });
        },
        toRegistration(){
            window.location = "#/registration";
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
        margin-top: -50px;
        display: inline-block;
        max-width: 350px;
        text-align: center;
    }
    .form__avatar{
        display: inline-block;
        max-width: 100px;
        margin: 10px;
        .form__avatar-img{
            width: 100%;
            border-radius: 50%;
        }
    }
    .form__label{
        display: block;
        text-align: center;
        margin-bottom: 10px;
    }
    .form__input{
        min-width: 250px;
    }
    .form__links{
        font-size: 12px;
        margin-bottom: 10px;
        a{
            text-decoration: none;
            color: grey;
            &:hover{
                color:black;
            }
        }
    }
    .link__forgot{
        margin-right: 70px;
    }
        .form__submit{
        min-width: 250px;
            margin-bottom: 20px;
    }
    .form__error{
        position: fixed;
        top: 15px;
        left: 50%;
        width: 600px;
        height: 100px;
        padding: 40px;
        margin-left: -300px;
        background: rgba(#ffffff, .7);
        box-shadow: 0, 1px, 5px, rgba(0,0,0,0.25);
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
