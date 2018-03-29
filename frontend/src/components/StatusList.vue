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
        white-space: nowrap;
        overflow-x: auto;
        min-height: 100%;
    }
    .status__item{
        display: inline-block;
        border: 3px solid black;
        padding: 10px;
        margin: 5px;
        vertical-align: top;
        background: rgba(#333,0.2);
        text-align: center;
        min-width: 250px;
    }
</style>
