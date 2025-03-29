package com.zonix.dndapp.controller;

import com.zonix.dndapp.entity.Combatant;
import com.zonix.dndapp.service.CombatantService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("battle_tracker")
public class BattleTrackerController {
    private final CombatantService combatantService;


    public BattleTrackerController(CombatantService combatantService) {
        this.combatantService = combatantService;
    }

    @GetMapping
    public String showBattleTracker(Model model) {
        model.addAttribute("availableCreatures", combatantService.getAvailableCreatures());
        model.addAttribute("activeCombatants", combatantService.getActiveCombatants());
        return "battle_tracker";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addToCombat(@RequestParam Long combatantId) {
        // Add selected combatant to the combat
        Combatant newCombatant = combatantService.addToCombat(combatantId);
        Map<String, Object> response = new HashMap<>();
        response.put("newCombatant", newCombatant); // Return the updated list of active combatants
        return ResponseEntity.ok(response);
    }


    @PostMapping("/delete")
    public String deleteCombatants(@RequestParam Long id) {
        combatantService.deleteCombatant(id);
        return "redirect:/battle_tracker";
    }

    @PostMapping("/nextTurn")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> nextTurn() {
        combatantService.nextTurn();
        Map<String, Object> response = new HashMap<>();
        response.put("currentTurnIndex", combatantService.getCurrentTurnIndex());
        response.put("activeCombatants", combatantService.getActiveCombatants());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/activeCombatants")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getActiveCombatants() {
        Map<String, Object> response = new HashMap<>();
        response.put("activeCombatants", combatantService.getActiveCombatants());
        response.put("currentTurnIndex", combatantService.getCurrentTurnIndex());
        return ResponseEntity.ok(response);
    }

}
