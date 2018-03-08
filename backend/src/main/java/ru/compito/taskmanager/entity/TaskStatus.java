package ru.compito.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name="task_status")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="status_name")
    private String statusName;

    public TaskStatus() {
    }

    public TaskStatus(String statusName) {
        this.statusName = statusName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
