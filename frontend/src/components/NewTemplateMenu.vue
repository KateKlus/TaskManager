<template>
    <div class="newTemplateMenu">
        <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="popup__title">Создать новый шаблон</div>
            <label for="" class="popup__label">
                <div class="popup__text">Название шаблона:</div>
                <input type="text" v-model="newTemplate.taskTemplateName" required>
            </label>
            <label for="" class="popup__label">
                <ul class="attribute__list">
                    <li class="attribute__item" v-for="attributeItem in attributeList">
                        <a href="" class="attribute__delete" @click.prevent="deleteAttributeItem(attributeItem)">
                            <img src="../assets/delete.png" alt="DEL" class="attribute__delete-img">
                        </a>
                        <label for="" class="attribute__label">
                            <div class="attribute__text">Название атрибута:</div>
                            <input type="text" v-model="attributeItem.attributeName" required>
                        </label>
                        <label for="" class="attribute__label">
                            <div class="attribute__text">Тип данных:</div>
                            <select name="typeList" v-model="attributeItem.attributeType" required="">
                                <option value="text">Текстовый</option>
                                <option value="number">Числовой</option>
                                <option value="date">Дата</option>
                                <option value="time">Время</option>
                            </select>
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
            deleteAttributeItem(item){
                this.attributeList.splice(this.attributeList.indexOf(item),1);
            },
            createNewTemplate(){
                var self = this;
                if(self.checkFields()){
                    axios({
                        method: 'post',
                        url: host+'/api/tasktemplates/?access_token='+getCookie("access_token"),
                        data:self.newTemplate
                    }).then(function (response) {
                        self.createNewAttributes(response.data);
                    }).catch(function (error) {
                        self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
                    });
                }
            },
            createNewAttributes(template){
                var self = this;
                var postError = false;
                this.attributeList.forEach(function(attribute){
                            axios({
                                method: 'post',
                                url: host+'/api/tasktemplates/'+template.id+'/attributes/?access_token='+getCookie("access_token"),
                                data: attribute
                            }).catch(function (error) {
                                postError = error;
                            });
                })
                if (!postError){
                    self.$root.$emit('updateBoard');
                    self.$emit('wrapperClick');
                    self.$root.$emit('showDialog',"Шаблон успешно создан!",'showMessage');
                }
                else{
                    self.$root.$emit('showDialog',postError.response.data.error+"; "+postError.response.data.message,'showError');
                }
            },
            checkFields(){
                var self = this;
                var error = false;
                if(this.newTemplate.taskTemplateName == ""){
                    self.$root.$emit('showDialog','Введите название шаблона!','showError');
                    error = true;
                    return false;
                }
                 this.attributeList.forEach(function(attribute){
                    if(attribute.attributeName == ""){
                        self.$root.$emit('showDialog','Введите название атрибута!','showError');
                        error = true;
                        return false;
                    }
                        if(attribute.attributeType == ""){
                            self.$root.$emit('showDialog','Выберите тип атрибута!','showError');
                            error = true;
                            return false;
                        }
                });
                if(!error){
                    return true;
                }

            }
        }
    }
</script>

<style lang="scss" scoped>
    .attribute__item{
        position: relative;
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
    .attribute__delete{
        display: block;
        position: absolute;
        top: 5px;
        right: 5px;
        width: 25px;
        height: 25px;
        cursor: pointer;
    }
    .attribute__delete-img{
        width: 100%;
        height: 100%;
    }

</style>
