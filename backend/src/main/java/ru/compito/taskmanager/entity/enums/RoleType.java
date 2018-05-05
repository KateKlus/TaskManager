package ru.compito.taskmanager.entity.enums;

import ru.compito.taskmanager.exception.TypeNotFoundException;

public enum RoleType {
    OWNER,ADMIN,MODERATOR,DEVELOPER,VIEWER;
    public static RoleType findType(String text) {
        if (text == null) {
            throw new TypeNotFoundException(String.format("Role type must not be empty!"));
        }
        for (RoleType roleType : RoleType.values()) {
            if (roleType.toString().equalsIgnoreCase(text)) {
                return roleType;
            }
        }
        throw new TypeNotFoundException(String.format("Role type=%s is not found!", text));
    }
}
