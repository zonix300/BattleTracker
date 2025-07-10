package com.zonix.dndapp.entity;

import com.zonix.dndapp.util.BaseAction;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "legendary_actions")
public class LegendaryAction implements BaseAction {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    public LegendaryAction() {}

    public LegendaryAction(String name, String description) {
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
