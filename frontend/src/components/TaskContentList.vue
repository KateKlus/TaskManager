<template>
    <ul class="content__list">
        <li class="content__item" v-for="contentItem in contentItems">
        <div class="content__title">{{contentItem.contentName}}</div>
        <textarea class="content__area popup__area">{{contentItem.content}}</textarea>
        </li>
    </ul>
</template>

<script>
export default{
    props:['contentItems']
}
</script>

<style lang="scss" scoped>
    .content__item{
        border: 1px solid gray;
        padding: 5px;
        margin: 10px 0;
        background: rgba(#333,.2)
    }
    .content__area{
        resize: vertical;
        width: 100%;
        min-height: 30px;
        max-height: 150px;
        height: auto;
    }
</style>
