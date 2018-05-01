<template>
    <div class="TemplateMenu">
       <div class="popup__wrapper" @click="closeMenu"></div>
        <div class="popup__body">
            <div class="popup__title">Управление шаблонами</div>
            <ul class="TemplateMenu__list">
                <li class="TemplateMenu__item">
                    <a href="" class="TemplateMenu__link" @click.prevent="showNewTemplateMenu = !showNewTemplateMenu">Создать шаблон</a>
                </li>
                <li class="TemplateMenu__item">
                    <a href="" class="TemplateMenu__link" @click.prevent="showEditTemplateMenu =!showEditTemplateMenu">Редактировать шаблон</a>
                </li>
                <li class="TemplateMenu__item">
                    <a href="" class="TemplateMenu__link" @click.prevent="showDelTemplateMenu = !showDelTemplateMenu">Удалить шаблон</a>
                </li>
            </ul>

        </div>
        <newTemplateMenu v-if="showNewTemplateMenu" @wrapperClick="showNewTemplateMenu = !showNewTemplateMenu"></newTemplateMenu>
        <editTemplateMenu v-if="showEditTemplateMenu" @wrapperClick="showEditTemplateMenu = !showEditTemplateMenu" :templateList="templateList"></editTemplateMenu>
        <delTemplateMenu v-if="showDelTemplateMenu" @wrapperClick="showDelTemplateMenu = !showDelTemplateMenu" :templateList="templateList"></delTemplateMenu>
    </div>
</template>

<script>
import axios from 'axios'
export default{
    data(){
        return{
            showNewTemplateMenu: false,
            showDelTemplateMenu: false,
            showEditTemplateMenu: false
        }
    },
    props:['templateList'],
    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        }
    }
}
</script>

<style lang="scss" scoped>
    .TemplateMenu__item{
        margin: 5px;
    }
    .TemplateMenu__link{
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
