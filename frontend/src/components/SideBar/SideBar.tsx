import { PropsWithChildren, useEffect, useState } from "react"
import { SideBarItem, useSideBar } from "./SideBarContext";
import { useNavigate } from "react-router-dom";
import "./SideBar.css"
import { useAuth } from "../Auth/AuthContext";
import { ReactComponent as Accout } from "../../icons/accout.svg";
import { ReactComponent as Logout } from "../../icons/logout.svg";

type SideBarProps = {
    isSideBarOpen: boolean;
    setIsSideBarOpen: React.Dispatch<React.SetStateAction<boolean>>
}
export const SideBar = ({isSideBarOpen, setIsSideBarOpen} : SideBarProps) => {
    const navigate = useNavigate();
    const [selectedSideBarItem, setSelectedSideBarItem] = useState<SideBarItem | null>(null);
    const { items } = useSideBar();
    const auth = useAuth();

    const handleHomeButtonClick = () => {
        navigate("/home");
    }

    const handleItemButtonClick = (item: SideBarItem) => {
        setIsSideBarOpen(!isSideBarOpen);

        if (item.onExpand) {
            setSelectedSideBarItem(item);
        }
        if (item.onClick) {
            item.onClick();
        }
    }

    const handleLoginButtonClick = () => {
        if (!auth) {
            throw new Error("Not able to create Authentication Context");
        }
        auth.openAuthModal();
    }

    const handleLogoutButtonClick = () => {
        if (!auth) {
            throw new Error("Not able to create Authentication Context");
        }
        auth.logout();
        navigate("/home");
    }

    useEffect(() => {
        setIsSideBarOpen(false);
        items?.map((item) => console.log((item.id)));
    }, [items]);

    return (
        <div className={`sidebar__container${isSideBarOpen ? "" : ""}`}>
            <aside className={`sidebar${isSideBarOpen ? "" : ""}`}>
                <div className="">

                </div>
                
                <nav className="sidebar__nav">
                    <button 
                        className="sidebar__item"
                        onClick={handleHomeButtonClick}
                    >Home
                    </button>
                    <div className="sidebar__main">
                    {items ? items.map((item) => (
                        <button 
                            key={item.id}
                            className="sidebar__item"
                            onClick={() => handleItemButtonClick(item)}
                            > <span className="sidebar__icon">{item.icon ?? "-"}</span>
                        </button>
                        
                    )) : null}
                    </div>
                    <div className="sidebar__footer">
                        
                        {auth?.checkAuth() ? (
                            <button
                                className="sidebar__item"
                                onClick={handleLogoutButtonClick}
                            ><Logout className="sidebar__accout-svg" />

                            </button>
                            
                        ) : (
                            <button
                                className="sidebar__item"
                                onClick={handleLoginButtonClick}
                            ><Accout className="sidebar__accout-svg"/>

                            </button>
                        )}
                    </div>
                </nav>
            </aside>
            {selectedSideBarItem && isSideBarOpen && (
                <div className="sidebar__expand">
                    {selectedSideBarItem.onExpand && selectedSideBarItem.onExpand()}
                </div>
            )}
        </div>
    );
}