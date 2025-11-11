package com.zonix.dndapp.service;

import com.zonix.dndapp.dto.request.TemplateCreatureCreationRequest;
import com.zonix.dndapp.dto.request.TemplateCreatureSearchRequest;
import com.zonix.dndapp.entity.*;
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

    TemplateCreatureService(TemplateCreatureRepository templateCreatureRepository) {
        this.templateCreatureRepository = templateCreatureRepository;
    }

    public Page<TurnQueueItem> search(TemplateCreatureSearchRequest request) {
        System.out.println(request.toString());
        Sort sort = Sort.by(Sort.Direction.ASC, request.sortBy());
        Pageable pageable = PageRequest.of(request.page(), request.size(), sort);


        return templateCreatureRepository.findByNameContainingIgnoreCase(request.query(), pageable);
    }

    public TemplateCreature findTemplateCreatureById(Long id) {

        Optional<TemplateCreature> templateCreature = templateCreatureRepository.findById(id);

        return templateCreature.orElseThrow(() -> new EntityNotFoundException("TemplateCreature not found with id: " + id));
    }

    public TemplateCreature create(TemplateCreatureCreationRequest request, User user) {
        TemplateCreature creature = TemplateCreatureMapper.toEntity(request);
        creature.setCreatedBy(user);
        System.out.println("creature: " + creature);
        return creature;
    }

}
