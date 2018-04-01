<template>
    <div class="editTemplateMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="popup__title">Редактировать шаблон</div>
            <label for="" class="popup__label">
                <div class="popup__text">Выберите шаблон</div>
                <select name="templateList" v-model="selectedTemplate" @change="loadAttributeList">
                    <option v-for="templateItem in templateList" v-bind:value="templateItem">{{templateItem.taskTemplateName}}</option>
                </select>
            </label>
            <label for="" class="popup__label">
                <div class="popup__text">Название шаблона:</div>
                <input type="text" v-model="selectedTemplate.taskTemplateName">
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
            <button class="popup__submit" @click.prevent="editTemplate">Сохранить изменения</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    props:['templateList'],
    data(){
        return{
            selectedTemplate:"",
            attributeList:[]
        }
    },
    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        },
        loadAttributeList(){
            var self = this;
            this.attributeList = [];
            axios.get('http://'+host+':'+port+'/api/tasktemplates/'+self.selectedTemplate.id+'/attributes/').then(function(response){
                response.data.forEach(function(attribute){
                    self.attributeList.push(attribute);
                })
            }).catch(function(error){
                alert(error);
            })
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
        editTemplate(){
            var self = this;
            if(this.selectedTemplate.default){
                alert("Невозможно изменить шаблон по умолчанию!")
            }
            else{
                axios({
                    method: 'put',
                    url: 'http://'+host+':'+port+'/api/tasktemplates/'+self.selectedTemplate.id+'/',
                    data:self.selectedTemplate
                }).then(function (response) {
                    self.editAttributes(response.data);
                }).catch(function (error) {
                    alert("Error! "+ error)
                });
            }
        },
        editAttributes(template){
            var self = this;
            var postError = false;
            this.attributeList.forEach(function(attribute){
                if(attribute.id){
                    axios({
                        method: 'put',
                        url: 'http://'+host+':'+port+'/api/attributes/'+attribute.id+'/',
                        data: attribute
                    }).catch(function (error) {
                        postError = error;
                    });
                }
                else{
                    axios({
                        method: 'post',
                        url: 'http://'+host+':'+port+'/api/tasktemplates/'+template.id+'/attributes/',
                        data: attribute
                    }).catch(function (error) {
                        postError = error;
                    });
                }

            })
            if (!postError){
                self.$root.$emit('updateBoard');
                self.$emit('wrapperClick');
                alert("Шаблон успешно изменён!")
            }
            else{
                alert(postError);
            }
        }
    },
    watch:{
        selectedTemplate: function(value){
            if(value.default){
                alert("Невозможно изменить шаблон по умолчанию!")
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
