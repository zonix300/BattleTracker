package com.zonix.dndapp.entity;

public interface TurnQueueItem {
    long getId();
    String getName();
    int armorClass();
    int getInitiative();
    int getDexterity();
    TurnItemType getType();
}
