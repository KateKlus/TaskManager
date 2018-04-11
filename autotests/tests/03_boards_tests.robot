# coding: utf-8

*** Settings ***
Documentation     Тестирование работы OAPI с объектами board
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
${BOARD_ID}  1

*** Test Cases ***
Test getting boards list
    [Tags]    board
    [Documentation]  Получение списка досок
    ${actual}  Get  ${API_URL}boards/
    Should Be Equal  ${actual.status_code}    ${200}
    Log  ${actual.content}
    ${actualList}  Create actual result board list  @{actual.json()}
    ${sql}  catenate
    ...  SELECT * FROM boards;
    @{db}    Query    ${sql}
    Log  ${db}
    ${expectedlList}  Create expected result board list  @{db}
    should be equal  ${actualList}  ${expectedlList}

Test getting boards tasks list
    [Tags]    board task
    [Documentation]  Получение списка всех задач доски
    ${actual}  Get  ${API_URL}boards/${BOARD_ID}/tasks/
    Should Be Equal  ${actual.status_code}    ${200}
    Log  ${actual.content}
    ${actualList}  Create actual result task list  @{actual.json()}
    ${sql}  catenate
    ...  SELECT users_tasks.task_id, tasks.task_name, users.username, users_tasks.user_id, tasks.board FROM users_tasks
    ...  inner join tasks on tasks.task_id=users_tasks.task_id
    ...  inner join users on users.user_id=users_tasks.user_id
    ...  where tasks.board=${BOARD_ID};
    @{db}    Query    ${sql}
    Log  ${db}
    ${expectedlList}  Create expected result task list  @{db}
    should be equal  ${actualList}  ${expectedlList}


*** Keywords ***
Create actual result board list
    [Arguments]  @{json}
    ${result}  create list
    :FOR    ${ELEMENT}    IN    @{json}
    \    Log    ${ELEMENT}
    \    ${id}  Get From Dictionary  ${ELEMENT}  id
    \    ${boardName}  Get From Dictionary  ${ELEMENT}  boardName
    \    ${description}  Get From Dictionary  ${ELEMENT}  description
    \    ${owner}  Get From Dictionary  ${ELEMENT}  boardOwner
    \    ${ownerId}  Get From Dictionary  ${owner}  id
    \    APPEND TO LIST  ${result}  ${id}  ${boardName}  ${description}  ${ownerId}
    Log  ${result}
    [Return]  ${result}

Create expected result board list
    [Arguments]  @{db}
    ${result}  create list
    :FOR    ${ELEMENT}    IN    @{db}
    \    Log    ${ELEMENT}
    \    APPEND TO LIST  ${result}  ${ELEMENT[0]}  ${ELEMENT[1]}  ${ELEMENT[2]}  ${ELEMENT[3]}
    Log  ${result}
    [Return]  ${result}