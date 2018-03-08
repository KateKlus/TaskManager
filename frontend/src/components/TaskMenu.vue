<template>
    <div class="taskMenu">
        <div class="taskMenu__wrapper" @click="closeMenu"></div>
        <div class="taskMenu__menu">
            <div class="taskMenu__header">
                <input type="text" v-model="taskItem.taskName" placeholder="Имя задачи" class="taskMenu__input taskMenu__taskName">
                <div class="taskMenu__text">TaskMgr-{{taskItem.taskId}}</div>
            </div>
            <div class="taskMenu__body">
                <div class="taskMenu__left">
                   <div class="taskMenu__text">Описание задачи</div>
                    <textarea v-model="taskItem.taskDescription" placeholder="Описание задачи" class="taskMenu__textarea"></textarea>
                    <div class="taskMenu__text">Дата создания задачи</div>
                    <input type="text" v-model="taskItem.taskDate" placeholder="Дата" class="taskMenu__input">
                    <ul class="custom__list">
                        <li class="custom__item" v-for="customField in taskItem.customFields">
                            <div class="custom__item-name">{{customField.fieldName}}</div>
                            <input type="text" v-model="customField.fieldContent">
                        </li>
                    </ul>
                </div>
                <div class="taskMenu__right">
                    <div class="taskMenu__text">Автор задачи</div>
                    <input type="text" v-model="taskItem.taskAuthor" placeholder="Автор" class="taskMenu__input">
                    <div class="taskMenu__text">Статус</div>
                    <select name="statusList" id="statusList" v-model="selected" @change="changeStatus">
                        <option v-for="statusItem in statusItems"
                        v-bind:value="statusItem.statusId">{{statusItem.statusName}}</option>
                    </select>
                    <div class="taskMenu__text">Исполнители</div>
                    <input type="text" v-model="taskItem.taskExecutor" placeholder="Исполнители" class="taskMenu__input">
                    <div class="taskMenu__text">Затраченное время</div>
                    <input type="text" v-model="taskItem.taskTime" placeholder="Затраченное время" class="taskMenu__input">
                </div>
            </div>
        <button class="taskMenu__save" @click=closeMenu>Сохранить</button>
        </div>
    </div>

</template>

<script>
export default{
    data(){
        return{
            selected:this.taskItem.statusId
        }
    },
    props:['taskItem','statusItems'],
    methods:{
        closeMenu(){
            this.$emit('wrapperClick');
        },
        changeStatus(){
            this.$root.$emit('changeStatus', this.taskItem, this.selected);
        }
    }
}
</script>

<style lang="scss" scoped>
    .taskMenu__wrapper{
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background: rgba(#000,.5);
        z-index: 0;
    }
    .taskMenu__menu{
        position: absolute;
        z-index: 100;
        padding: 10px 30px;
        top: 50px;
        left: 20%;
        right: 20%;
        max-height: 560px;
        width: 800px;
        overflow-y: auto;
        border: 2px solid black;
        background-color: #cccccc;
    }
    .taskMenu__header{
        display: flex;
        justify-content: space-around;
    }
    .taskMenu__body{
        display: flex;
        justify-content: space-around;
    }
    .taskMenu__input{
        display: block;
    }
    .taskMenu__header{
        padding:20px;
    }
    .taskMenu__left,.taskMenu__right{
        padding: 20px;
        border: 1px solid black;
    }
    .taskMenu__left{
        min-width: 450px;
    }
    .taskMenu__textarea{
        width: 400px;
        min-height: 100px;
        resize: none;
    }
    .taskMenu__save{
        display: block;
        margin: 20px auto;
    }
</style>
