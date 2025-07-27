package com.zonix.dndapp.mapper;

import com.zonix.dndapp.dto.request.TemplateCreatureCreationRequest;
import com.zonix.dndapp.entity.*;
import com.zonix.dndapp.util.ActionMapperHelper;
import com.zonix.dndapp.util.DndUtils;
import com.zonix.dndapp.util.Skill;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
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


}
