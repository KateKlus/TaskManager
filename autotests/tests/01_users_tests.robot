# coding: utf-8

*** Settings ***
Documentation     Тестирование работы OAPI с объектами user
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
Test geting list of users
    [Tags]    user
    [Documentation]  Получение списка пользователей
    ${actual}  Get  ${API_URL}users/
    Should Be Equal  ${actual.status_code}    ${200}
    Log  ${actual.content}
    ${actualList}  Create actual result user list  @{actual.json()}
    @{db}    Query  SELECT username, email FROM users;
    ${expectedlList}  Create expected result user list  @{db}
    Should Be Equal  ${actualList}  ${expectedlList}

Test geting user info
    [Tags]    user
    [Documentation]  Получение информации о пользователе
    ${actual}  Get  ${API_URL}users/${USER_ID}/
    Should Be Equal  ${actual.status_code}    ${200}
    Log  ${actual.content}
    ${actualList}  Create actual result user list  ${actual.json()}
    @{db}    Query  SELECT username, email FROM users where user_id=${USER_ID};
    ${expectedlList}  Create expected result user list  @{db}
    Should Be Equal  ${actualList}  ${expectedlList}

Test geting not existing user info
    [Tags]    user
    [Documentation]  Получение информации о не существующем пользователе
    ${actual}  Get  ${API_URL}users/-1/
    Should Be Equal  ${actual.status_code}    ${404}

