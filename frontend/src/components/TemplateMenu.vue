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
        <transition-group name='fade' :duration="100">
            <newTemplateMenu v-if="showNewTemplateMenu" key="newTemplate" @wrapperClick="showNewTemplateMenu = !showNewTemplateMenu"></newTemplateMenu>
            <editTemplateMenu v-if="showEditTemplateMenu" key="editTemplate" @wrapperClick="showEditTemplateMenu = !showEditTemplateMenu" :templateList="templateList"></editTemplateMenu>
            <delTemplateMenu v-if="showDelTemplateMenu" key="delTemplate" @wrapperClick="showDelTemplateMenu = !showDelTemplateMenu" :templateList="templateList"></delTemplateMenu>
        </transition-group>
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
