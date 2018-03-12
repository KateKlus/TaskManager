<template>
    <div class="newStatusMenu">
       <div class="menu__wrapper" @click="closeMenu"></div>
        <div class="menu__body">
            <div class="newStatusMenu__title">Добавить новый статус</div>
            <label for="" class="newStatusMenu__label">
                <div class="newStatusMenu__text">Введите имя статуса:</div>
                <input type="text" v-model="newStatus.statusName">
            </label>
            <button class="newStatusMenu__submit" @click.prevent="addNewStatus">Добавить статус</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    props:['currentUser'],
    data(){
        return{
            newStatus:{
                statusName:""
            }
        }
    },
    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        },
        addNewStatus(){
            var self = this;
            axios({
                method: 'post',
                url: 'http://localhost:8080/api/statuses/',
                data:self.newStatus
            }).then(function (response) {
                self.addNewBoardStatus();
            }).catch(function (error) {
                alert("Error! "+ error)
            });

        },
        addNewBoardStatus(){
            var self = this;
            axios.get("http://localhost:8080/api/statuses/").then(function(response){
                        response.data.forEach(function(item){
                            if(item.statusName == self.newStatus.statusName){
                                axios({
                                    method: 'post',
                                    url: 'http://localhost:8080/api/boards/'+getCookie("current_board")+'/statuses',
                                    data:item
                                }).then(function (response) {
                                    alert("Success!");
                                    self.$root.$emit('updateBoard');
                                    self.$emit('wrapperClick');
                                }).catch(function (error) {
                                    alert("Error! "+ error)
                                });
                            }
                        });
                    }).catch(function(error){
                        alert(error);
                    })
        }
    }
}
</script>

<style lang="scss" scoped>
    .newStatusMenu__title{
        font-weight: bold;
        font-size: 18px;
        margin-bottom: 10px;
    }
    .newStatusMenu__submit{
        display: block;
        margin: 10px auto;
    }

</style>
