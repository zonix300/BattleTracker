package com.zonix.dndapp.service;

import com.zonix.dndapp.dto.entity.AuthContext;
import com.zonix.dndapp.dto.response.BattleResponse;
import com.zonix.dndapp.entity.*;
import com.zonix.dndapp.exception.BattleNotFoundException;
import com.zonix.dndapp.exception.UserNotFoundException;
import com.zonix.dndapp.repository.BattleRepository;
import com.zonix.dndapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

@Service
public class BattleService {

    private final BattleRepository battleRepository;
    private final UserService userService;
    private final LobbyService lobbyService;

    public BattleService(BattleRepository battleRepository, UserService userService, LobbyService lobbyService) {
        this.battleRepository = battleRepository;
        this.userService = userService;
        this.lobbyService = lobbyService;
    }

    public BattleResponse getBattleResponse(Optional<Long> battleId, AuthContext authContext) {

        User owner = userService.findUser(authContext);

        Battle battle;

        if (battleId.isPresent()) {
            battle = battleRepository.findById(battleId.get())
                    .orElseThrow(() -> new BattleNotFoundException("Battle was not found"));
        } else {
            battle = battleRepository.findFirstByOwnerOrderByCreatedAtDesc(owner)
                    .orElseGet(() -> createBattle(owner));
        }

        sortUserCombatants(battle);

        return new BattleResponse(battle);
    }

    public Lobby assignBattleToLobby(Optional<Long> battleId, Lobby lobby) {

        Battle battle;

        if (battleId.isPresent()) {
            battle = battleRepository.findById(battleId.get())
                    .orElseThrow(() -> new BattleNotFoundException("Battle was not found or user doesn't have access"));
        } else {
            battle = battleRepository.findFirstByOwnerOrderByCreatedAtDesc(lobby.getOwner())
                    .orElseGet(() -> createBattle(lobby.getOwner()));
        }

        lobby.setBattle(battle);

        lobbyService.save(lobby);

        return lobby;
    }

    public void addUserCombatant(UserCombatant userCombatant, Battle battle) {
        battle.getUserCombatants().add(userCombatant);
        userCombatant.setBattle(battle);
        battleRepository.save(battle);
    }

    public Battle validateBattle(Optional<Long> battleIdOptional, User owner) {
        if (battleIdOptional.isPresent()) {
            return battleRepository. findById(battleIdOptional.get())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid battle ID for this user"));
        }

        return battleRepository.findFirstByOwnerOrderByCreatedAtDesc(owner)
                .orElseGet(() -> {
                    Battle newBattle = new Battle();
                    newBattle.setOwner(owner);
                    newBattle.setName("Untitled Battle");
                    newBattle.setCreatedAt(LocalDateTime.now());
                    return battleRepository.save(newBattle);
                });

    }

    public Battle createBattle(User owner) {
        Battle newBattle = new Battle();
        newBattle.setOwner(owner);
        newBattle.setName("Untitled Battle");
        newBattle.setCreatedAt(LocalDateTime.now());
        return battleRepository.save(newBattle);
    }

    public void validateCurrent(Battle battle) {
        if (battle.getCurrent() == null || !battle.getUserCombatants().contains(battle.getCurrent())) {
            sortUserCombatants(battle);

            if (!battle.getUserCombatants().isEmpty()) {
                battle.setCurrent(battle.getUserCombatants().getFirst());
            }
        }
    }

    public BattleResponse nextTurn(AuthContext authContext) {
        User owner = userService.findUser(authContext);

        Battle battle = validateBattle(Optional.empty(), owner);
        sortUserCombatants(battle);

        int current = 0;
        for (UserCombatant uc : battle.getUserCombatants()) {
            current++;
            if (uc == battle.getCurrent()) {
                break;
            }

        }

        current = (current % battle.getUserCombatants().size());

        battle.setCurrent(battle.getUserCombatants().get(current));

        battleRepository.save(battle);

        return new BattleResponse(battle);

    }

    public void sortUserCombatants(Battle battle) {
        battle.getUserCombatants().sort(
                Comparator
                        .comparingInt(UserCombatant::getInitiative)
                        .reversed()
                        .thenComparingInt(UserCombatant::getDexterity)
                        .reversed()
        );
    }
}
