// MasteryModal.tsx
import { useState } from "react";
import { PlayerCharacter } from "../../types/PlayerCharacter";


type Props = {
  setPlayerCharacter: React.Dispatch<React.SetStateAction<PlayerCharacter | null>>;
  onClose: () => void;
};

export const MasteryModal = ({ setPlayerCharacter, onClose }: Props) => {
  const [name, setName] = useState("");
  const [items, setItems] = useState("");
  const [description, setDescription] = useState("");

  const handleSubmit = () => {
    const newMastery = { name, items, description };
    setPlayerCharacter(prev => {
      if (!prev) return prev;
      return {
        ...prev,
        combat: {
          ...prev.combat,
          masteries: [...(prev.combat.masteries || []), newMastery]
        }
      };
    });
    onClose();
  };

  return (
    <div className="modal">
      <h3>Add Mastery</h3>
      <input value={name} onChange={e => setName(e.target.value)} placeholder="Name" />
      <input value={items} onChange={e => setItems(e.target.value)} placeholder="Items/Property" />
      <textarea value={description} onChange={e => setDescription(e.target.value)} placeholder="Description" />
      <button onClick={handleSubmit}>Add</button>
      <button onClick={onClose}>Cancel</button>
    </div>
  );
};
