package com.zonix.dndapp.service;

import com.zonix.dndapp.entity.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class TurnQueueService {
    private final List<TurnQueueItem> turnQueueItems = new ArrayList<>();
    private final CombatantService combatantService;
    private final CombatGroupService combatGroupService;

    public TurnQueueService(CombatantService combatantService, CombatGroupService combatGroupService) {
        this.combatantService = combatantService;
        this.combatGroupService = combatGroupService;
    }

    public void addItems(Long templateId, int amount) {
        if (amount == 1) {
            turnQueueItems.add(combatantService.addCombatant(templateId));
        } else {
            turnQueueItems.add(combatGroupService.addCombatGroup(templateId, amount));
        }
        sortByInitiative();
    }

    public List<TurnQueueItem> getAllItems() {
        return turnQueueItems;
    }

    public void nextTurn() {
        if (!turnQueueItems.isEmpty()) {
            TurnQueueItem current = turnQueueItems.remove(0);
            turnQueueItems.add(current);
        }
    }

    public void applyHeal(int itemId, int amount) {
        TurnQueueItem turnQueueItem = findItemById(itemId);
        if (turnQueueItem.getType() == TurnItemType.INDIVIDUAL) {
            combatantService.heal((Combatant) turnQueueItem, amount);
        }
        if (turnQueueItem.getType() == TurnItemType.GROUP) {
            combatGroupService.heal((CombatGroup) turnQueueItem, amount);
        }
    }

    public void applyDamage(int itemId, int amount) {
        TurnQueueItem turnQueueItem = findItemById(itemId);
        if (turnQueueItem.getType() == TurnItemType.INDIVIDUAL) {
            combatantService.dealDamage((Combatant) turnQueueItem, amount);
        }
        if (turnQueueItem.getType() == TurnItemType.GROUP) {
            combatGroupService.dealDamage((CombatGroup) turnQueueItem, amount);
        }
    }

    public void applyEffect(int itemId, String effect) {
        TurnQueueItem turnQueueItem = findItemById(itemId);
        if (turnQueueItem instanceof Combatant combatant) {
            StatusEffect statusEffect = StatusEffect.valueOf(effect);
            combatant.getStatusEffects().add(statusEffect);
        }
    }

    public void removeEffect(int itemId, String effect) {
        TurnQueueItem turnQueueItem = findItemById(itemId);
        if (turnQueueItem instanceof Combatant combatant) {
            StatusEffect statusEffect = StatusEffect.valueOf(effect);
            combatant.getStatusEffects().remove(statusEffect);
        }
    }
    public void remove(int itemId) {
        TurnQueueItem item = findItemById(itemId);
        if (item == null) return;

        if (item instanceof CombatGroup group) {
            handleGroupRemoval(group);
        } else if (item instanceof Combatant combatant) {
            handleCombatantRemoval(combatant);
        }
    }

    private void handleGroupRemoval(CombatGroup group) {
        // Clear group references from members
        group.getMembers().forEach(c -> c.setGroupId(null));
        turnQueueItems.remove(group);
    }

    private void handleCombatantRemoval(Combatant combatant) {
        Long groupId = combatant.getGroupId();
        if (groupId != null) {
            CombatGroup group = findCombatGroupById(groupId.intValue());
            if (group != null) {
                group.getMembers().remove(combatant);
                cleanUpEmptyGroup(group);
            }
        }
        turnQueueItems.remove(combatant);
    }

    private void cleanUpEmptyGroup(CombatGroup group) {
        if (group.getMembers().isEmpty()) {
            turnQueueItems.remove(group);
        }
    }
    public void sortByInitiative() {
        turnQueueItems.sort(Comparator.comparingInt(TurnQueueItem::getInitiative).reversed());
    }

    public TurnQueueItem findItemById(int itemId) {
        for (TurnQueueItem item : turnQueueItems) {
            if (item.getId() == itemId) return item;

            if (item instanceof CombatGroup group) {  // Pattern matching
                for (Combatant combatant : group.getMembers()) {
                    if (combatant.getId() == itemId) {
                        return combatant;
                    }
                }
            }
        }
        return null;
    }

    public CombatGroup findCombatGroupById(int groupId) {
        return turnQueueItems.stream()
                .filter(CombatGroup.class::isInstance)
                .map(CombatGroup.class::cast)
                .filter(group -> group.getGroupId() == groupId)
                .findFirst()
                .orElse(null);
    }


}
