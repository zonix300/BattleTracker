package com.zonix.dndapp.controller;

import com.zonix.dndapp.entity.TurnQueueItem;
import com.zonix.dndapp.service.CombatGroupService;
import com.zonix.dndapp.service.CombatantService;
import com.zonix.dndapp.service.TurnQueueService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("battle_tracker")
public class BattleTrackerController {
    private final CombatantService combatantService;
    private final CombatGroupService combatGroupService;

    private final TurnQueueService turnQueueService;

    private final SpringTemplateEngine templateEngine;


    public BattleTrackerController(CombatantService combatantService, CombatGroupService combatGroupService, TurnQueueService turnQueueService, SpringTemplateEngine templateEngine) {
        this.combatantService = combatantService;
        this.combatGroupService = combatGroupService;
        this.turnQueueService = turnQueueService;
        this.templateEngine = templateEngine;
    }

    @GetMapping
    public String showBattleTracker(Model model) {
        model.addAttribute("availableCreatures", combatantService.getAvailableCreatures());
        model.addAttribute("turnQueueItems", turnQueueService.getAllItems());
        return "battle_tracker.html";
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

    @PostMapping("/nextTurn")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> nextTurn() {
        turnQueueService.nextTurn();
        Map<String, Object> response = new HashMap<>();
        response.put("turnQueueItems", turnQueueService.getAllItems());
        response.put("roundCounter", combatantService.getCurrentRound());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/renderCombatants")
    @ResponseBody
    public String renderAllCombatant(
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse) {

        List<TurnQueueItem> combatants = turnQueueService.getAllItems();

        WebContext context = new WebContext(
                JakartaServletWebApplication
                        .buildApplication(servletRequest.getServletContext())
                        .buildExchange(servletRequest, servletResponse),
                servletRequest.getLocale(),
                Map.of("items", combatants)
        );

        return templateEngine.process("combatant.html", context);
    }


}
