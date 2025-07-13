package com.zonix.dndapp.controller;

import com.zonix.dndapp.dto.request.EffectBatchRequest;
import com.zonix.dndapp.dto.request.HpUpdateRequest;
import com.zonix.dndapp.dto.request.ItemRemovalRequest;
import com.zonix.dndapp.dto.request.TemplateCreatureSearchRequest;
import com.zonix.dndapp.entity.Combatant;
import com.zonix.dndapp.entity.TemplateCreature;
import com.zonix.dndapp.entity.TurnQueueItem;
import com.zonix.dndapp.service.TemplateCreatureService;
import com.zonix.dndapp.service.TurnQueueService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/combatants")
public class CombatantController {

    private final TurnQueueService turnQueueService;
    private final TemplateCreatureService templateCreatureService;
    private final static Logger log = LoggerFactory.getLogger(CombatantController.class);

    public CombatantController(TurnQueueService turnQueueService, TemplateCreatureService templateCreatureService) {
        this.turnQueueService = turnQueueService;
        this.templateCreatureService = templateCreatureService;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<TurnQueueItem>>> getItems() {
        List<TurnQueueItem> items = turnQueueService.getAllItems();
        Map<String, List<TurnQueueItem>> response = new HashMap<>();
        response.put("turnQueueItems", items);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/hp")
    public ResponseEntity<Map<String, List<TurnQueueItem>>> updateHp(@Valid @RequestBody HpUpdateRequest request) {
        log.info("Received request to update HP: {}", request);
        List<TurnQueueItem> items = turnQueueService.updateHp(request.combatantIds(), request.amount(), request.type());
        log.info("Updated items: {}", items);
        Map<String, List<TurnQueueItem>> response = new HashMap<>();
        response.put("turnQueueItems", items);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/effects")
    public ResponseEntity<Void> updateEffects(@Valid @RequestBody EffectBatchRequest request) {
        request.updates().forEach(update -> turnQueueService.updateEffects(update.itemId(), update.effects(), update.shouldAdd()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Map<String, List<TurnQueueItem>>> remove(@Valid @RequestBody ItemRemovalRequest request) {
        List<TurnQueueItem> items = turnQueueService.remove(request.itemIds());
        Map<String, List<TurnQueueItem>> response = new HashMap<>();
        response.put("turnQueueItems", items);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{templateId}/add")
    public ResponseEntity<TurnQueueItem> add(@PathVariable Long templateId) {
        TurnQueueItem item = turnQueueService.addItem(templateId);

        return ResponseEntity.ok(item);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Combatant>> search(@Valid @RequestBody TemplateCreatureSearchRequest request) {
        log.info("Search request: {}", request);

        Page<Combatant> response;
        response = templateCreatureService.search(request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{templateId}/initiative-roll")
    public ResponseEntity<TurnQueueItem> rollInitiative(@PathVariable Long templateId) {
        Combatant combatant = turnQueueService.rollInitiative(templateId);
        return ResponseEntity.ok(combatant);
    }

    @PatchMapping("/{templateId}/initiative")
    public ResponseEntity<TurnQueueItem> initiative(@PathVariable Long templateId, @RequestBody Map<String, Integer> request) {
        Combatant combatant = (Combatant) turnQueueService.findItemById(templateId);
        Integer newInitiative = request.get("initiative");
        combatant.setInitiative(newInitiative);
        return ResponseEntity.ok(combatant);
    }

    @GetMapping("{templateId}")
    public ResponseEntity<TemplateCreature> get(@PathVariable Long templateId) {
        TemplateCreature creature = templateCreatureService.findTemplateCreatureById(templateId);
        return ResponseEntity.ok(creature);
    }
}
