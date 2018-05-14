<template>
    <div class="boardListMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="popup__title">Список досок</div>
            <ul class="boardListMenu__list">
                <li class="boardListMenu__item" v-for="member in memberList"><a href="" class="boardListMenu__link" @click.prevent="selectBoard(member.board)"> {{member.board.boardName}} [{{member.role}}]</a></li>
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
        axios.get(host+'/api/getUserId/?access_token=' + getCookie("access_token")).then(function(response){
            axios.get(host+'/api/users/'+response.data+'/members/?access_token='+getCookie("access_token")).then(function(response){
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
        display: block;
        padding: 7px;
        text-decoration: none;
        color: #757575;
        border-radius: 5px;
        background: #fff;
        margin-bottom: 7px;
        box-shadow: 1px 1px 4px rgba(black, .5);
        transition: box-shadow .3s;
        &:hover{
            box-shadow: none;
        }
    }

</style>
