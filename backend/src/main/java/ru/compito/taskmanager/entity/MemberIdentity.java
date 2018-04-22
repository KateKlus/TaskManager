package ru.compito.taskmanager.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MemberIdentity implements Serializable {

    @NotNull
    @Column(name = "userId")
    private Integer userId;

    @NotNull
    @Column(name = "boardId")
    private Integer boardId;

    public MemberIdentity() {
    }

    public MemberIdentity(Integer userId, Integer boardId) {
        this.userId = userId;
        this.boardId = boardId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
        MemberIdentity that = (MemberIdentity) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(boardId, that.boardId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId, boardId);
    }
}
