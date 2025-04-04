package com.zonix.dndapp.config;

import com.zonix.dndapp.entity.TemplateCreature;
import com.zonix.dndapp.repository.CombatantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class H2Starter implements CommandLineRunner {
    private final CombatantRepository combatantRepository;

    public H2Starter(CombatantRepository combatantRepository) {
        this.combatantRepository = combatantRepository;
    }


    @Override
    public void run(String... args) throws Exception {
    }
}
