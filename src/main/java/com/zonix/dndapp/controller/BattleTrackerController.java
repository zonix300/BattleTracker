package com.zonix.dndapp.controller;

import com.zonix.dndapp.entity.TemplateCreature;
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
    public ResponseEntity<Map<String, Object>> addToCombat(@RequestParam Long combatantId, @RequestParam int amount) {
       TemplateCreature newTemplateCreature = combatantService.addToCombat(combatantId, amount);

       Map<String, Object> response = new HashMap<>();
       response.put("newCombatant", newTemplateCreature);
       response.put("activeCombatants", combatantService.getActiveCombatants());

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
        response.put("currentRound", combatantService.getCurrentRound());
        response.put("activeCombatants", combatantService.getActiveCombatants());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/activeCombatants")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getActiveCombatants() {
        Map<String, Object> response = new HashMap<>();
        response.put("activeCombatants", combatantService.getActiveCombatants());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/heal")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> heal(@RequestParam int index, @RequestParam int amount) {
        combatantService.heal(index, amount);
        Map<String, Object> response = new HashMap<>();
        response.put("activeCombatants", combatantService.getActiveCombatants());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/damage")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> dealDamage(@RequestParam int index, @RequestParam int amount) {
        combatantService.dealDamage(index, amount);
        Map<String, Object> response = new HashMap<>();
        response.put("activeCombatants", combatantService.getActiveCombatants());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/remove")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> remove(@RequestParam int index) {
        combatantService.remove(index);
        Map<String, Object> response = new HashMap<>();
        response.put("activeCombatants", combatantService.getActiveCombatants());
        return ResponseEntity.ok(response);
    }
}
