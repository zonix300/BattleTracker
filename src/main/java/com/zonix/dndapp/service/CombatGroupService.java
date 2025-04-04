package com.zonix.dndapp.service;

import com.zonix.dndapp.entity.CombatGroup;
import com.zonix.dndapp.entity.Combatant;
import com.zonix.dndapp.entity.TemplateCreature;
import com.zonix.dndapp.repository.CombatantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class CombatGroupService {
    private final CombatantRepository combatantRepository;
    private final CombatantService combatantService;
    private final List<CombatGroup> activeCombatGroups = new ArrayList<>();

    public CombatGroupService(CombatantRepository combatantRepository, CombatantService combatantService) {
        this.combatantRepository = combatantRepository;
        this.combatantService = combatantService;
    }

    public CombatGroup addCombatGroup(Long templateId, int count) {

        TemplateCreature templateCreature = combatantRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Combatant not found"));

        List<Combatant> combatGroupList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Combatant combatant = new Combatant(templateCreature);
            combatantService.addCombatant(combatant);
            combatGroupList.add(combatant);
        }
        CombatGroup combatGroup = new CombatGroup(templateCreature.getName() + "'s group", combatGroupList);
        activeCombatGroups.add(combatGroup);
        return combatGroup;
    }

    public List<CombatGroup> getActiveCombatGroups() {
        return Collections.unmodifiableList(activeCombatGroups);
    }

    public void heal(CombatGroup combatGroup, int amount) {
        for (Combatant combatant : combatGroup.getMembers()) {
            combatantService.heal(combatant, amount);
        }
    }

    public void dealDamage(CombatGroup combatGroup, int amount) {
        for (Combatant combatant : combatGroup.getMembers()) {
            combatantService.dealDamage(combatant, amount);
        }
    }
}
