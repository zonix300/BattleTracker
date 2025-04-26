package com.zonix.dndapp.config;

import com.zonix.dndapp.repository.TemplateCreatureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class H2Starter implements CommandLineRunner {
    private final TemplateCreatureRepository templateCreatureRepository;

    public H2Starter(TemplateCreatureRepository templateCreatureRepository) {
        this.templateCreatureRepository = templateCreatureRepository;
    }


    @Override
    public void run(String... args) throws Exception {
    }
}
