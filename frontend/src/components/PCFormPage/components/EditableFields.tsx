import { useRef, useState } from "react"
type EditableFieldProps = {
    value: string | number,
    onChange: (temp: string | number) => void;
}

export const EditableField = ({ value, onChange }: EditableFieldProps) => {
    const [editing, setEditing] = useState(false);
    const [temp, setTemp] = useState(value);
    const inputRef = useRef<HTMLInputElement | null>(null);

    const handleBlur = () => {
        setEditing(false);
        onChange(temp);
    };

    const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if (e.key === "Enter") {
            inputRef.current?.blur();
        }
    }

    return editing ? (
        <input
            ref={inputRef}
            type="text"
            value={temp}
            onChange={(e) => setTemp(e.target.value)}
            onBlur={handleBlur}
            onKeyDown={handleKeyDown}
            autoFocus
        />
    ) : (
        <span onClick={() => setEditing(true)}>{value}</span>
    );
}