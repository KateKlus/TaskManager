*** Settings ***
Documentation     Тестирование работы OAPI с объектами attribute
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
Test getting attributes list
    [Tags]    attributes
    [Documentation]  Получение списка атрибутов
    ${actual}  Get  ${API_URL}attributes/
    Should Be Equal  ${actual.status_code}    ${200}
    Log  ${actual.content}
    ${actualList}  Create actual result attribute list  @{actual.json()}
    ${sql}  catenate
    ...  SELECT attributes.attribute_id, attributes.attribute_name, attributes.attribute_type FROM attributes;
    @{db}    Query    ${sql}
    Log  ${db}
    ${expectedlList}  Create expected result attribute list  @{db}
    should be equal  ${actualList}  ${expectedlList}

Test getting tasktemplate attributes list
    [Tags]    tasktemplate attributes
    [Documentation]  Получение списка атрибутов конкретного шаблона
    ${actual}  Get  ${API_URL}tasktemplates/${TASKTEMPLATE_ID}/attributes/
    Should Be Equal  ${actual.status_code}    ${200}
    Log  ${actual.content}
    ${actualList}  Create actual result attribute list  @{actual.json()}
    ${sql}  catenate
    ...  SELECT  task_attributes.attribute_id, attributes.attribute_name, attributes.attribute_type FROM task_attributes
    ...  inner join tasktemplates on tasktemplates.tasktemplate_id=task_attributes.tasktemplate_id
    ...  inner join attributes on attributes.attribute_id=task_attributes.attribute_id
    ...  where tasktemplates.tasktemplate_id=${TASKTEMPLATE_ID};
    @{db}    Query    ${sql}
    Log  ${db}
    ${expectedlList}  Create expected result attribute list  @{db}
    should be equal  ${actualList}  ${expectedlList}

*** Keywords ***
Create actual result attribute list
    [Arguments]  @{json}
    ${result}  create list
    :FOR    ${ELEMENT}    IN    @{json}
    \    Log    ${ELEMENT}
    \    ${id}  Get From Dictionary  ${ELEMENT}  id
    \    ${attributeName}  Get From Dictionary  ${ELEMENT}  attributeName
    \    ${attributeType}  Get From Dictionary  ${ELEMENT}  attributeType
    \    APPEND TO LIST  ${result}  ${id}  ${attributeName} ${attributeType}
    Log  ${result}
    [Return]  ${result}

Create expected result attribute list
    [Arguments]  @{db}
    ${result}  create list
    :FOR    ${ELEMENT}    IN    @{db}
    \    Log    ${ELEMENT}
    \    APPEND TO LIST  ${result}  ${ELEMENT[0]}  ${ELEMENT[1]} ${ELEMENT[2]}
    Log  ${result}
    [Return]  ${result}