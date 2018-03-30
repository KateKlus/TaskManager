<template>
    <div class="newTemplateMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="popup__title">Создать новый шаблон</div>
            <label for="" class="popup__label">
                <div class="popup__text">Название шаблона:</div>
                <input type="text" v-model="newTemplate.taskTemplateName">
            </label>
            <label for="" class="popup__label">
                <ul class="attribute__list">
                    <li class="attribute__item" v-for="attributeItem in attributeList">
                        <label for="" class="attribute__label">
                           <div class="attribute__text">Название атрибута:</div>
                            <input type="text" v-model="attributeItem.attributeName">
                        </label>
                        <label for="" class="attribute__label">
                            <div class="attribute__text">Тип данных:</div>
                            <input type="text" v-model="attributeItem.attributeType">
                        </label>
                        <label for="" class="attribute__label">
                            <div class="attribute__text">Обязателен к заполнению</div>
                            <input type="checkbox" v-model="attributeItem.obligatory">
                        </label>
                    </li>
                </ul>
            </label>
            <label for="" class="popup__label">
                <div class="popup__button" @click="addNewAttribute">Добавить поле в шаблон</div>
            </label>
            <button class="popup__submit" @click.prevent="createNewTemplate">Создать шаблон</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            newTemplate:{
                taskTemplateName:"",
                default: false
            },
            attributeList:[
                {
                    attributeName: "",
                    attributeType: "",
                    default: false,
                    obligatory: false
                }
            ]
        }
    },
    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        },
        addNewAttribute(){
            var attribute = {
                attributeName: "",
                attributeType: "",
                default: false,
                obligatory: false
            }
            this.attributeList.push(attribute);
        },
        createNewTemplate(){
            var self = this;
            axios({
                method: 'post',
                url: 'http://'+host+':'+port+'/api/tasktemplates/',
                data:self.newTemplate
            }).then(function (response) {
                self.createNewAttributes(response.data);
            }).catch(function (error) {
                alert("Error! "+ error)
            });
        },
        createNewAttributes(template){
            var self = this;
            var postError = false;
            this.attributeList.forEach(function(attribute){
                axios({
                    method: 'post',
                    url: 'http://'+host+':'+port+'/api/tasktemplates/'+template.id+'/attributes/',
                    data: attribute
                }).catch(function (error) {
                    postError = error;
                });
            })
            if (!postError){
                self.$root.$emit('updateBoard');
                self.$emit('wrapperClick');
                alert("Шаблон успешно создан!")
            }
            else{
                alert(postError);
            }
        }
    }
}
</script>

<style lang="scss" scoped>
    .attribute__item{
        border: 1px solid black;
        padding: 10px;
        margin: 10px;
    }
    .attribute__text{
        display: inline-block;
    }
    .attribute__label{
        display: inline-block;
        margin: 10px;
        width: 70%;

    }

</style>
