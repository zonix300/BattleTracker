package com.zonix.dndapp.service;

import com.zonix.dndapp.entity.TemplateCreature;
import com.zonix.dndapp.entity.Combatant;
import com.zonix.dndapp.repository.CombatantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    public List<Combatant> getActiveCombatants() {
        return Collections.unmodifiableList(activeCombatants);
    }

    public TemplateCreature addToCombat(Long combatantId, int amount) {
        TemplateCreature templateCreature = combatantRepository.findById(combatantId)
                .orElseThrow(() -> new RuntimeException("Combatant not found"));

        for (int i = 0; i < amount; i++) {
            Combatant combatant = new Combatant(templateCreature, activeCombatants.size());
            activeCombatants.add(combatant);
        }


        return templateCreature;
    }

    public void nextTurn() {
        if (activeCombatants.isEmpty()) return;

        if (roundStartCombatant == null) {
            roundStartCombatant = activeCombatants.get(0);
        }

        Combatant first = activeCombatants.remove(0);
        activeCombatants.add(first);

        boolean isNewRound = activeCombatants.get(0).equals(roundStartCombatant);
        if (isNewRound) {
            currentRound++;
            roundStartCombatant = activeCombatants.get(0);
        }
        for(int i = 0; i < activeCombatants.size(); i++) {
            activeCombatants.get(i).setTurnOrder(i);
        }
    }

    public void dealDamage(int index, int amount) {
        Combatant combatant = activeCombatants.get(index);
        int newCurrentHp = Math.max(0, combatant.getCurrentHp() - amount);
        combatant.setCurrentHp(newCurrentHp);

    }
    public void heal(int index, int amount) {
        Combatant combatant = activeCombatants.get(index);
        int newCurrentHp = Math.min(1000, combatant.getCurrentHp() + amount);
        combatant.setCurrentHp(newCurrentHp);
    }

    public void remove(int index) {
        if (roundStartCombatant == activeCombatants.get(index)) {
            roundStartCombatant = null;
        }
        activeCombatants.remove(index);

    }

    public int getCurrentRound() {
        return currentRound;
    }
}
