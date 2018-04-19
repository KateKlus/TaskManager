# coding: utf-8

*** Settings ***
Documentation     Тестирование работы OAPI с объектами task
Default Tags  api
Test Timeout      1 minute
Library  requests
Library  Collections
Library  DatabaseLibrary
Library  OperatingSystem
Library  String

Resource  ./variables.robot
Resource  ../keywords/common-keywords.robot

Suite Setup       Connect To Database    pymysql   ${DBName}    ${DBUser}    ${DBPass}    ${DBHost}    ${DBPort}    utf8
Suite Teardown    Disconnect From Database

*** Variables ***
${USER_URL}
${USER_NAME}  AutotestUser
${USER_ID}  2
${TASK_ID}  2

*** Test Cases ***
Test getting users tasks list
    [Tags]    task
    [Documentation]  Получение списка задач пользователя
    ${actual}  Get  ${API_URL}users/${USER_ID}/tasks/
    Should Be Equal  ${actual.status_code}    ${200}
    Log  ${actual.content}
    ${actualList}  Create actual result task list  @{actual.json()}
    ${sql}  catenate
    ...  SELECT users_tasks.task_id, tasks.task_name, users.username, users_tasks.user_id, tasks.board FROM users_tasks
    ...  inner join tasks on tasks.task_id=users_tasks.task_id
    ...  inner join users on users.user_id=users_tasks.user_id
    ...  where tasks.user_id=${USER_ID};
    @{db}    Query    ${sql}
    Log  ${db}
    ${expectedlList}  Create expected result task list  @{db}
    should be equal  ${actualList}  ${expectedlList}

Test getting users task
    [Tags]    task
    [Documentation]  Получение информации о задаче пользователя
    ${actual}  Get  ${API_URL}users/${USER_ID}/tasks/${TASK_ID}/
    Should Be Equal  ${actual.status_code}    ${200}
    Log  ${actual.content}
    ${actualList}  Create actual result task list  ${actual.json()}
    ${sql}  catenate
    ...  SELECT users_tasks.task_id, tasks.task_name, users.username, users_tasks.user_id, tasks.board FROM users_tasks
    ...  inner join tasks on tasks.task_id=users_tasks.task_id
    ...  inner join users on users.user_id=users_tasks.user_id
    ...  where tasks.user_id=${USER_ID} and tasks.task_id=${TASK_ID};
    ${db}    Query    ${sql}
    Log  ${db}
    ${expectedlList}  Create expected result task list  @{db}
    should be equal  ${actualList}  ${expectedlList}


