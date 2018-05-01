package ru.compito.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tasktemplates")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TaskTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tasktemplate_id")
    private Integer id;

    @Column(name="tasktemplate_name")
    private String taskTemplateName;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "task_attributes",
            joinColumns = {@JoinColumn(name = "tasktemplate_id")},
            inverseJoinColumns = {@JoinColumn(name = "attribute_id")})
    private List<Attribute> attributes = new ArrayList<>();

    @Column(name="is_default")
    private Boolean isDefault;

    public TaskTemplate() {
    }

    public TaskTemplate(String taskTemplateName, List<Attribute> attributes, Boolean isDefault) {
        this.taskTemplateName = taskTemplateName;
        this.attributes = attributes;
        this.isDefault = isDefault;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskTemplateName() {
        return taskTemplateName;
    }

    public void setTaskTemplateName(String taskTemplateName) {
        this.taskTemplateName = taskTemplateName;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TaskTemplate that = (TaskTemplate) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Long.valueOf(getId()).hashCode();
    }
}
