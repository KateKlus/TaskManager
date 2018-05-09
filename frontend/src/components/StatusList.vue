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
        color:black;
    }
    .status__list{
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        flex-wrap: nowrap;
        align-items: stretch;
    }
    .status__item{
        display: inline-block;
        padding: 10px;
        vertical-align: top;
        background: rgba(#333,0.2);
        text-align: center;
        min-width: 250px;
        border-left: 2px dashed black;
        height: 100%;
        &:first-child{
            border-left: none;
        }
    }
</style>
