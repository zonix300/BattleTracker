package com.zonix.dndapp.entity;

import com.zonix.dndapp.util.BaseAction;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "reactions")
public class Reaction implements BaseAction {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    public Reaction() {}

    public Reaction(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
