import { useState } from "react";
import axios from "axios";
import "./CreatureForm.css";
import Select from "react-select";
import ActionInputBlock from "./ActionInputBlock";  
import { CreateCreatureRequest } from "../type/CreateCreatureRequest";
      
export default function CreateCreatureForm() {
  const [creature, setCreature] = useState<CreateCreatureRequest>({
    name: "",
    size: "",
    type: "",
    alignment: "",
    armorClass: 0,
    armorDescription: "",
    hitPoints: 0,
    hitDice: "",
    speeds: {},
    skills: {},
    damageResistances: [],
    damageVulnerabilities: [],
    damageImmunities: [],
    conditionImmunities: [],
    senses: [],
    languages: [],
    challengeRating: "",
    strength: 10,
    dexterity: 10,
    constitution: 10,
    intelligence: 10,
    wisdom: 10,
    charisma: 10,
    savingThrows: {
      strength: false,
      dexterity: false,
      constitution: false,
      intelligence: false,
      wisdom: false,
      charisma: false
    },
    actions: [],
    bonusActions: [],
    reactions: [],
    legendaryDescription: "",
    legendaryActions: [],
    abilities: [],
    description: "",
  });

  const DAMAGE_TYPES = [
  "Acid",
  "Bludgeoning",
  "Cold",
  "Fire",
  "Force",
  "Lightning",
  "Necrotic",
  "Piercing",
  "Poison",
  "Psychic",
  "Radiant",
  "Slashing",
  "Thunder",
  ];

  const DAMAGE_TYPE_OPTIONS = DAMAGE_TYPES.map(type => ({
    value: type,
    label: type,
  }));

  const CONDITION_IMMUNITIES = [
    "Charmed",
    "Deafened",
    "Frightened",
    "Grappled",
    "Incapacitated",
    "Paralyzed",
    "Petrified",
    "Poisoned",
    "Prone",
    "Restrained",
    "Stunned",
  ];

  const CONDITION_IMMUNITY_OPTIONS = CONDITION_IMMUNITIES.map(condition => ({
    value: condition,
    label: condition,
  }));

  const SKILLS = [
    "acrobatics", 
    "animal handling",
    "arcana",
    "athletics",
    "deception",
    "history",
    "insight",
    "intimidation",
    "Investigation",
    "medicine",
    "nature",
    "perception",
    "performance",
    "persuasion",
    "religion",
    "sleight of hand",
    "stealth",
    "survival"
  ];

  

  const [actionInput, setActionInput] = useState({ name: "", description: "" });
  const [bonusActionInput, setBonusActionInput] = useState({ name: "", description: "" });
  const [reactionInput, setReactionInput] = useState({ name: "", description: "" });
  const [legendaryActionInput, setLegendaryActionInput] = useState({ name: "", description: "" });
  const [abilitieInput, setAbilieInput] = useState({ name: "", description: "" });
  const [sensesInput, setSensesInput] = useState({ name: "", value: "" });
  const [speedsInput, setSpeedsInput] = useState({ type: "", value: "" });

  

  const statKeys: Array<"strength" | "dexterity" | "constitution" | "intelligence" | "wisdom" | "charisma"> = [
    "strength",
    "dexterity",
    "constitution",
    "intelligence",
    "wisdom",
    "charisma"
  ];

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>
  ) => {
    const { name, value } = e.target;
    if (name === "actionName" || name === "actionDescription") {
      setActionInput((prev) => ({ ...prev, [name === "actionName" ? "name" : "description"]: value }));
    } else {
      setCreature((prev) => ({ ...prev, [name]: value }));
    }
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      
      const actionsMap = actionsArrayToMap(creature.actions);
      const bonusActionsMap = actionsArrayToMap(creature.bonusActions);
      const reactionsMap = actionsArrayToMap(creature.reactions);
      const legendaryActionsMap = actionsArrayToMap(creature.legendaryActions);
      const abilitiesMap = actionsArrayToMap(creature.abilities);

      const savingThrows = Object.entries(creature.savingThrows)
        .filter(([_, value]) => value)
        .map(([name]) => name);

      const skills = Object.entries(creature.skills)
        .filter(([_, value]) => value)
        .map(([name]) => name);

      

      const requestBody = {
        ...creature,
        damageResistance: creature.damageResistances.join(", "),
        damageVulnerabilities: creature.damageVulnerabilities.join(", "),
        damageImmunities: creature.damageImmunities.join(", "),
        conditionImmunities: creature.conditionImmunities.join(", "),
        senses: creature.senses.map(sense => `${sense.name}: ${sense.value}`).join("ft., "),
        actions: actionsMap,
        bonusActions: bonusActionsMap,
        reactions: reactionsMap,
        legendaryActions: legendaryActionsMap,
        abiliities: abilitiesMap,
        skills: skills,
        savingThrows: savingThrows,
        languages: creature.languages.join(", "),
      };
      const response = await axios.post("http://localhost:8080/creatures/create", requestBody);
      console.log("API response:", response.data);
      // Optionally reset form or show success message here
    } catch (error) {
      console.error("Error submitting creature:", error);
      // Optionally show error message here
    }
  };

  

  return (
    <form onSubmit={handleSubmit} className="creature-form">
      <h2 className="form-title">Create New D&D Creature</h2>

      <div className="form-grid">
        <div className="form-header">
          <input
            type="text"
            name="name"
            placeholder="Name"
            value={creature.name}
            onChange={handleChange}
            className="input"
            required
          />
          <select
            name="size"
            value={creature.size}
            onChange={handleChange}
            className="input"
            required
          >
            <option value="">Select Size</option>
            <option value="Tiny">Tiny</option>
            <option value="Small">Small</option>
            <option value="Medium">Medium</option>
            <option value="Large">Large</option>
            <option value="Huge">Huge</option>
            <option value="Gargantuan">Gargantuan</option>
          </select>
          <input
            type="text"
            name="type"
            placeholder="Type (e.g., Beast)"
            value={creature.type}
            onChange={handleChange}
            className="input"
            required
          />
          <input
            type="text"
            name="alignment"
            placeholder="Alignment"
            value={creature.alignment}
            onChange={handleChange}
            className="input"
            required
          />
          <input
            type="number"
            name="armorClass"
            placeholder="Armor Class"
            value={creature.armorClass}
            onChange={handleChange}
            className="input"
            required
          />
          <input
            type="number"
            name="hitPoints"
            placeholder="Hit Points"
            value={creature.hitPoints}
            onChange={handleChange}
            className="input"
          />
          <input
            type="text"
            name="hitDice"
            placeholder="Hit Dice (e.g., 2d8+2)"
            value={creature.hitDice || ""}
            onChange={handleChange}
            className="input"
            required
          />
        </div>
        <div className="speed-inputs">
          <input
            type="text"
            name="speed-type"
            placeholder="Speed (e.g., walk)"
            value={speedsInput.type}
            onChange={e => setSpeedsInput(prev => ({ ...prev, type: e.target.value }))}
            className="input"
          />
          <input
            type="text"
            name="speed-value"
            placeholder="Value (e.g., 30)"
            value={speedsInput.value}
            onChange={e => setSpeedsInput(prev => ({ ...prev, value: e.target.value }))}
            className="input"
          />
          <button
            type="button"
            onClick={() => {
              if (speedsInput.type && speedsInput.value) {
                setCreature(prev => ({
                  ...prev,
                  speeds: {
                    ...prev.speeds,
                    [speedsInput.type]: speedsInput.value
                  }
                }));
                setSpeedsInput({ type: "", value: "" });
              }
            }}
            className="add-speed-btn"
            >
            Add Speed
          </button>
        </div>
        <div className="speeds-tags">
          {Object.entries(creature.speeds).map(([type, value], idx) => (
            <span key={idx} className="speed-tag">
              {type} {value} ft.
              <button
                type="button"
                className="remove-tag-btn"
                onClick={() => {
                  setCreature(prev => ({
                    ...prev,
                    speeds: Object.fromEntries(
                      Object.entries(prev.speeds).filter(([key]) => key !== type)
                    )
                  }));
                }}
              >
                ×
              </button>
            </span>
          ))}
        </div>
        <div className="skills-inputs">
          {SKILLS.map(skill => (
            <div key={skill}>
              <label>
                <input
                  type="checkbox"
                  checked={creature.skills?.[skill] || false}
                  onChange={(e) => {
                    setCreature(prev => ({
                      ...prev,
                      skills: {
                        ...prev.skills,
                        [skill]: e.target.checked
                      }
                    }));
                  }}
                />
                {skill}
              </label>
            </div>
          ))}
        </div>
        <div className="selectors">
          <Select
            name="damageResistance"
            isMulti
            options={DAMAGE_TYPE_OPTIONS}
            value={DAMAGE_TYPE_OPTIONS.filter(option => creature.damageResistances.includes(option.value))}
            onChange={selectedOptions => {
              setCreature(prev => ({
                ...prev,
                damageResistance: selectedOptions.map(opt => opt.value),
              }));
            }}
            className="input"
            classNamePrefix="select"
            placeholder="Select Damage Resistance"
          />
          <Select
            name="damageVulnerabilities"
            isMulti
            options={DAMAGE_TYPE_OPTIONS}
            value={DAMAGE_TYPE_OPTIONS.filter(option => creature.damageVulnerabilities.includes(option.value))}
            onChange={selectedOptions => {
              setCreature(prev => ({
                ...prev,
                damageVulnerabilities: selectedOptions.map(opt => opt.value),
              }));
            }}
            className="input"
            classNamePrefix="select"
            placeholder="Select Damage Vulnerabilities"
          />
          <Select
            name="damageImmunities"
            isMulti
            options={DAMAGE_TYPE_OPTIONS}
            value={DAMAGE_TYPE_OPTIONS.filter(option => creature.damageImmunities.includes(option.value))}
            onChange={selectedOptions => {
              setCreature(prev => ({
                ...prev,
                damageImmunities: selectedOptions.map(opt => opt.value),
              }));
            }}
            className="input"
            classNamePrefix="select"
            classNames={{
              multiValue: () => 'my-multivalue',
              control: () => 'my-control',
              menu: () => 'my-menu',
              option: ({ isFocused }) => (isFocused ? 'my-option-focused' : 'my-option'),
            }}
            placeholder="Select Damage Immunities"
          />
          <Select
            styles={{}}
            name="conditionImmunities"
            isMulti
            options={CONDITION_IMMUNITY_OPTIONS}
            value={CONDITION_IMMUNITY_OPTIONS.filter(option => creature.conditionImmunities.includes(option.value))}
            onChange={selectedOptions => {
              setCreature(prev => ({
                ...prev,
                conditionImmunities: selectedOptions.map(opt => opt.value),
              }));
            }}
            className="input"
            classNamePrefix="select"
            placeholder="Select Condition Immunities"
          />
        </div>
        <div className="senses-inputs">
          <input
            type="text"
            placeholder="Sense name (e.g., Darkvision)"
            value={sensesInput.name}
            onChange={e => setSensesInput(prev => ({ ...prev, name: e.target.value }))}
            className="input"
          />
          <input
            type="text"
            placeholder="Value (e.g., 60 ft)"
            value={sensesInput.value}
            onChange={e => setSensesInput(prev => ({ ...prev, value: e.target.value }))}
            className="input"
          />
          <button
            type="button"
            onClick={() => {
              if (sensesInput.name && sensesInput.value) {
                setCreature(prev => ({
                  ...prev,
                  senses: [...prev.senses, { ...sensesInput }]
                }));
                setSensesInput({ name: "", value: "" });
              }
            }}
            className="add-sense-btn"
          >
            Add
          </button>
        </div>
        <div className="senses-tags">
          {creature.senses.map((sense, idx) => (
            <span key={idx} className="sense-tag">
              {sense.name}: {sense.value}
              <button
                type="button"
                className="remove-tag-btn"
                onClick={() => {
                  setCreature(prev => ({
                    ...prev,
                    senses: prev.senses.filter((_, i) => i !== idx)
                  }));
                }}
              >
                ×
              </button>
            </span>
          ))}
        </div>
        <input
          type="text"
          name="languages"
          placeholder="Languages (e.g., Common, Elvish)"
          value={creature.languages || ""}
          onChange={handleChange}
          className="input"
        />
        <input
          type="text"
          name="challengeRating"
          placeholder="Challenge Rating/Level (e.g., 1/4, 5)"
          value={creature.challengeRating || ""}
          onChange={handleChange}
          className="input"
          required
        />

        {statKeys.map((stat) => (
          <div key={stat} className="stat-input-block">
            <input
              type="number"
              name={stat}
              placeholder={stat.charAt(0).toUpperCase() + stat.slice(1)}
              value={creature[stat]}
              onChange={handleChange}
              className="input"
            />
            <label>
              <input
                type="checkbox"
                name={`savingThrows[${stat}]`}
                checked={creature.savingThrows[stat] || false}
                onChange={(e) => {
                  setCreature(prev => ({
                    ...prev,
                    savingThrows: {
                      ...prev.savingThrows,
                      [stat]: e.target.checked
                    }
                  }));
                }}
              />
              {stat.charAt(0).toUpperCase() + stat.slice(1)} Saving Throw
            </label>
          </div>
        ))}
      </div>

      {/* Actions input block */}
      <ActionInputBlock
        title="Action"
        input={actionInput}
        setInput={setActionInput}
        actions={creature.actions}
        setActions={(actions) => setCreature(prev => ({ ...prev, actions: actions }))}
      />
      <ActionInputBlock
        title="Bonus Action"
        input={bonusActionInput}
        setInput={setBonusActionInput}
        actions={creature.bonusActions}
        setActions={(actions) => setCreature(prev => ({ ...prev, bonusActions: actions }))}
      />
      <ActionInputBlock
        title="Reaction"
        input={reactionInput}
        setInput={setReactionInput}
        actions={creature.reactions}
        setActions={(actions) => setCreature(prev => ({ ...prev, reactions: actions }))}
      />
      <ActionInputBlock
        title="Legendary Action"
        input={legendaryActionInput}
        setInput={setLegendaryActionInput}
        actions={creature.legendaryActions}
        setActions={(actions) => setCreature(prev => ({ ...prev, legendaryActions: actions }))}
      />
      <ActionInputBlock
        title="Ability"
        input={abilitieInput}
        setInput={setAbilieInput}
        actions={creature.abilities}
        setActions={(actions) => setCreature(prev => ({ ...prev, abiliities: actions }))}
      />
      <textarea
        name="description"
        placeholder="Description (Markdown or plain text)"
        value={creature.description || ""}
        onChange={handleChange}
        className="input"
      />

      <button type="submit" className="submit-btn">
        Create Creature
      </button>
    </form>
  );
}
function actionsArrayToMap(
  actions: { name: string; description: string }[]
): Record<string, string> {
  return actions.reduce((acc, action) => {
    acc[action.name] = action.description;
    return acc;
  }, {} as Record<string, string>);
}