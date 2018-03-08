package ru.compito.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="board_status")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BoardStatus {

    @EmbeddedId
    private BoardStatusIdentity boardStatusIdentity;

    @Column(name="priority_level")
    private Integer priorityLevel;

    @MapsId( "boardId" )
    @ManyToOne
    private Board board;

    @MapsId( "taskStatusId" )
    @ManyToOne
    private TaskStatus taskStatus;

    public BoardStatus() {
    }

    public BoardStatus(Board board, TaskStatus taskStatus) {
        this.board = board;
        this.taskStatus = taskStatus;
    }

    public BoardStatus(BoardStatusIdentity boardStatusIdentity, Integer priorityLevel) {
        this.boardStatusIdentity = boardStatusIdentity;
        this.priorityLevel = priorityLevel;
    }

    public BoardStatusIdentity getBoardStatusIdentity() {
        return boardStatusIdentity;
    }

    public void setBoardStatusIdentity(BoardStatusIdentity boardStatusIdentity) {
        this.boardStatusIdentity = boardStatusIdentity;
    }

    public Integer getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(Integer priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
