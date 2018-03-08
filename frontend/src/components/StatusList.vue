<template>
    <ul class="status__list">
        <li class="status__item" v-for="(statusItem, index) in statusItems">
            <div class="status__name">{{statusItem.statusName}}</div>
            <taskList :taskItems="statusItems[index].taskItems"></taskList>
        </li>
    </ul>
</template>

<script>
export default{
    props:['statusItems']
}
</script>

<style lang="scss" scoped>
    .status__list{
        white-space: nowrap;
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
