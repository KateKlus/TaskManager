package ru.compito.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.compito.taskmanager.entity.enums.RoleType;

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
/*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    private Role role;*/

    @Enumerated(EnumType.STRING)
    @Column(name = "role",
            nullable = false)
    private RoleType role;

    @Column(name="description")
    private String description;

    public Member() {
    }

    public Member(User user, Board board, RoleType role) {
        this.user = user;
        this.board = board;
        this.role = role;
        this.memberIdentity = new MemberIdentity(user.getId(), board.getId());
    }

    public Member(MemberIdentity memberIdentity, RoleType role) {
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

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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