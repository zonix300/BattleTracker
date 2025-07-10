import React from "react";

interface Action {
    name: string;
    description: string;
}

interface ActionInputBlockProps {
    title: string;
    input: Action;
    setInput: React.Dispatch<React.SetStateAction<Action>>;
    actions: Action[];
    setActions: (actions: Action[]) => void;
}

const ActionInputBlock: React.FC<ActionInputBlockProps> = ({
    title,
    input,
    setInput,
    actions,
    setActions,
}) => {
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setInput(prev => ({...prev, [name]: value}));
    };

    const handleAdd = (e: React.FormEvent) => {
        e.preventDefault();
        if (input.name.trim() && input.description.trim()) {
            input.name = input.name.trim();
            input.description = input.description.trim();

            input.name = input.name.charAt(0).toUpperCase() + input.name.slice(1);
            input.description = input.description.charAt(0).toUpperCase() + input.description.slice(1);

            input.name = input.name.endsWith('.') ? input.name : input.name + '.';
            input.description = input.description.endsWith('.') ? input.description : input.description + '.';

            setActions([...actions, {...input}]);
            setInput({ name: "", description: "" });
        }
    };

    return (
        <div className="actions-block">
            <div className="actions-inputs">
                <input
                    type="text"
                    name="name"
                    placeholder={`${title} Name`}
                    value={input.name}
                    onChange={handleChange}
                    className="action-input"
                />
                <input
                    type="text"
                    name="description"
                    placeholder={`${title} Description`}
                    value={input.description}
                    onChange={handleChange}
                    className="action-input"
                />
                <button
                    type="submit"
                    onClick={handleAdd}
                    className="add-action-btn"
                >
                    Add {title}
                </button>
            </div>
            {actions.length > 0 && (
                <div className="actions-list">
                    {actions.map((action, index) => (
                        <div key={index} className="action-item">
                            <span className="action-name">
                                {action.name + ' '}
                            </span>
                            <span className="action-description">{action.description}</span>
                        </div>
                    ))}
                </div>
                )}
        </div>
    );
};

export default ActionInputBlock;