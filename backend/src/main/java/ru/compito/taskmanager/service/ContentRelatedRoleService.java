package ru.compito.taskmanager.service;

import org.springframework.security.core.Authentication;
import ru.compito.taskmanager.entity.Board;

import java.security.Principal;

public interface ContentRelatedRoleService {
    boolean isContentOwner(Integer boardId, Authentication authentication);
    boolean isContentAdministrator(Integer boardId, Authentication authentication);
    boolean isContentModerator(Integer boardId, Authentication authentication);
    boolean isContentDeveloper(Integer boardId, Authentication authentication);
    boolean isContentViewer(Integer boardId, Authentication authentication);
}
