<template>
    <div class="DelTemplateMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body popup__body-delTemplate">
            <div class="popup__title">Удаление шаблона</div>
            <label for="" class="popup__label">
                <div class="popup__text">Выберите шаблон</div>
                <select name="templateList" v-model="selectedTemplate">
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
                alert("Невозможно удалить шаблон по умолчанию!");
            }
            else{
                axios({
                    method: 'DELETE',
                    url: 'http://'+host+':'+port+'/api/tasktemplates/'+self.selectedTemplate.id+'/'
                }).then(function (response) {
                    alert("Шаблон успешно удалён!")
                    self.$emit('wrapperClick');
                    self.$root.$emit('updateBoard');
                }).catch(function (error) {
                    alert("Error! "+ error);
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
