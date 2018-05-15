<template>
    <div class="editTemplateMenu">
        <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="popup__title">Редактировать шаблон</div>
            <label for="" class="popup__label">
                <div class="popup__text">Выберите шаблон</div>
                <select name="templateList" class="popup__select" v-model="selectedTemplate" @change="loadAttributeList">
                    <option v-for="templateItem in templateList" v-bind:value="templateItem">{{templateItem.taskTemplateName}}</option>
                </select>
            </label>
            <label for="" class="popup__label">
                <div class="popup__text">Название шаблона:</div>
                <input type="text" class="popup__input" v-model="selectedTemplate.taskTemplateName">
            </label>
            <label for="" class="popup__label">
                <ul class="attribute__list">
                    <li class="attribute__item" v-for="attributeItem in attributeList">
                        <a href="" class="attribute__delete" @click.prevent="deleteAttributeItem(attributeItem)">
                            <img src="../assets/delete.png" alt="DEL" class="attribute__delete-img">
                        </a>
                        <label for="" class="attribute__label">
                            <div class="popup__text">Название атрибута:</div>
                            <input type="text" class="popup__input" v-model="attributeItem.attributeName">
                        </label>
                        <label for="" class="attribute__label">
                            <select name="typeList" class="popup__select" v-model="attributeItem.attributeType">
                                <option value="text">Текстовый</option>
                                <option value="number">Числовой</option>
                                <option value="date">Дата</option>
                                <option value="time">Время</option>
                            </select>
                        </label>
                        <label for="" class="attribute__label">
                            <div class="popup__text">Обязателен к заполнению</div>
                            <input type="checkbox" class="popup__checkbox" v-model="attributeItem.obligatory">
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
                axios.get(host+'/api/tasktemplates/'+self.selectedTemplate.id+'/attributes/?access_token='+getCookie("access_token")).then(function(response){
                    response.data.forEach(function(attribute){
                        self.attributeList.push(attribute);
                    })
                }).catch(function(error){
                    self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
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
                    self.$root.$emit('showDialog',"Невозможно удалить шаблон по умолчанию!",'showError');
                }
                else{
                    axios({
                        method: 'put',
                        url: host+'/api/tasktemplates/'+self.selectedTemplate.id+'/?access_token='+getCookie("access_token"),
                        data:self.selectedTemplate
                    }).then(function (response) {
                        self.editAttributes(response.data);
                    }).catch(function (error) {
                        self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
                    });
                }
            },
            deleteAttributeItem(attribute){
                axios({
                    method: 'DELETE',
                    url: host+'/api/attributes/'+attribute.id+'/?access_token='+getCookie("access_token"),
                }).then(function (response) {
                }).catch(function (error) {
                    self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
                });
                this.attributeList.splice(this.attributeList.indexOf(attribute),1);
            },
            editAttributes(template){
                var self = this;
                var postError = false;
                this.attributeList.forEach(function(attribute){
                    if(attribute.id){
                        axios({
                            method: 'put',
                            url: host+'/api/attributes/'+attribute.id+'/?access_token='+getCookie("access_token"),
                            data: attribute
                        }).catch(function (error) {
                            postError = error;
                        });
                    }
                    else{
                        axios({
                            method: 'post',
                            url: host+'/api/tasktemplates/'+template.id+'/attributes/?access_token='+getCookie("access_token"),
                            data: attribute
                        }).catch(function (error) {
                            postError = error;
                        });
                    }

                })
                if (!postError){
                    self.$root.$emit('updateBoard');
                    self.$emit('wrapperClick');
                    self.$root.$emit('showDialog',"Шаблон успешно изменён!",'showMessage');
                }
                else{
                    self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
                }
            }
        },
        watch:{
            selectedTemplate: function(value){
                if(value.default){
                    self.$root.$emit('showDialog',"Невозможно изменить шаблон по умолчанию!",'showError');
                }
            }
        }
    }
</script>

<style lang="scss" scoped>
    .attribute__item{
        position: relative;
        border: 2px dashed rgba(0,0,0,.5);
        padding: 10px;
        margin: 10px;
    }
    .attribute__label{
        display: inline-block;
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
        opacity: .7;
    }

</style>
