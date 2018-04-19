*** Settings ***
Documentation     Тестирование работы OAPI с объектами tasktemplate
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
${TASKTEMPLATE_ID}  2

*** Test Cases ***
Test getting tasktemplates list
    [Tags]    tasktemplates
    [Documentation]  Получение списка шаблонов
    ${actual}  Get  ${API_URL}tasktemplates/
    Should Be Equal  ${actual.status_code}    ${200}
    Log  ${actual.content}
    ${actualList}  Create actual result tasktemplate list  @{actual.json()}
    ${sql}  catenate
    ...  SELECT tasktemplates.tasktemplate_id, tasktemplates.tasktemplate_name FROM tasktemplates;
    @{db}    Query    ${sql}
    Log  ${db}
    ${expectedlList}  Create expected result tasktemplate list  @{db}
    should be equal  ${actualList}  ${expectedlList}

Test getting tasktemplate info
    [Tags]    tasktemplate
    [Documentation]  Получение информации о конкретном шаблоне
    ${actual}  Get  ${API_URL}tasktemplates/${TASKTEMPLATE_ID}/
    Should Be Equal  ${actual.status_code}    ${200}
    Log  ${actual.content}
    ${actualList}  Create actual result tasktemplate list  ${actual.json()}
    ${sql}  catenate
    ...  SELECT tasktemplates.tasktemplate_id, tasktemplates.tasktemplate_name FROM tasktemplates
    ...  where tasktemplate_id=${TASKTEMPLATE_ID};
    @{db}   Query   ${sql}
    ${expectedlList}  Create expected result tasktemplate list  @{db}
    Should Be Equal  ${actualList}  ${expectedlList}

*** Keywords ***
Create actual result tasktemplate list
    [Arguments]  @{json}
    ${result}  create list
    :FOR    ${ELEMENT}    IN    @{json}
    \    Log    ${ELEMENT}
    \    ${id}  Get From Dictionary  ${ELEMENT}  id
    \    ${tasktemplateName}  Get From Dictionary  ${ELEMENT}  taskTemplateName
    \    APPEND TO LIST  ${result}  ${id}  ${taskTemplateName}
    Log  ${result}
    [Return]  ${result}

Create expected result tasktemplate list
    [Arguments]  @{db}
    ${result}  create list
    :FOR    ${ELEMENT}    IN    @{db}
    \    Log    ${ELEMENT}
    \    APPEND TO LIST  ${result}  ${ELEMENT[0]}  ${ELEMENT[1]}
    Log  ${result}
    [Return]  ${result}