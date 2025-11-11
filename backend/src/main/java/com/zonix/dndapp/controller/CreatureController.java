package com.zonix.dndapp.controller;

import com.zonix.dndapp.dto.request.TemplateCreatureCreationRequest;
import com.zonix.dndapp.entity.*;
import com.zonix.dndapp.service.TemplateCreatureService;
import com.zonix.dndapp.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/creatures")
public class CreatureController {
    private static final Logger log = LoggerFactory.getLogger(CreatureController.class);
    private final TemplateCreatureService templateCreatureService;
    private final UserService userService;

    public CreatureController(TemplateCreatureService templateCreatureService, UserService userService) {
        this.templateCreatureService = templateCreatureService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<TemplateCreature> create(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody TemplateCreatureCreationRequest request
    ) {
        log.info("Create creature request with data: {}", request);
        Optional<User> user = userService.findUserByEmail(userDetails.getUsername());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        TemplateCreature creature = templateCreatureService.create(request, user.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(creature);
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<TemplateCreature> getTemplateCreature(@PathVariable Long templateId) {
        TemplateCreature creature = templateCreatureService.findTemplateCreatureById(templateId);
        return ResponseEntity.ok(creature);
    }



}
