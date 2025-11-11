import { useState } from "react";
import { Effect, PlayerCharacter } from "../../types/PlayerCharacter";
type Props = {
  setPlayerCharacter: React.Dispatch<React.SetStateAction<PlayerCharacter | null>>;
  onClose: () => void;
};

export const EffectModal = ({ setPlayerCharacter, onClose }: Props) => {
  const [name, setName] = useState("");
  const [diceValue, setDiceValue] = useState("");
  const [otherBonus, setOtherBonus] = useState(0);
  const [applyEffectTo, setApplyEffectTo] = useState("");
  const [description, setDescription] = useState("");

  const handleSubmit = () => {
    const newEffect = { name, applyEffectTo, diceValue, otherBonus, description } as Effect;
    setPlayerCharacter(prev => {
      if (!prev) return prev;
      return {
        ...prev,
        combat: {
          ...prev.combat,
          effects: [...(prev.combat.effects || []), newEffect]
        }
      };
    });
    onClose();
  };

  return (
    <div className="modal">
      <h3>Add Effect</h3>
      <input value={name} onChange={e => setName(e.target.value)} placeholder="Name" />
      <input value={diceValue} onChange={e => setDiceValue(e.target.value)} placeholder="Dice value" />
      <input type="number" value={otherBonus} onChange={e => setOtherBonus(+e.target.value)} placeholder="Other bonus" />
      <input value={applyEffectTo} onChange={e => setApplyEffectTo(e.target.value)} placeholder="Applies to" />
      <input value={description} onChange={e => setDescription(e.target.value)} placeholder="Description" />
      <button onClick={handleSubmit}>Add</button>
      <button onClick={onClose}>Cancel</button>
    </div>
  );
};
