package com.zonix.dndapp.entity;

import jakarta.persistence.*;

@Entity
public class CreatureAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creature_id")
    private TemplateCreature creature;
}
