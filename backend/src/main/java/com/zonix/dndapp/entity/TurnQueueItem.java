package com.zonix.dndapp.entity;

public interface TurnQueueItem {
    public Long getId();
    public String getName();
    public Integer getInitiative();
    public TurnItemType getType();
}
