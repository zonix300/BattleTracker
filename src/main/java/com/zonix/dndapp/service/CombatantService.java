package com.zonix.dndapp.service;

import com.zonix.dndapp.entity.CombatGroup;
import com.zonix.dndapp.entity.Combatant;
import com.zonix.dndapp.entity.TemplateCreature;
import com.zonix.dndapp.repository.CombatantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CombatantService {
    private final CombatantRepository combatantRepository;
    private final List<Combatant> activeCombatants = new ArrayList<>();
    private Combatant roundStartCombatant = null;

    private int currentRound = 1;


    public CombatantService(CombatantRepository combatantRepository) {
        this.combatantRepository = combatantRepository;
    }

    public List<TemplateCreature> getAvailableCreatures() {
        return combatantRepository.findAll();
    }

    public Combatant addCombatant(Long templateId) {
        TemplateCreature templateCreature = combatantRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Combatant not found"));

        Combatant combatant = new Combatant(templateCreature);
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
