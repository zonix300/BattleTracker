package com.zonix.dndapp.mapper;

import com.zonix.dndapp.dto.request.TemplateCreatureCreationRequest;
import com.zonix.dndapp.dto.response.sheet.PlayerCharacterResponse;
import com.zonix.dndapp.dto.response.sheet.TemplateCreatureResponse;
import com.zonix.dndapp.entity.*;
import com.zonix.dndapp.util.ActionMapperHelper;
import com.zonix.dndapp.util.DndUtils;
import com.zonix.dndapp.util.Skill;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TemplateCreatureMapper {

    public static TemplateCreature toEntity(final TemplateCreatureCreationRequest request) {
        final TemplateCreature templateCreature = new TemplateCreature();
        templateCreature.setChallengeRating(request.challengeRating());
        templateCreature.setName(request.name());
        templateCreature.setSize(request.size());
        templateCreature.setCreatureType(request.creatureType());
        templateCreature.setAlignment(request.alignment());
        templateCreature.setArmorClass(request.armorClass());
        templateCreature.setArmorDescription(request.armorDescription());
        templateCreature.setHitPoints(request.hitPoints());
        templateCreature.setHitDice(request.hitDice());

        templateCreature.setStrength(request.strength());
        templateCreature.setDexterity(request.dexterity());
        templateCreature.setConstitution(request.constitution());
        templateCreature.setIntelligence(request.intelligence());
        templateCreature.setWisdom(request.wisdom());
        templateCreature.setCharisma(request.charisma());

        int proficiencyBonus = DndUtils.getProficiencyForCr(request.challengeRating());

        for (String savingThrow : request.savingThrows()) {
            if (savingThrow.equals("strength")) {
                templateCreature.setStrengthSave(request.strength() + proficiencyBonus);
            }
            if (savingThrow.equals("dexterity")) {
                templateCreature.setDexteritySave(request.dexterity() + proficiencyBonus);
            }
            if (savingThrow.equals("constitution")) {
                templateCreature.setConstitutionSave(request.constitution() + proficiencyBonus);
            }
            if (savingThrow.equals("intelligence")) {
                templateCreature.setIntelligence(request.intelligence() + proficiencyBonus);
            }
            if (savingThrow.equals("wisdom")) {
                templateCreature.setWisdomSave(request.wisdom() + proficiencyBonus);
            }
            if (savingThrow.equals("charisma")) {
                templateCreature.setCharismaSave(request.charisma() + proficiencyBonus);
            }
        }

        Set<CreatureSkills> creatureSkills = request.skills().stream()
                .map(e -> {
                    CreatureSkills creatureSkill = new CreatureSkills();

                    CreatureSkillsId id = new CreatureSkillsId();
                    id.setSkillName(e);
                    creatureSkill.setId(id);
                    creatureSkill.setValue(DndUtils.calculateProficientSkillValue(templateCreature, e));

                    return creatureSkill;
                })
                .collect(Collectors.toSet());
        templateCreature.setSkills(creatureSkills);

        templateCreature.setDamageResistances(request.damageResistances());
        templateCreature.setDamageVulnerabilities(request.damageVulnerabilities());
        templateCreature.setDamageImmunities(request.damageImmunities());
        templateCreature.setConditionImmunities(request.conditionImmunities());

        templateCreature.setSenses(request.senses());
        templateCreature.setLanguages(request.languages());


        Set<Action> actions = ActionMapperHelper.mapActionMap(
                request.actions(),
                Action::new
        );
        templateCreature.setActions(actions);

        Set<BonusAction> bonusActions = ActionMapperHelper.mapActionMap(
                request.bonusActions(),
                BonusAction::new
        );
        templateCreature.setBonusActions(bonusActions);

        Set<Ability> abilities = ActionMapperHelper.mapActionMap(
                request.abilities(),
                Ability::new
        );
        templateCreature.setAbilities(abilities);

        Set<LegendaryAction> legendaryActions = ActionMapperHelper.mapActionMap(
                request.legendaryActions(),
                LegendaryAction::new
        );
        templateCreature.setLegendaryActions(legendaryActions);

        Set<Reaction> reactions = ActionMapperHelper.mapActionMap(
                request.reactions(),
                Reaction::new
        );
        templateCreature.setReactions(reactions);

        Set<CreatureSpeed> speeds = request.speeds().entrySet().stream()
                .map(e -> {
                    CreatureSpeed speed = new CreatureSpeed();

                    CreatureSpeedId id = new CreatureSpeedId();
                    id.setSpeedType(e.getKey());
                    speed.setId(id);

                    int speedValue = Integer.parseInt(e.getValue());
                    speed.setValue(speedValue);
                    speed.setCreature(templateCreature);

                    return speed;
                })
                .collect(Collectors.toSet());
        templateCreature.setSpeeds(speeds);

        templateCreature.setDescription(request.description());
        templateCreature.setLegendaryDescription(request.legendaryDescription());

        return templateCreature;

    }

    public static TemplateCreatureResponse fromUserCombatant(UserCombatant uc) {
        if (uc.getTemplateCreature() == null) return null;
        TemplateCreature tc = uc.getTemplateCreature();
        TemplateCreatureResponse r = new TemplateCreatureResponse();
        r.setId(uc.getTemplateCreature().getId());
        r.setName(uc.getName());
        r.setCreatureType(tc.getCreatureType());
        r.setChallengeRating(tc.getChallengeRating());
        r.setDescription(tc.getDescription());
        r.setHitDice(tc.getHitDice());
        r.setArmorClass(uc.getArmorClass());
        r.setMaxHp(uc.getMaxHp());
        r.setCurrentHp(uc.getCurrentHp());
        r.setAbilities(
                Map.of(
                    "strength", tc.getStrength(),
                    "dexterity", tc.getDexterity(),
                    "constitution", tc.getConstitution(),
                    "intelligence", tc.getIntelligence(),
                    "wisdom", tc.getWisdom(),
                    "charisma", tc.getCharisma()
                )
        );

        Set<String> skills = new HashSet<>();
        for (CreatureSkills skill : tc.getSkills()) {
            skills.add(skill.getName().toLowerCase());
        }
        r.setSkills(skills);

        Map<String, String> actions = new HashMap<>();
        for (Action action : tc.getActions()) {
            actions.put(action.getName(), action.getDescription());
        }
        r.setActions(Map.of("action", actions));
        r.setAlignment(tc.getAlignment());
        r.setSize(tc.getSize());
        Map<String, Integer> speeds = new HashMap<>();
        for (CreatureSpeed speed : tc.getSpeeds()) {
            speeds.put(speed.getName(), speed.getValue());
        }
        r.setSpeeds(speeds);

        r.setSavingThrows(getSaves(tc));

        r.setLanguages(tc.getLanguages());
        r.setActions(getActions(tc));
        r.setLegendaryDescription(tc.getLegendaryDescription());
        r.setPassivePerception(10 + DndUtils.calculateModifier(tc.getDexterity()));

        r.setDamageImmunities(tc.getDamageImmunities());
        r.setDamageResistances(tc.getDamageResistances());
        r.setDamageVulnerabilities(tc.getDamageVulnerabilities());
        r.setConditionImmunities(tc.getConditionImmunities());
        r.setSenses(tc.getSenses());
        Map<String, String> specialAbilities = new HashMap<>();

        for (Ability ability : tc.getAbilities()) {
            specialAbilities.put(ability.getName(), ability.getDescription());
        }
        r.setSpecialAbilities(specialAbilities);


        r.setType(TurnItemType.TEMPLATE_CREATURE);
        return r;
    }

    private static Map<String, Integer> getSaves(TemplateCreature tc) {
        Map<String, Integer> saves = new HashMap<>();
        if (tc.getStrengthSave() != null) saves.put("strength", tc.getStrengthSave());
        if (tc.getDexteritySave() != null) saves.put("dexterity", tc.getDexteritySave());
        if (tc.getConstitutionSave() != null) saves.put("constitution", tc.getConstitutionSave());
        if (tc.getIntelligenceSave() != null) saves.put("intelligence", tc.getIntelligenceSave());
        if (tc.getWisdomSave() != null) saves.put("wisdom", tc.getWisdomSave());
        if (tc.getCharismaSave() != null) saves.put("charisma", tc.getCharismaSave());
        return saves;
    }

    private static Map<String, Map<String, String>> getActions(TemplateCreature tc) {
        Map<String, Map<String, String>> actions = new HashMap<>();
        Map<String, String> fullActions = new HashMap<>();
        for (Action action : tc.getActions()) {
            fullActions.put(action.getName(), action.getDescription());
        }
        Map<String, String> bonusActions = new HashMap<>();
        for (BonusAction action : tc.getBonusActions()) {
            bonusActions.put(action.getName(), action.getDescription());
        }
        Map<String, String> legendaryActions = new HashMap<>();
        for (LegendaryAction action : tc.getLegendaryActions()) {
            legendaryActions.put(action.getName(), action.getDescription());
        }
        
        actions.put("legendary_action", legendaryActions);
        actions.put("bonus_action", bonusActions);
        actions.put("action", fullActions);
        return actions;
    }



}
