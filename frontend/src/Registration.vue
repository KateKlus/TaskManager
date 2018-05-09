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
            }
        }
    },
    methods:{
        register(){
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
                    alert(response.data);
                    if(response.data == "User created"){
                        window.location = "#/board";
                    }

                }).catch(function(error){
                    if(error.response.status == 500){
                        alert("Такой email уже занят!");
                    }
                    if(error.response.status == 406){
                        alert("Проверьте правильность введенных данных!");
                    }
                })
            }else{
                alert("Введённые пароли не совпадают!");
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
            border-bottom-color: #dcdcdc;
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
