<template>
    <div class="boardListMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="popup__title">Список досок</div>
            <ul class="boardListMenu__list">
                <li class="boardListMenu__item" v-for="member in memberList"><a href="" class="boardListMenu__link" @click.prevent="selectBoard(member.board)"> {{member.board.boardName}} [{{member.role.roleName}}]</a></li>
            </ul>

        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            memberList: ""
        }
    },
    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        },
        selectBoard(board){
            this.$root.$emit('onBoardSelect', board);
            this.$emit('wrapperClick');
        }
    },
    created(){
        var self = this;
        axios.get('http://'+host+':'+port+'/api/getUserId/?access_token=' + getCookie("access_token")).then(function(response){
            axios.get('http://'+host+':'+port+'/api/users/'+response.data+'/members/?access_token='+getCookie("access_token")).then(function(response){
                self.memberList = response.data;
            })
        })
    }
}
</script>

<style lang="scss" scoped>
    .boardListMenu__item{
        margin: 5px;
    }
    .boardListMenu__link{
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

</style>
