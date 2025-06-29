package com.zonix.dndapp.controller;

import com.zonix.dndapp.dto.request.TemplateCreatureCreationRequest;
import com.zonix.dndapp.entity.*;
import com.zonix.dndapp.service.TemplateCreatureService;
import com.zonix.dndapp.service.TurnQueueService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/creatures")
public class CreatureController {
    private final TurnQueueService turnQueueService;
    private final TemplateCreatureService templateCreatureService;

    public CreatureController(TemplateCreatureService templateCreatureService, TurnQueueService turnQueueService) {
        this.templateCreatureService = templateCreatureService;
        this.turnQueueService = turnQueueService;
    }

    @GetMapping("/create")
    public String getCreatureCreateTemplate() {
        return "create_template_creature.html";
    }

    @PostMapping("/create")
    public ResponseEntity<TemplateCreature> create(@Valid @RequestBody TemplateCreatureCreationRequest request) {
        TemplateCreature creature = templateCreatureService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creature);
    }

    @GetMapping("/renderTemplateCreature")
    public String getCreatureTemplate(@Param(value = "id") Long id, Model model) {
        TemplateCreature creature = templateCreatureService.findTemplateCreatureById(id);
        model.addAttribute("creature", creature);
        return "template_creature.html";
    }


}
