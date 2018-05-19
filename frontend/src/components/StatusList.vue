<template>
    <ul class="status__list">
        <li class="status__item" v-for="(statusItem, index) in statusList">
            <a href="" class="status__name" @click.prevent="showStatusMenu(statusItem)">{{statusItem.taskStatus.statusName}}</a>
            <taskList :statusItemID="statusList[index].taskStatus.id" :taskList="taskList"></taskList>
        </li>
    </ul>
</template>

<script>
export default{
    props:['statusList','taskList'],
    methods:{
        showStatusMenu(statusItem){
            this.$root.$emit('clickOnStatusName', statusItem);
        },
    }
}
</script>

<style lang="scss" scoped>
    .status__name{
        text-decoration: none;
        display: block;
    }
    .status__list{
        white-space: nowrap;
        overflow-x: auto;
        min-height: 100%;
        display: inline-block;
        margin-top: 70px;
    }
    .status__item{
        display: inline-block;
        padding: 10px;
        vertical-align: top;
        background: #f1f1f1;
        text-align: center;
        min-width: 250px;
        border-left: 2px dashed #757575;
        height: 100%;
        &:first-child{
            border-left: none;
        }
    }
</style>
