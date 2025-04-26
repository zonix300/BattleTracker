package com.zonix.dndapp.controller;

import com.zonix.dndapp.dto.EffectBatchRequest;
import com.zonix.dndapp.dto.HpUpdateRequest;
import com.zonix.dndapp.dto.ItemAddRequest;
import com.zonix.dndapp.dto.ItemRemovalRequest;
import com.zonix.dndapp.entity.Combatant;
import com.zonix.dndapp.entity.TurnQueueItem;
import com.zonix.dndapp.service.TurnQueueService;
import jakarta.validation.Valid;
import org.hibernate.engine.spi.EffectiveEntityGraph;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/combatants")
public class CombatantController {

    private final TurnQueueService turnQueueService;

    public CombatantController(TurnQueueService turnQueueService) {
        this.turnQueueService = turnQueueService;
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
        List<TurnQueueItem> items = turnQueueService.updateHp(request.combatantIds(), request.amount(), request.type());
        Map<String, List<TurnQueueItem>> response = new HashMap<>();
        response.put("turnQueueItems", items);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/effects")
    public ResponseEntity<Void> updateEffects(@Valid @RequestBody EffectBatchRequest request) {
        request.updates().forEach(update -> {
            turnQueueService.updateEffects(update.itemId(), update.effects(), update.shouldAdd());
        });
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Map<String, List<TurnQueueItem>>> remove(@Valid @RequestBody ItemRemovalRequest request) {
        List<TurnQueueItem> items = turnQueueService.remove(request.itemIds());
        Map<String, List<TurnQueueItem>> response = new HashMap<>();
        response.put("turnQueueItems", items);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, List<TurnQueueItem>>> add(@Valid @RequestBody ItemAddRequest request) {
        List<TurnQueueItem> items = turnQueueService.addItems(request.templateId(), request.amount());
        Map<String, List<TurnQueueItem>> response = new HashMap<>();
        response.put("turnQueueItems", items);
        return ResponseEntity.ok(response);
    }
}
