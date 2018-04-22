package ru.compito.taskmanager.config;

public interface ServiceConstants {
    String API_URL = "/api/";
    String USERS = "users";
    String TASKS = "tasks";
    String BOARDS = "boards";
    String ATTRIBUTES = "attributes";
    String CUSTOMFIELDS = "customfields";
    String MEMBERS = "members";
    String ROLES = "roles";
    String STATUSES = "statuses";
    String TASKTEMPLATES = "tasktemplates";


    String USER_PATH = API_URL + USERS;
    String TASK_PATH = API_URL + TASKS;
    String BOARD_PATH = API_URL + BOARDS;
    String ATTRIBUTE_PATH = API_URL + ATTRIBUTES;
    String CUSTOMFIELD_PATH = API_URL + CUSTOMFIELDS;
    String MEMBER_PATH = API_URL + MEMBERS;
    String ROLE_PATH = API_URL + ROLES;
    String STATUS_PATH = API_URL + STATUSES;
    String TASKTEMPLATE_PATH = API_URL + TASKTEMPLATES;
    String REGISTER_PATH = API_URL + "register/";
}
