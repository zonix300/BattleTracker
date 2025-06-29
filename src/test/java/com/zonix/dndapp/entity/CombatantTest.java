package com.zonix.dndapp.entity;

import com.zonix.dndapp.util.TestCreatureFabric;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CombatantTest {
    @Test
    void constructor_WithValidCreature_SetsCorrectHp() {
        TemplateCreature creature = TestCreatureFabric.createBasicCreature();
        for (int i = 0; i < 1000; i++) {

            Combatant combatant = new Combatant(creature);

            assertThat(combatant.getMaxHp())
                    .isBetween(2, 12);
            assertThat(combatant.getCurrentHp())
                    .isEqualTo(combatant.getMaxHp());
        }
    }
}
