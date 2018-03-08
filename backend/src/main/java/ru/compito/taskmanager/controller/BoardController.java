package ru.compito.taskmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.compito.taskmanager.entity.Board;
import ru.compito.taskmanager.entity.Task;
import ru.compito.taskmanager.entity.User;
import ru.compito.taskmanager.service.BoardService;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    
    @Autowired
    private BoardService boardService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Board> getAll() {
        return boardService.findAll();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Board getBoardById(@PathVariable Integer id) {
        return boardService.getOne(id);
    }

    @GetMapping(value = "/{id}/owner", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getBoardOwner(@PathVariable Integer id){
        return boardService.getBoardOwner(id);
    }

    @GetMapping(value = "/{id}/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getUsersFromBoard(@PathVariable Integer id){
        return boardService.getUsers(id);
    }

    @GetMapping(value = "/{id}/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getTasksFromBoard(@PathVariable Integer id){
        return boardService.getTasks(id);
    }

    @GetMapping(value = "/{id}/tasks/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Task> getTaskByIdFromBoard(@PathVariable Integer id, @PathVariable Integer taskId){
        return boardService.getTaskById(id,taskId);
    }

    @PutMapping(value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable Integer id, @RequestBody Board board) {
        boardService.update(board);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        boardService.delete(id);
    }

}
