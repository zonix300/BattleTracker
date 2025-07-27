package com.zonix.dndapp.dto.request;

public record CombatantUpdateRequest(
        Integer armorClass,
        Integer currentHp,
        Integer initiative,
        Integer maxHp,
        String name
) {

}
