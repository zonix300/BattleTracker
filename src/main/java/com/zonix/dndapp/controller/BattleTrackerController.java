package com.zonix.dndapp.controller;

import com.zonix.dndapp.entity.Combatant;
import com.zonix.dndapp.service.CombatantService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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
        model.addAttribute("combatants", combatantService.getAllCombatants());
        return "battle_tracker";
    }

    @PostMapping("/add")
    public String addCombatants(@RequestParam String name,
                                @RequestParam Integer hp,
                                @RequestParam Integer ac,
                                @RequestParam Integer initiative) {
        combatantService.addCombatant(new Combatant(name, hp, ac, initiative));
        return "redirect:/battle_tracker";
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

        List<Combatant> combatants = combatantService.getAllCombatants();
        combatants.sort(Comparator.comparingInt(Combatant::getInitiative).reversed());

        int currentTurnIndex = combatantService.getCurrentTurnIndex();
        Map<String, Object> response = new HashMap<>();

        response.put("combatants", combatants);
        response.put("currentTurnIndex", currentTurnIndex);
        return ResponseEntity.ok(response);
    }
}
