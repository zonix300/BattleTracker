package com.zonix.dndapp.entity;

import com.zonix.dndapp.service.IdGeneratorService;

import java.util.List;

public class CombatGroup implements TurnQueueItem {
    private Long groupId;
    private String groupName;
    private Integer groupInitiative;
    private List<Combatant> members;
    private final TurnItemType groupType = TurnItemType.GROUP;

    public CombatGroup(String groupName, List<Combatant> members) {
        this.groupId = IdGeneratorService.generateId();
        this.groupName = groupName;
        this.members = members;
        this.groupInitiative = this.members.get(0).getInitiative();
        for (Combatant member : this.members) {
            member.setGroupId(this.groupId);
        }
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Combatant> getMembers() {
        return members;
    }

    public void setMembers(List<Combatant> members) {
        this.members = members;
    }


    @Override
    public Long getId() {
        return groupId;
    }

    @Override
    public String getName() {
        return groupName;
    }

    @Override
    public Integer getInitiative() {
        return groupInitiative;
    }

    @Override
    public TurnItemType getType() {
        return groupType;
    }
}
