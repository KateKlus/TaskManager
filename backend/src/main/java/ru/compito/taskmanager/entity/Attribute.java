package ru.compito.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="attributes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_id")
    private Integer id;

    @Column(name="attribute_name")
    private String attributeName;

    @Column(name="attribute_type")
    private String attributeType;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "task_attributes",
            joinColumns = {@JoinColumn(name = "attribute_id")},
            inverseJoinColumns = {@JoinColumn(name = "tasktemplate_id")})
    private List<TaskTemplate> taskTemplates = new ArrayList<>();

    @Column(name="is_obligatory")
    private Boolean isObligatory;

    @Column(name="is_default")
    private Boolean isDefault;

    public Attribute() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public List<TaskTemplate> getTaskTemplates() {
        return taskTemplates;
    }

    public void setTaskTemplates(List<TaskTemplate> taskTemplates) {
        this.taskTemplates = taskTemplates;
    }

    public Boolean getObligatory() {
        return isObligatory;
    }

    public void setObligatory(Boolean obligatory) {
        isObligatory = obligatory;
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
        Attribute that = (Attribute) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Long.valueOf(getId()).hashCode();
    }
}
