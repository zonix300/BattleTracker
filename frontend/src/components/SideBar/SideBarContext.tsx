import { createContext, ReactNode, useCallback, useContext, useState } from "react";

export type SideBarItem = {
    id: string,
    label: string,
    icon?: ReactNode,
    onClick?: () => void,
    onExpand?: () => ReactNode;
};

type SideBarContextType = {
    items: SideBarItem[] | undefined,
    addItem: (item: SideBarItem) => void,
    removeItem: (id: string) => void;
};

const SideBarContext = createContext<SideBarContextType | undefined>(undefined);

export const SideBarProvider: React.FC<{children: ReactNode}> = ({ children } : { children: ReactNode }) => {
    const [items, setItems] = useState<SideBarItem[]>();

    const addItem = useCallback((item: SideBarItem) => {
        setItems(prev => {
            if (!prev) prev = [item];
            const existingIndex = prev.findIndex(i => i.id === item.id);
            if (existingIndex !== -1) {
                const updated = [...prev];
                updated[existingIndex] = item;
                return updated;
            }
            return [...prev, item];
        })
    }, []);

    const removeItem = useCallback((id: string) => {
        setItems(prev => {
            if (!prev) return undefined;
            prev.filter(i => i.id !== id);
        });
    }, [])

    return (
        <SideBarContext.Provider value={{ items, addItem, removeItem }}>
            {children}
        </SideBarContext.Provider>
    )
}

export const useSideBar = () => {
    const context = useContext(SideBarContext);
    if (!context) throw new Error("useSideBar must be used within SideBarProvider");
    return context;
}