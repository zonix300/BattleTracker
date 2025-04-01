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
        if(combatantRepository.count() == 0) {
            combatantRepository.save(new TemplateCreature( "Goblin", 12, 7, 10));
            combatantRepository.save(new TemplateCreature("Orc", 15, 20, 10));
            combatantRepository.save(new TemplateCreature("Skeleton", 13, 10, 10));
            System.out.println("Preloaded default combatants into the database.");
            System.out.println("Repository instance: " + combatantRepository);
        }
    }
}
