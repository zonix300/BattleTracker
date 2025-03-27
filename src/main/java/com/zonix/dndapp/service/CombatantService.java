package com.zonix.dndapp.service;

import com.zonix.dndapp.entity.Combatant;
import com.zonix.dndapp.repository.CombatantRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CombatantService {
    private final CombatantRepository combatantRepository;
    private int currentTurnIndex = 0;

    public CombatantService(CombatantRepository combatantRepository) {
        this.combatantRepository = combatantRepository;
    }
    public Combatant addCombatant(Combatant combatant) {
        return combatantRepository.save(combatant);
    }
    public List<Combatant> getAllCombatants() {
        return combatantRepository.findAll();
    }
    public void deleteCombatant(Long id) {
        combatantRepository.deleteById(id);
    }

    public void nextTurn() {
        List<Combatant> combatants = combatantRepository.findAll();

        if(combatants.isEmpty()) {
            return;
        }

        currentTurnIndex = (currentTurnIndex + 1) % combatants.size();
    }

    public int getCurrentTurnIndex() {
        return currentTurnIndex;
    }
}
