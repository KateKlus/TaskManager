<template>
    <div class="wrapper">
        <header id="board__header" class="board__header">
            <leftMenu :leftMenu="leftMenu"></leftMenu>
            <div class="board__title">{{boardTitle}}</div>
            <div class="board__user">
                <div class="user__name">{{userName}}</div>
                <div class="user__avatar">
                    <img src="./assets/avatar.jpg" alt="User" class="user__avatar-img">
                </div>
            </div>
        </header>
        <statusList :statusItems="statusItems"></statusList>
    </div>

</template>

<script>
export default{
    data(){
        return{
            boardTitle: 'Название доски',
            userName: 'Имя пользователя',
            leftMenu:{
                menuTitle: "Меню",
                menuItems:[
                    {menuItemLink: "https://www.google.ru/", menuItemName: "Создать доску"},
                    {menuItemLink: "https://www.yandex.ru/", menuItemName: "Мои доски"},
                    {menuItemLink: "https://www.yandex.ru/", menuItemName: "Пригласить"},
                    {menuItemLink: "https://www.yandex.ru/", menuItemName: "Настройка доски"},
                    {menuItemLink: "https://www.yandex.ru/", menuItemName: "Отчеты"}
                ]
            },
            statusItems:[
                {statusId: 0,
                 statusName: "Название статуса",
                 taskItems:[
                     {taskId:0,
                      taskName: "Имя задачи",
                      statusId: 0,
                      taskDate: "28.02.2018",
                      taskTime: "12h",
                      taskExecutor:"German",
                      contentItems:[
                          {contentId: 0, contentName: "Название поля", content: "Содержание поля"},
                          {contentId: 1, contentName: "ipsum", content: "Lorem ipsum dolor sit amet"}
                      ]},
                     {taskId:1,
                      taskName: "impedit",
                      statusId: 0,
                      taskDate: "12.02.2018",
                      taskTime: "4h",
                      taskExecutor:"Exec",
                      contentItems:[
                          {contentId: 3, contentName: "dolor", content: "Контееент!"},
                          {contentId: 4, contentName: "sit", content: "Абракадабра"}
                      ]}
                 ]},
                {statusId: 1,
                 statusName: "Deserunt",
                 taskItems:[
                     {taskId:2,
                      taskName: "Task 3",
                      statusId: 1,
                      taskDate: "01.01.2018",
                      taskTime: "36h",
                      taskExecutor:"Thomas Check",
                      contentItems:[
                          {contentId: 5, contentName: "amet", content: "Content 5"},
                          {contentId: 6, contentName: "consectetur", content: "eius dolor impedit"},
                          {contentId: 7, contentName: "adipisicing", content: "Content 7"},
                          {contentId: 8, contentName: "elit", content: "dolor sit amet"}
                      ]},
                     {taskId:3,
                      taskName: "voluptates",
                      statusId: 1,
                      taskDate: "28.01.2017",
                      taskTime: "1h",
                      taskExecutor:"Fill Kill",
                      contentItems:[
                          {contentId: 9, contentName: "Eaque", content: "deserunt explicabo voluptates"},
                          {contentId: 10, contentName: "officia", content: "Eaque ad officia"}
                      ]}
                 ]},
                {statusId: 2,
                 statusName: "Explicabo",
                 taskItems:[
                   {taskId:4,
                    taskName: "beatae",
                    statusId: 2,
                    taskDate: "21.08.2018",
                    taskTime: "5h",
                    taskExecutor:"Rus Lang",
                    contentItems:[
                        {contentId: 11, contentName: "magnam", content: "Content 11"}
                    ]}
                ]}
            ]
        }
    }
}
</script>

<style lang="scss">
    .board__header{
        display: flex;
        justify-content: space-between;
        padding: 8px;
        align-items: center;
        background: rgba(#333,.2);
        border: 1px solid black;
        min-width: 400px;
    }
    .user__avatar{
        max-width: 50px;
    }
    .user__avatar-img{
        width: 100%;
        border-radius: 50%;
    }
    .board__button,.user__avatar, .user__name{
        display: inline-block;
        vertical-align: middle;
    }

</style>
