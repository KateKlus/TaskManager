<template>
    <div class="DelTemplateMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body popup__body-delTemplate">
            <div class="popup__title">Удаление шаблона</div>
            <label for="" class="popup__label">
                <div class="popup__text">Выберите шаблон</div>
                <select name="templateList" class="popup__select" v-model="selectedTemplate">
                    <option v-for="templateItem in templateList" v-bind:value="templateItem">{{templateItem.taskTemplateName}}</option>
                </select>
            </label>
            <button class="popup__submit" @click.prevent="deleteTemplate">Удалить</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    props:['templateList'],
    data(){
        return{
            selectedTemplate:""
        }
    },
    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        },
        deleteTemplate(){
            var self = this;
            if(this.selectedTemplate.default){
                self.$root.$emit('showDialog','Невозможно удалить шаблон по умолчанию!','showError');
            }
            else{
                axios({
                    method: 'DELETE',
                    url: host+'/api/tasktemplates/'+self.selectedTemplate.id+'/?access_token='+getCookie("access_token")
                }).then(function (response) {
                    self.$root.$emit('showDialog','Шаблон успешно удалён!','showMessage');
                    self.$emit('wrapperClick');
                    self.$root.$emit('updateBoard');
                }).catch(function (error) {
                    self.$root.$emit('showDialog',error.response.data.error+"; "+error.response.data.message,'showError');
                });
            }
        }
    }
}
</script>
<style lang="scss" scoped>
    .popup__body-delTemplate{
        min-height: 200px;
    }

</style>
