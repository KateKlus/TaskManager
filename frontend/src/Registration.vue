<template>
    <div class="wrapper__form">
        <div class="company">Compito</div>
        <form action="" class="form clearfix">
           <div class="form__title">Регистрация</div>
            <label class="form__label">
                <div class="form__name">Введите имя:</div>
                <input type="text" class="form__input" v-model="user.fullname" required>
            </label>
              <label class="form__label">
                <div class="form__name">Введите логин:</div>
                <input type="text" class="form__input" v-model="user.username" required>
            </label>
            <label class="form__label">
                <div class="form__name">Введите пароль:</div>
                <input type="password" class="form__input" v-model="user.password" required>
            </label>
            <label class="form__label">
                <div class="form__name">Повторите пароль:</div>
                <input type="password" class="form__input" v-model="user.passwordConfirm" required>
            </label>
            <label class="form__label">
                <div class="form__name">E-mail:</div>
                <input type="email" class="form__input" v-model="user.email" required>
            </label>
            <button class="form__submit" type="submit" @click.prevent="register">Зарегистрироваться</button>

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
                    url:'http://'+host+':'+port+'/api/register/',
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
                        document.location.replace("/board");
                    }
                });
            }else{
                alert("Введённые пароли не совпадают!");
            }

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
        display: inline-block;
        border: 2px solid black;
        border-radius: 10px;
        padding: 10px 30px;
        max-width: 350px;
        text-align: center;
        background-color: #cccccc;
    }
    .form__title{
        display: inline-block;
        font-size: 20px;
        font-weight: bold;
        margin-bottom: 10px;
    }
    .form__label{
        display: block;
        text-align: left;
        margin-bottom: 10px;
    }
    .form__input{
        min-width: 250px;
    }
    .form__submit{
        margin: 10px 0px;
    }
</style>
