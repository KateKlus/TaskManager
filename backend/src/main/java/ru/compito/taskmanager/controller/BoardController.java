package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.config.ServiceConstants;
import ru.compito.taskmanager.entity.*;
import ru.compito.taskmanager.service.BoardService;
import ru.compito.taskmanager.service.ContentRelatedRoleService;

import java.util.List;

@RestController
@RequestMapping(value = ServiceConstants.BOARD_PATH)
public class BoardController {
    
    @Autowired
    private BoardService boardService;
    @Autowired
    private ContentRelatedRoleService contentRelatedRoleService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getAll() {
        List<Board> boards = boardService.findAll();
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @GetMapping(value = "/{boardId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Board> getBoardById(@PathVariable Integer boardId) {
        Board board = boardService.getOne(boardId);
        if(board==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(board, HttpStatus.OK);
    }

    @GetMapping(value = "/{boardId}/owner/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<User> getBoardOwner(@PathVariable Integer boardId){
        User user = boardService.getBoardOwner(boardId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/{boardId}/users/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getUsersFromBoard(@PathVariable Integer boardId){
        List<User> users = boardService.getUsersById(boardId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/{boardId}/tasks/", produces = MediaType.APPLICATION_JSON_VALUE)
    public  @ResponseBody ResponseEntity<Object> getTasksFromBoard(@PathVariable Integer boardId){
        List<Task> tasks = boardService.getTasks(boardId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping(value = "/{boardId}/tasks/{taskId}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getTaskByIdFromBoard(@PathVariable Integer boardId, @PathVariable Integer taskId){
        List<Task> tasks = boardService.getTaskById(boardId,taskId);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping(value = "/{boardId}/statuses/", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> getBoardStatusesFromBoard(@PathVariable Integer boardId){
        List<BoardStatus> boardStatuses = boardService.getBoardStatuses(boardId);
        return new ResponseEntity<>(boardStatuses, HttpStatus.OK);
    }

    @PostMapping(value = "/{boardId}/statuses/", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<BoardStatus> addBoardStatus(@RequestBody TaskStatus taskStatus, @PathVariable Integer boardId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(contentRelatedRoleService.isContentOwner(boardId,authentication) ||
                contentRelatedRoleService.isContentAdministrator(boardId,authentication)||
                contentRelatedRoleService.isContentModerator(boardId,authentication)) {
            BoardStatus boardStatus = boardService.addBoardStatus(boardId, taskStatus);
            return new ResponseEntity<>(boardStatus, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping(value = "/{boardId}/",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Board> update(@PathVariable Integer boardId, @RequestBody Board board) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(contentRelatedRoleService.isContentOwner(boardId,authentication) ||
                contentRelatedRoleService.isContentAdministrator(boardId,authentication)) {
            Board updatedBoard = boardService.update(board);
            return new ResponseEntity<>(updatedBoard, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/{boardId}/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody ResponseEntity<?> delete(@PathVariable Integer boardId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(contentRelatedRoleService.isContentOwner(boardId,authentication)) {
            boardService.delete(boardId);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
