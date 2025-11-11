package com.zonix.dndapp.entity.playerCharacter.helperType;

import java.util.List;
import java.util.Map;

public class Inventory {
    private Map<String, Integer> coins;
    private Integer totalWeight;
    private List<String> equipment;

    public static Inventory emptyInventory() {
        Inventory i = new Inventory();
        i.coins = Map.of(
                "copper", 0,
                "silver", 0,
                "electrum", 0,
                "gold", 0,
                "platinum", 0
        );
        i.totalWeight = 0;
        i.equipment = List.of();
        return i;
    }

    public Map<String, Integer> getCoins() {
        return coins;
    }

    public void setCoins(Map<String, Integer> coins) {
        this.coins = coins;
    }

    public Integer getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Integer totalWeight) {
        this.totalWeight = totalWeight;
    }

    public List<String> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<String> equipment) {
        this.equipment = equipment;
    }
}
