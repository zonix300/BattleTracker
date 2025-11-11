package com.zonix.dndapp.resolver;

import com.zonix.dndapp.entity.*;
import org.springframework.stereotype.Component;

@Component
public class HealRequestResolver implements RequestResolver{


    @Override
    public void apply(LobbyRequest request, Lobby lobby, User user) {
        UserCombatant target = request.getTarget();
        int value = Integer.parseInt(request.getValue());

        int newHp = target.getCurrentHp() + value;

        target.setCurrentHp(newHp);
    }
}
