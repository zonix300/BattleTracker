package com.zonix.dndapp.controller;

import com.zonix.dndapp.entity.*;
import com.zonix.dndapp.repository.TemplateCreatureRepository;
import com.zonix.dndapp.service.CombatantService;
import com.zonix.dndapp.service.TurnQueueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/creatures")
public class CreatureController {
    TemplateCreatureRepository templateCreatureRepository;

    TurnQueueService turnQueueService;

    public CreatureController(TemplateCreatureRepository templateCreatureRepository, TurnQueueService turnQueueService) {
        this.templateCreatureRepository = templateCreatureRepository;
        this.turnQueueService = turnQueueService;
    }

    @GetMapping("/custom")
    public String showCustomCreateForm(Model model) {
        model.addAttribute("creature", new TemplateCreature());
        return "addCreature";
    }

    @PostMapping("/custom")
    public String saveCustomCreature(
            @Validated @ModelAttribute("creature") TemplateCreature creature,
            BindingResult result) {

        if (result.hasErrors()) {
            return "addCreature";
        }

        templateCreatureRepository.save(creature);

        return "redirect:/creatures";
    }

    @GetMapping("/{id}/template")
    public String getCreatureTemplate(@PathVariable Long id, Model model) {
        TurnQueueItem item = turnQueueService.findItemById(id);
        if (item.getType() == TurnItemType.INDIVIDUAL) {
            Combatant combatant = (Combatant) item;
            model.addAttribute("creature", combatant.getTemplateCreature());
        }
        if (item.getType() == TurnItemType.GROUP) {
            CombatGroup group = (CombatGroup) item;
            model.addAttribute("creature", group.getMembers().get(0).getTemplateCreature());
        }
        return "fragment/creature_template :: template";
    }


}
