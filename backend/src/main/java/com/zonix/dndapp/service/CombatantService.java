package com.zonix.dndapp.service;

import com.zonix.dndapp.entity.Combatant;
import com.zonix.dndapp.entity.TemplateCreature;
import com.zonix.dndapp.entity.TurnQueueItem;
import com.zonix.dndapp.repository.TemplateCreatureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CombatantService {
    private final TemplateCreatureRepository templateCreatureRepository;
    private final List<Combatant> activeCombatants = new ArrayList<>();
    private Combatant roundStartCombatant = null;

    private int currentRound = 1;


    public CombatantService(TemplateCreatureRepository templateCreatureRepository) {
        this.templateCreatureRepository = templateCreatureRepository;
    }

    public List<TemplateCreature> getAvailableCreatures() {
        return templateCreatureRepository.findAll();
    }

    public Combatant addCombatant(Long templateId) {
        Combatant combatant = templateCreatureRepository.findCreatureSummariesById(templateId)
                .orElseThrow(() -> new RuntimeException("Combatant not found"));
        System.out.println(combatant.getCurrentHp());

        activeCombatants.add(combatant);
        return combatant;
    }

    public void addCombatant(Combatant combatant) {
        activeCombatants.add(combatant);
        System.out.println("Combatant id to add: " + combatant.getId());
    }

    public void heal(Combatant combatant, int amount) {
        int newCurrentHp = Math.min(combatant.getMaxHp(), combatant.getCurrentHp() + amount);
        combatant.setCurrentHp(newCurrentHp);
    }

    public void dealDamage(Combatant combatant, int amount) {
        int newCurrentHp = Math.max(0, combatant.getCurrentHp() - amount);
        combatant.setCurrentHp(newCurrentHp);

    }

    public int getCurrentRound() {
        return currentRound;
    }
}
