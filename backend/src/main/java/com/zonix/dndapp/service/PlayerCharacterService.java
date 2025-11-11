package com.zonix.dndapp.service;

import com.zonix.dndapp.dto.entity.PlayerCharacterDTO;
import com.zonix.dndapp.dto.entity.combatant.PlayerCombatantDTO;
import com.zonix.dndapp.dto.request.PlayerCharacterSearchRequest;
import com.zonix.dndapp.entity.playerCharacter.PlayerCharacter;
import com.zonix.dndapp.entity.User;
import com.zonix.dndapp.repository.PlayerCharacterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerCharacterService {
    private final PlayerCharacterRepository playerCharacterRepository;

    public PlayerCharacterService(PlayerCharacterRepository playerCharacterRepository) {
        this.playerCharacterRepository = playerCharacterRepository;
    }

    public List<PlayerCharacterDTO> getUserPlayerCharacters(User user) {
        List<PlayerCharacterDTO> dtos = new ArrayList<>();
        List<PlayerCharacter> characters = playerCharacterRepository.findAllByOwner(user);
        for (PlayerCharacter character : characters) {
            dtos.add(new PlayerCharacterDTO(character));
        }
        return dtos;
    }

    public PlayerCharacter create(User owner) {

        PlayerCharacter pc = new PlayerCharacter.Builder()
                .createDefault()
                .owner(owner)
                .build();

        playerCharacterRepository.save(pc);
        return pc;
    }

    public Optional<PlayerCharacter> findById(Long id) {
        return playerCharacterRepository.findById(id);
    }

    public Optional<PlayerCharacter> edit(Long id, PlayerCharacterDTO dto) {
        Optional<PlayerCharacter> pc = playerCharacterRepository.findById(id);

        if(pc.isEmpty()) {
            return Optional.empty();
        }

        pc.get().editFromDTO(dto);
        playerCharacterRepository.save(pc.get());

        return pc;
    }

    public List<PlayerCharacter> delete(Long id, User owner) {
        playerCharacterRepository.deleteById(id);

        return playerCharacterRepository.findAllByOwner(owner);
    }

    public Page<PlayerCombatantDTO> search(PlayerCharacterSearchRequest request, User owner) {

        Sort sort = Sort.by(Sort.Direction.ASC, request.sortBy());
        Pageable pageable = PageRequest.of(request.page(), request.size(), sort);

        return playerCharacterRepository.searchCombatants(request, owner, pageable);
    }
}
