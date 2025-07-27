package com.zonix.dndapp.controller;

import com.zonix.dndapp.dto.request.TemplateCreatureCreationRequest;
import com.zonix.dndapp.entity.*;
import com.zonix.dndapp.service.TemplateCreatureService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/creatures")
public class CreatureController {
    private final TemplateCreatureService templateCreatureService;

    public CreatureController(TemplateCreatureService templateCreatureService) {
        this.templateCreatureService = templateCreatureService;
    }

    @PostMapping("/create")
    public ResponseEntity<TemplateCreature> create(@Valid @RequestBody TemplateCreatureCreationRequest request) {
        request.actions().forEach((key, value) -> System.out.println(key + " " + value));
        TemplateCreature creature = templateCreatureService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creature);
    }

    @GetMapping("/renderTemplateCreature")
    public String getCreatureTemplate(@RequestParam(value = "id") Long id, Model model) {
        TemplateCreature creature = templateCreatureService.findTemplateCreatureById(id);
        model.addAttribute("creature", creature);
        return "template_creature.html";
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<TemplateCreature> getTemplateCreature(@PathVariable Long templateId) {
        TemplateCreature creature = templateCreatureService.findTemplateCreatureById(templateId);
        return ResponseEntity.ok(creature);
    }



}
