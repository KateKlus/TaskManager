<template>
    <div class="wrapper__form">
        <form action="" class="form">
            <div class="form__avatar">
                <img src="./assets/avatar.jpg" alt="" class="form__avatar-img">
            </div>
            <label class="form__label">
                <div class="form__name">Логин</div>
                <input type="text" class="form__input" v-model="user.login" required>
            </label>
              <label class="form__label">
                <div class="form__name">Пароль</div>
                <input type="password" class="form__input" v-model="user.password" required>
            </label>
            <div class="form__links">
                <a href="" class="link__forgot">Забыли пароль?</a>
                <a href="/registration" class="link__registration">Регистрация</a>
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
            document.location.replace("/board");
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
                url: 'http://'+host+':'+port+'/oauth/token',
                auth: {username: 'my-trusted-client', password: 'secret'},
                headers: {"Content-type": "application/x-www-form-urlencoded; charset=utf-8"},
                data:params
            }).then(function (response) {
                set_cookie("access_token", response.data.access_token);
                document.location.replace("/board");
            }).catch(function (error) {
                self.show = !self.show;
                setTimeout(function(){
                    self.show = !self.show;
                },2000);
            });
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
    .company{
        position: absolute;
        width: 300px;
        font-size: 60px;
        top: 2%;
        left: 50%;
        margin-left: -150px;

    }
    .form{
        vertical-align: middle;
        margin-top: -50px;
        display: inline-block;
        border: 2px solid black;
        border-radius: 10px;
        padding: 10px 30px;
        max-width: 350px;
        text-align: center;
        background-color: #cccccc;
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
        background: rgba(#222, .2);
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
</style>
