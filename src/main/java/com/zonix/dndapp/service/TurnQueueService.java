package com.zonix.dndapp.service;

import com.zonix.dndapp.dto.HpChangeType;
import com.zonix.dndapp.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class TurnQueueService {
    private final List<TurnQueueItem> turnQueueItems = new ArrayList<>();
    private final CombatantService combatantService;
    private final CombatGroupService combatGroupService;

    private static final Logger logger = LoggerFactory.getLogger(TurnQueueService.class);

    public TurnQueueService(CombatantService combatantService, CombatGroupService combatGroupService) {
        this.combatantService = combatantService;
        this.combatGroupService = combatGroupService;
    }

    public List<TurnQueueItem> addItems(Long templateId, int amount) {
        if (amount == 1) {
            turnQueueItems.add(combatantService.addCombatant(templateId));
        } else {
            turnQueueItems.add(combatGroupService.addCombatGroup(templateId, amount));
        }
        sortByInitiative();
        return turnQueueItems;
    }

    public List<TurnQueueItem> remove(Set<Long> itemIds) {
        List<TurnQueueItem> updatedTurnQueueItems = new ArrayList<>();
        for (Long itemId : itemIds) {
            TurnQueueItem item = findItemById(itemId);
            if (item == null) continue;

            if (item instanceof CombatGroup group) {
                handleGroupRemoval(group);
            } else if (item instanceof Combatant combatant) {
                handleCombatantRemoval(combatant);
            }
            updatedTurnQueueItems.add(item);
        }
        return updatedTurnQueueItems;
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

    public List<TurnQueueItem> updateHp(Set<Long> itemIds, int amount, HpChangeType type) {
        List<TurnQueueItem> updatedTurnQueueItems = new ArrayList<>();
        for (Long itemId : itemIds) {
            TurnQueueItem item = findItemById(itemId);
            if (item == null) continue;

            if (item.getType() == TurnItemType.INDIVIDUAL) {
                Combatant combatant = (Combatant) item;
                applyHpChange(combatant, type, amount);
            }

            if (item.getType() == TurnItemType.GROUP) {
                CombatGroup group = (CombatGroup) item;
                for (Combatant combatant : group.getMembers()) {
                    applyHpChange(combatant, type, amount);
                }
            }
            updatedTurnQueueItems.add(item);
        }
        return updatedTurnQueueItems;
    }

    private void applyHpChange(Combatant combatant, HpChangeType type, int amount) {
        switch (type) {
            case HEAL -> {
                int newHp = Math.min(combatant.getMaxHp(), combatant.getCurrentHp() + amount);
                combatant.setCurrentHp(newHp);
                logHealing(combatant, amount, newHp);
            }
            case DAMAGE -> {
                int newHp = Math.max(0, combatant.getCurrentHp() - amount);
                combatant.setCurrentHp(newHp);
                logDamage(combatant, amount, newHp);
            }
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

    public void updateEffects(Long itemId, Set<StatusEffect> newEffects, boolean shouldAdd) {
        var item = findItemById(itemId);
        if (item.getType() == TurnItemType.INDIVIDUAL) {
            Combatant combatant = (Combatant) item;
            newEffects.forEach(effect -> {
                if (shouldAdd) {
                    combatant.getStatusEffects().add(effect);
                } else {
                    combatant.getStatusEffects().remove(effect);
                }
            });
        }

    }
    public void sortByInitiative() {
        turnQueueItems.sort(Comparator.comparingInt(TurnQueueItem::getInitiative).reversed());
    }

    public TurnQueueItem findItemById(Long itemId) {
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

    private void logHealing(Combatant combatant, int amount, int newHp) {
        logger.info("Healed {} for {} HP ({} -> {}/{})",
                combatant.getName(), amount,
                combatant.getCurrentHp() - amount, newHp, combatant.getMaxHp());
    }

    private void logDamage(Combatant combatant, int amount, int newHp) {
        logger.info("Damaged {} for {} HP ({} -> {}/{})",
                combatant.getName(), amount,
                combatant.getCurrentHp() + amount, newHp, combatant.getMaxHp());
    }

}
