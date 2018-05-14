<template>
    <ul class="task__list">
        <li class="task__item" v-for="(taskItem, index) in taskList" v-if="taskItem.currentStatus.id == statusItemID">
            <div class="task__header">
                <div class="task__index">TskMgr-{{taskItem.id}}</div>
                <div class="task__executor">
                    <div class="task__executor-name">{{taskItem.author.username}}</div>
                    <div class="task__executor-image">
                        <img src="../assets/avatar.png" alt="AVA" class="task__executor-img">
                    </div>
                </div>
            </div>
            <div class="task__content">
                <div class="task__priority">
                    <img src="./../assets/priority.png" alt="" class="task__priority-img">
                </div>
                <a href="" class="task__name" @click.prevent="showTaskMenu(taskItem)">{{taskItem.taskName}}</a>
            </div>
            <div class="task__footer">
                <div class="task__date">{{taskItem.taskDate}}</div>
                <div class="task__time">{{taskItem.taskTime}}</div>
            </div>
        </li>
    </ul>
</template>

<script>
export default{
    props:['statusItemID','taskList'],
    methods:{
        showTaskMenu(task){
            this.$root.$emit('clickOnTaskName', task);
        }
    }
}
</script>

<style lang="scss" scoped>
    .task__item{
        margin: 10px 0px;
        width: 250px;
        background-color: #fff;
        border-radius: 4px;
        box-shadow: 0px 1px 5px rgba(0,0,0,0.25);
        white-space: pre-line;
    }
    .task__menu-link{
        display: inline-block;
    }
    .task__header{
        text-align: center;
        padding: 5px;
    }
    .task__index,.task__executor{
        font-size: 12px;
        display: inline-block;
        width: 49%;
        vertical-align: middle;
    }
    .task__index{
        text-align: left;
    }
    .task__executor{
        text-align: right;
    }
    .task__executor-name, .task__executor-image{
        display: inline-block;
        vertical-align: middle;
        max-width: 60%;
    }
    .task__executor-name{
        text-align: center;
    }
    .task__executor-image{
        max-width: 35px;
        .task__executor-img{
            width: 100%;
            border-radius: 50%;
        }
    }
    .task__content{
        font-size: 18px;
        font-weight: bold;
        border-top: 1px solid black;
        border-bottom: 1px solid black;
        padding: 10px 0px;
    }
    .task__priority,.task__name{
        display: inline-block;
        vertical-align: middle;
    }
    .task__priority{
        max-width: 40px;
        .task__priority-img{
            width: 100%;
        }
    }
    .task__name{
        width: 80%;
        text-align: left;
        text-decoration: none;
        color: black;
    }
    .task__footer,.task__header{

    }
    .task__date,.task__time{
        display: inline-block;

    }
    .task__date{
        width: 80%;
        text-align: left;
        vertical-align: middle;
    }
    .task__time{
        max-width: 18%;
        vertical-align: middle;
    }
</style>
