package com.zonix.dndapp.service;

import com.zonix.dndapp.entity.Combatant;
import com.zonix.dndapp.entity.TurnTracker;
import com.zonix.dndapp.repository.CombatantRepository;
import com.zonix.dndapp.repository.TurnTrackerRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CombatantService {
    private final CombatantRepository combatantRepository;
    private final TurnTrackerRepository turnTrackerRepository;
    private int currentTurnIndex = 0;

    public CombatantService(CombatantRepository combatantRepository, TurnTrackerRepository turnTrackerRepository) {
        this.combatantRepository = combatantRepository;
        this.turnTrackerRepository = turnTrackerRepository;
    }
    public Combatant addCombatant(Combatant combatant) {
        return combatantRepository.save(combatant);
    }
    public List<Combatant> getAvailableCreatures() {
        return combatantRepository.findAll();
    }

    public List<TurnTracker> getActiveCombatants() {
        return turnTrackerRepository.findAllByOrderByTurnOrderAsc();
    }

    public Combatant addToCombat(Long combatantId) {
        Combatant combatant = combatantRepository.findById(combatantId)
                .orElseThrow(() -> new RuntimeException("Combatant not found"));

        TurnTracker turnTracker = new TurnTracker();
        turnTracker.setCombatant(combatant);
        turnTracker.setTurnOrder((int) turnTrackerRepository.count());
        turnTrackerRepository.save(turnTracker);
        return combatant;
    }
    public void deleteCombatant(Long id) {
        combatantRepository.deleteById(id);
    }

    public void nextTurn() {
        List<TurnTracker> turnOrder = turnTrackerRepository.findAllByOrderByTurnOrderAsc();
        if (turnOrder.isEmpty()) return;

        TurnTracker first = turnOrder.remove(0);
        turnOrder.add(first);

        int order = 0;
        for(TurnTracker turn : turnOrder) {
            turn.setTurnOrder(order++);
            turnTrackerRepository.save(turn);
        }
    }

    public int getCurrentTurnIndex() {
        return currentTurnIndex;
    }
}
