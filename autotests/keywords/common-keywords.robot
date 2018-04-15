*** Settings ***
Library  requests
Library  Collections
Library  DatabaseLibrary
Library  OperatingSystem
Library  json

Resource  ../tests/variables.robot

*** Keywords ***
Create actual result task list
    [Arguments]  @{json}
    ${result}  create list
    :FOR    ${ELEMENT}    IN    @{json}
    \    Log    ${ELEMENT}
    \    ${id}  Get From Dictionary  ${ELEMENT}  id
    \    ${taskName}  Get From Dictionary  ${ELEMENT}  taskName
    \    ${author}  Get From Dictionary  ${ELEMENT}  author
    \    ${authorName}  Get From Dictionary  ${author}  username
    \    ${authorId}  Get From Dictionary  ${author}  id
    \    ${board}  Get From Dictionary  ${ELEMENT}  board
    \    ${boardId}  Get From Dictionary  ${board}  id
    \    APPEND TO LIST  ${result}  ${id}  ${taskName}  ${authorName}  ${authorId}  ${boardId}
    Log  ${result}
    [Return]  ${result}

Create expected result task list
    [Arguments]  @{db}
    ${result}  create list
    :FOR    ${ELEMENT}    IN    @{db}
    \    Log    ${ELEMENT}
    \    APPEND TO LIST  ${result}  ${ELEMENT[0]}  ${ELEMENT[1]}  ${ELEMENT[2]}  ${ELEMENT[3]}  ${ELEMENT[4]}
    Log  ${result}
    [Return]  ${result}

Create actual result user list
    [Arguments]  @{json}
    ${result}  create list
    :FOR    ${ELEMENT}    IN    @{json}
    \    Log    ${ELEMENT}
    \    ${username}  Get From Dictionary  ${ELEMENT}  username
    \    ${email}  Get From Dictionary  ${ELEMENT}  email
    \    APPEND TO LIST  ${result}  ${username}  ${email}
    Log  ${result}
    [Return]  ${result}

Create expected result user list
    [Arguments]  @{db}
    ${result}  create list
    :FOR    ${ELEMENT}    IN    @{db}
    \    Log    ${ELEMENT}
    \    APPEND TO LIST  ${result}  ${ELEMENT[0]}  ${ELEMENT[1]}
    Log  ${result}
    [Return]  ${result}