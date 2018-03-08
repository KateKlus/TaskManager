package ru.compito.taskmanager.entity;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BoardStatusIdentity implements Serializable {
    @NotNull
    private Integer taskStatusId;
    @NotNull
    private Integer boardId;

    public BoardStatusIdentity() {
    }

    public BoardStatusIdentity(Integer taskStatusId, Integer boardId) {
        this.taskStatusId = taskStatusId;
        this.boardId = boardId;
    }

    public Integer getTaskStatusId() {
        return taskStatusId;
    }

    public void setTaskStatusId(Integer taskStatusId) {
        this.taskStatusId = taskStatusId;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardStatusIdentity that = (BoardStatusIdentity) o;
        return Objects.equals(taskStatusId, that.taskStatusId) &&
                Objects.equals(boardId, that.boardId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(taskStatusId, boardId);
    }
}
