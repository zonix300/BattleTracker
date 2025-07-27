package com.zonix.dndapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "battles")
public class Battle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @OneToOne
    @JoinColumn(name = "current_user_combatant_id")
    private UserCombatant current;

    private int round = 1;

    @OneToMany(mappedBy = "battle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCombatant> userCombatants = new ArrayList<>();

    @ManyToOne
    private User owner;

    private LocalDateTime createdAt;

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

    public UserCombatant getCurrent() {
        return current;
    }

    public void setCurrent(UserCombatant current) {
        this.current = current;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public List<UserCombatant> getUserCombatants() {
        return userCombatants;
    }

    public void setUserCombatants(List<UserCombatant> userCombatants) {
        this.userCombatants = userCombatants;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
