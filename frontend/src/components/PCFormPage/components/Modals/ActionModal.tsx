import { useState } from "react";
import { Action, PlayerCharacter } from "../../types/PlayerCharacter";

type Props = {
  setPlayerCharacter: React.Dispatch<React.SetStateAction<PlayerCharacter | null>>;
  onClose: () => void;
};

export const ActionModal = ({ setPlayerCharacter, onClose }: Props) => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [type, setType] = useState("action"); // action | bonusAction | reaction | free_action

  const handleSubmit = () => {
    const newAction = { name, description, type } as Action;
    setPlayerCharacter(prev => {
      if (!prev) return prev;
      return {
        ...prev,
        combat: {
          ...prev.combat,
          actions: [...(prev.combat.actions || []), newAction]
        }
      };
    });
    onClose();
  };

  return (
    <div className="modal">
      <h3>Add Action</h3>
      <input value={name} onChange={e => setName(e.target.value)} placeholder="Action Name" />
      <textarea value={description} onChange={e => setDescription(e.target.value)} placeholder="Description" />
      <select value={type} onChange={e => setType(e.target.value)}>
        <option value="action">Action</option>
        <option value="bonusAction">Bonus Action</option>
        <option value="reaction">Reaction</option>
        <option value="free_action">Free Action</option>
      </select>
      <button onClick={handleSubmit}>Add</button>
      <button onClick={onClose}>Cancel</button>
    </div>
  );
};
