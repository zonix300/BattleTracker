package com.zonix.dndapp.controller;

import com.zonix.dndapp.entity.StatusEffect;
import com.zonix.dndapp.service.CombatGroupService;
import com.zonix.dndapp.service.CombatantService;
import com.zonix.dndapp.service.TurnQueueService;
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
    private final CombatGroupService combatGroupService;

    private final TurnQueueService turnQueueService;


    public BattleTrackerController(CombatantService combatantService, CombatGroupService combatGroupService, TurnQueueService turnQueueService) {
        this.combatantService = combatantService;
        this.combatGroupService = combatGroupService;
        this.turnQueueService = turnQueueService;
    }

    @GetMapping
    public String showBattleTracker(Model model) {
        model.addAttribute("availableCreatures", combatantService.getAvailableCreatures());
        model.addAttribute("turnQueueItems", turnQueueService.getAllItems());
        return "battle_tracker";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addToCombat(@RequestParam Long templateId, @RequestParam(defaultValue = "1") int amount) {

        if (amount < 1) {
            throw new IllegalStateException("Amount must be positive");
        }
        Map<String, Object> response = new HashMap<>();

        turnQueueService.addItems(templateId, amount);

        response.put("turnQueueItems", turnQueueService.getAllItems());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/remove")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> remove(@RequestParam int itemId) {
        turnQueueService.remove(itemId);
        Map<String, Object> response = new HashMap<>();
        response.put("turnQueueItems", turnQueueService.getAllItems());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/applyEffect")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> applyEffect(@RequestParam int itemId, @RequestParam String effect) {
        turnQueueService.applyEffect(itemId, effect);
        Map<String, Object> response = new HashMap<>();
        response.put("turnQueueItems", turnQueueService.getAllItems());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/removeEffect")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> removeEffect(@RequestParam int itemId, @RequestParam String effect) {
        turnQueueService.removeEffect(itemId, effect);
        Map<String, Object> response = new HashMap<>();
        response.put("turnQueueItems", turnQueueService.getAllItems());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/nextTurn")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> nextTurn() {
        turnQueueService.nextTurn();
        Map<String, Object> response = new HashMap<>();
        response.put("turnQueueItems", turnQueueService.getAllItems());
        response.put("roundCounter", combatantService.getCurrentRound());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/activeCombatants")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getActiveCombatants() {
        Map<String, Object> response = new HashMap<>();
        response.put("turnQueueItems", turnQueueService.getAllItems());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/heal")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> applyHeal(@RequestParam int itemId, @RequestParam int amount) {
        turnQueueService.applyHeal(itemId, amount);
        Map<String, Object> response = new HashMap<>();
        response.put("turnQueueItems", turnQueueService.getAllItems());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/damage")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> applyDamage(@RequestParam int itemId, @RequestParam int amount) {
        turnQueueService.applyDamage(itemId, amount);
        Map<String, Object> response = new HashMap<>();
        response.put("turnQueueItems", turnQueueService.getAllItems());
        return ResponseEntity.ok(response);
    }


}
