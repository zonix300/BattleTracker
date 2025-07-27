package com.zonix.dndapp.service;

import com.zonix.dndapp.dto.response.BattleResponse;
import com.zonix.dndapp.entity.Battle;
import com.zonix.dndapp.entity.User;
import com.zonix.dndapp.entity.UserCombatant;
import com.zonix.dndapp.repository.BattleRepository;
import com.zonix.dndapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

@Service
public class BattleService {

    private BattleRepository battleRepository;
    private UserRepository userRepository;

    public BattleService(BattleRepository battleRepository, UserRepository userRepository) {
        this.battleRepository = battleRepository;
        this.userRepository = userRepository;
    }

//    public Long nextTurn(String email) {
//        Optional<User> userOptional = userRepository.findByEmail(email);
//        if (userOptional.isEmpty()) {
//            return null;
//        }
//        User user = userOptional.get();
//
//        Optional<Battle> battleOptional = battleRepository.findByOwner(user); // todo
//
//        if (battleOptional.isEmpty()) {
//            return null;
//        }
//
//        Battle battle = battleOptional.get();
//
//        battle.setCurrentIndex((battle.getCurrentIndex()+1) % battle.getUserCombatants().size());
//
//        return battle.getUserCombatants().get(battle.getCurrentIndex()).getId();
//    }

    public BattleResponse getBattle(Optional<Long> battleId, String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return null;
        }

        Battle battle = validateBattle(battleId, userOptional.get());
        validateCurrent(battle);
        return new BattleResponse(battle);
    }

    public void addUserCombatant(UserCombatant userCombatant, Battle battle) {
        battle.getUserCombatants().add(userCombatant);
        userCombatant.setBattle(battle);
        battleRepository.save(battle);
    }

    public Battle validateBattle(Optional<Long> battleIdOptional, User owner) {
        if (battleIdOptional.isPresent()) {
            return battleRepository.findByIdAndOwner(battleIdOptional.get(), owner)
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

    public void validateCurrent(Battle battle) {
        if (battle.getCurrent() == null || !battle.getUserCombatants().contains(battle.getCurrent())) {
            sortUserCombatants(battle);

            if (!battle.getUserCombatants().isEmpty()) {
                battle.setCurrent(battle.getUserCombatants().getFirst());
            }
        }
    }

    public BattleResponse nextTurn(String email) {
        Optional<User> ownerOptional = userRepository.findByEmail(email);

        if (ownerOptional.isEmpty()) {
            return null;
        }

        Battle battle = validateBattle(Optional.empty(), ownerOptional.get());
        sortUserCombatants(battle);

        int current = 0;
        for(UserCombatant uc : battle.getUserCombatants()) {
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
