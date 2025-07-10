package com.zonix.dndapp.service;

import com.zonix.dndapp.dto.request.TemplateCreatureCreationRequest;
import com.zonix.dndapp.dto.request.TemplateCreatureSearchRequest;
import com.zonix.dndapp.entity.Action;
import com.zonix.dndapp.entity.BonusAction;
import com.zonix.dndapp.entity.Combatant;
import com.zonix.dndapp.entity.TemplateCreature;
import com.zonix.dndapp.mapper.TemplateCreatureMapper;
import com.zonix.dndapp.repository.TemplateCreatureRepository;
import com.zonix.dndapp.util.ActionMapperHelper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TemplateCreatureService {

    private final TemplateCreatureRepository templateCreatureRepository;
    private final TemplateCreatureMapper mapper;

    TemplateCreatureService(TemplateCreatureRepository templateCreatureRepository, TemplateCreatureMapper mapper) {
        this.templateCreatureRepository = templateCreatureRepository;
        this.mapper = mapper;
    }

    public Page<Combatant> search(TemplateCreatureSearchRequest request) {
        System.out.println(request.toString());
        Sort sort = Sort.by(Sort.Direction.ASC, request.sortBy());
        Pageable pageable = PageRequest.of(request.page(), request.size(), sort);


        return templateCreatureRepository.findByNameContainingIgnoreCase(request.name(), pageable);
    }

    public TemplateCreature findTemplateCreatureById(Long id) {
        Optional<TemplateCreature> templateCreature = templateCreatureRepository.findById(id);

        return templateCreature.orElseThrow(() -> new EntityNotFoundException("TemplateCreature not found with id: " + id));
    }

    public TemplateCreature create(TemplateCreatureCreationRequest request) {
        TemplateCreature creature = mapper.toEntity(request);
        System.out.println("creature: " + creature);
        return creature;
    }

}
