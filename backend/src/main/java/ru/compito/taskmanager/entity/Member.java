package ru.compito.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="members")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Member {

    @JsonIgnore
    @EmbeddedId
    private MemberIdentity memberIdentity;

    @MapsId( "userId" )
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @MapsId( "boardId" )
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    private Role role;

    public Member() {
    }

    public Member(User user, Board board, Role role) {
        this.user = user;
        this.board = board;
        this.role = role;
        this.memberIdentity = new MemberIdentity(user.getId(), board.getId());
    }

    public Member(MemberIdentity memberIdentity, Role role) {
        this.memberIdentity = memberIdentity;
        this.role = role;
    }

    public MemberIdentity getMemberIdentity() {
        return memberIdentity;
    }

    public void setMemberIdentity(MemberIdentity memberIdentity) {
        this.memberIdentity = memberIdentity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(user, member.user) &&
                Objects.equals(board, member.board);
    }

    @Override
    public int hashCode() {

        return Objects.hash(user, board);
    }
}