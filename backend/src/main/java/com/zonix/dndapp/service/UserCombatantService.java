package com.zonix.dndapp.service;

import com.zonix.dndapp.component.UserCombatantFactory;
import com.zonix.dndapp.dto.entity.UserCombatantDTO;
import com.zonix.dndapp.dto.request.CombatantUpdateRequest;
import com.zonix.dndapp.dto.response.BattleResponse;
import com.zonix.dndapp.entity.*;
import com.zonix.dndapp.repository.BattleRepository;
import com.zonix.dndapp.repository.TemplateCreatureRepository;
import com.zonix.dndapp.repository.UserCombatantRepository;
import com.zonix.dndapp.repository.UserRepository;
import com.zonix.dndapp.util.DndUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserCombatantService {

    private static final Logger log = LoggerFactory.getLogger(UserCombatantService.class);
    private final TemplateCreatureRepository templateCreatureRepository;
    private final UserRepository userRepository;
    private final UserCombatantRepository userCombatantRepository;
    private final BattleService battleService;
    private final BattleRepository battleRepository;

    public UserCombatantService(TemplateCreatureRepository templateCreatureRepository, UserRepository userRepository, UserCombatantRepository userCombatantRepository, BattleService battleService, BattleRepository battleRepository) {
        this.templateCreatureRepository = templateCreatureRepository;
        this.userRepository = userRepository;
        this.userCombatantRepository = userCombatantRepository;
        this.battleService = battleService;
        this.battleRepository = battleRepository;
    }

    public Set<UserCombatantDTO> getUserCombatants(String email) {
        Set<UserCombatant> userCombatants = userCombatantRepository.findUserCombatantsByOwnerEmail(email);
        Set<UserCombatantDTO> combatantDTOs = new HashSet<>();
        for (UserCombatant uc : userCombatants) {
            UserCombatantDTO dto = new UserCombatantDTO(uc);
            combatantDTOs.add(dto);
        }
        return combatantDTOs;
    }

    public BattleResponse addCombatant(Long templateId, Optional<Long> battleId, String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return null;
        }

        Battle battle = battleService.validateBattle(battleId, userOptional.get());

        Optional<TemplateCreature> templateCreatureOptional = templateCreatureRepository.findTemplateCreatureById(templateId);

        if (templateCreatureOptional.isEmpty()) {
            return null;
        }

        UserCombatant userCombatant = UserCombatantFactory.fromTemplateWithHitDice(templateCreatureOptional.get(), userOptional.get());
        battleService.addUserCombatant(userCombatant, battle);

        if (battle.getCurrent() == null) {
            battleService.validateCurrent(battle);
        }

        battleService.sortUserCombatants(battle);

        return new BattleResponse(battle);
    }

    public BattleResponse deleteCombatant(Long combatantId, String email) {
        Optional<User> ownerOptional = userRepository.findByEmail(email);

        if (ownerOptional.isEmpty()) {
            return null;
        }

        Optional<UserCombatant> userCombatantOptional = userCombatantRepository.getByIdAndOwner(combatantId, ownerOptional.get());

        if (userCombatantOptional.isEmpty()) {
            return null;
        }

        Battle battle = userCombatantOptional.get().getBattle();

        userCombatantRepository.deleteByIdAndOwner(combatantId, ownerOptional.get());

        battle.getUserCombatants().removeIf(uc -> uc.getId().equals(combatantId));
        if (battle.getCurrent() != null && battle.getCurrent().getId().equals(combatantId)) {
            battle.setCurrent(null);
        }

        battleService.sortUserCombatants(battle);

        return new BattleResponse(battle);
    }

    public BattleResponse updateCombatant(Long combatantId, CombatantUpdateRequest request, String email) {
        Optional<User> ownerOptional = userRepository.findByEmail(email);

        if (ownerOptional.isEmpty()) {
            return null;
        }

        Optional<UserCombatant> userCombatantOptional = userCombatantRepository.findByIdAndOwner(combatantId, ownerOptional.get());

        if (userCombatantOptional.isEmpty()) {
            return null;
        }

        UserCombatant uc = userCombatantOptional.get();

        uc.setMaxHp(request.maxHp());
        uc.setCurrentHp(request.currentHp());
        uc.setName(request.name());
        uc.setInitiative(request.initiative());
        uc.setArmorClass(request.armorClass());

        userCombatantRepository.save(uc);

        Battle battle = userCombatantOptional.get().getBattle();

        battleService.sortUserCombatants(battle);

        return new BattleResponse(battle);
    }

    public UserCombatantDTO rollInitiative(Long combatantId, String email) {
        Optional<User> ownerOptional = userRepository.findByEmail(email);

        if (ownerOptional.isEmpty()) {
            return null;
        }

        Optional<UserCombatant> userCombatantOptional = userCombatantRepository.findByIdAndOwner(combatantId, ownerOptional.get());

        if (userCombatantOptional.isEmpty()) {
            return null;
        }

        UserCombatant uc = userCombatantOptional.get();

        uc.setInitiative(DndUtils.roll(20) + DndUtils.calculateModifier(uc.getDexterity()));

        userCombatantRepository.save(uc);

        return new UserCombatantDTO(uc);
    }


}
