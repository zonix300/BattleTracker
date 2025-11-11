package com.zonix.dndapp.service;

import com.zonix.dndapp.component.UserCombatantFactory;
import com.zonix.dndapp.dto.entity.AuthContext;
import com.zonix.dndapp.dto.entity.UserCombatantDTO;
import com.zonix.dndapp.dto.request.CombatantUpdateRequest;
import com.zonix.dndapp.dto.response.BattleResponse;
import com.zonix.dndapp.dto.response.sheet.CreatureResponse;
import com.zonix.dndapp.entity.*;
import com.zonix.dndapp.entity.playerCharacter.PlayerCharacter;
import com.zonix.dndapp.mapper.PlayerCharacterMapper;
import com.zonix.dndapp.mapper.TemplateCreatureMapper;
import com.zonix.dndapp.repository.*;
import com.zonix.dndapp.util.DndUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserCombatantService {

    private static final Logger log = LoggerFactory.getLogger(UserCombatantService.class);
    private final TemplateCreatureRepository templateCreatureRepository;
    private final UserCombatantRepository userCombatantRepository;
    private final BattleService battleService;
    private final UserService userService;
    private final PlayerCharacterRepository playerCharacterRepository;

    public UserCombatantService(TemplateCreatureRepository templateCreatureRepository, UserCombatantRepository userCombatantRepository, BattleService battleService, UserService userService, PlayerCharacterRepository playerCharacterRepository) {
        this.templateCreatureRepository = templateCreatureRepository;
        this.userCombatantRepository = userCombatantRepository;
        this.battleService = battleService;
        this.userService = userService;
        this.playerCharacterRepository = playerCharacterRepository;
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

    public Optional<UserCombatant> findByIdAndOwner(Long userCombatantId, User owner) {
        return userCombatantRepository.findByIdAndOwner(userCombatantId, owner);
    }

    public BattleResponse addCombatant(Long templateId, Optional<Long> battleId, AuthContext authContext) {
        User user = userService.findUser(authContext);

        Battle battle = battleService.validateBattle(battleId, user);

        Optional<TemplateCreature> templateCreatureOptional = templateCreatureRepository.findTemplateCreatureById(templateId);

        if (templateCreatureOptional.isEmpty()) {
            return null;
        }

        UserCombatant userCombatant = UserCombatantFactory.fromTemplateWithHitDice(templateCreatureOptional.get(), user);
        battleService.addUserCombatant(userCombatant, battle);

        if (battle.getCurrent() == null) {
            battleService.validateCurrent(battle);
        }

        battleService.sortUserCombatants(battle);

        return new BattleResponse(battle);
    }

    public BattleResponse addPlayerCharacter(Long characterId, Optional<Long> battleId, AuthContext authContext) {
        User user = userService.findUser(authContext);

        Battle battle = battleService.validateBattle(battleId, user);

        Optional<PlayerCharacter> playerCharacter = playerCharacterRepository.findById(characterId);

        if (playerCharacter.isEmpty()) {
            return null;
        }

        UserCombatant userCombatant = UserCombatantFactory.fromPlayerCharacterWithHitPoints(playerCharacter.get(), user);
        battleService.addUserCombatant(userCombatant, battle);

        if (battle.getCurrent() == null) {
            battleService.validateCurrent(battle);
        }

        battleService.sortUserCombatants(battle);

        return new BattleResponse(battle);
    }
    public BattleResponse deleteCombatant(Long combatantId, AuthContext authContext) {
        User owner = userService.findUser(authContext);

        Optional<UserCombatant> userCombatantOptional = userCombatantRepository.getByIdAndOwner(combatantId, owner);

        if (userCombatantOptional.isEmpty()) {
            return null;
        }

        Battle battle = userCombatantOptional.get().getBattle();

        userCombatantRepository.deleteByIdAndOwner(combatantId, owner);

        battle.getUserCombatants().removeIf(uc -> uc.getId().equals(combatantId));
        if (battle.getCurrent() != null && battle.getCurrent().getId().equals(combatantId)) {
            battle.setCurrent(null);
        }

        battleService.sortUserCombatants(battle);

        return new BattleResponse(battle);
    }

    public BattleResponse updateCombatant(Long combatantId, CombatantUpdateRequest request, AuthContext authContext) {
        User owner = userService.findUser(authContext);

        Optional<UserCombatant> userCombatantOptional = userCombatantRepository.findByIdAndOwner(combatantId, owner);

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

    public UserCombatantDTO rollInitiative(Long combatantId, AuthContext authContext) {
        User owner = userService.findUser(authContext);

        Optional<UserCombatant> userCombatantOptional = userCombatantRepository.findByIdAndOwner(combatantId, owner);

        if (userCombatantOptional.isEmpty()) {
            return null;
        }

        UserCombatant uc = userCombatantOptional.get();

        uc.setInitiative(DndUtils.roll(20) + DndUtils.calculateModifier(uc.getDexterity()));

        userCombatantRepository.save(uc);

        return new UserCombatantDTO(uc);
    }

    public CreatureResponse getCreatureResponseFromCombatantId(long id) {
        Optional<UserCombatant> uc = userCombatantRepository.findById(id);

        if (uc.isEmpty()) return null;

        CreatureResponse r = null;
        if (uc.get().getPlayerCharacter() != null) {
            r = PlayerCharacterMapper.fromUserCombatant(uc.get());
        } else if (uc.get().getTemplateCreature() != null) {
            r = TemplateCreatureMapper.fromUserCombatant(uc.get());
        }

        return r;

    }


}
