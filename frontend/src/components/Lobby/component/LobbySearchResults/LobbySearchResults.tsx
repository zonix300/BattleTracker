import { Lobby } from "../../type/Lobby";
import "../../Lobby.css";

type LobbySearchResultsProps = {
    lobbies?: Lobby[],
    selectedLobby?: Lobby,
    setSelectedLobby: React.Dispatch<React.SetStateAction<Lobby | undefined>>
}

export const LobbySearchResults: React.FC<LobbySearchResultsProps> = ({lobbies, selectedLobby, setSelectedLobby}) => {

    const handleLobbyRowClick = (lobby: Lobby) => {
        if (selectedLobby === lobby) {
            setSelectedLobby(undefined);
        } else {
            setSelectedLobby(lobby);
        }
    }

    const formatTimeAgo = (dateString: string) : string => {
        const date = new Date(dateString);
        const now  = new Date();
        const diffMs = now.getTime() - date.getTime();
        const diffSec = Math.floor(diffMs/1000);
        const diffMin = Math.floor(diffSec/60);
        const diffHr = Math.floor(diffMin/60);
        const diffDay = Math.floor(diffHr/24);

        if (diffSec < 60) return "just now";
        if (diffMin < 60) return `${diffMin} minute${diffMin > 1 ? "s" : ""} ago`;
        if (diffHr < 24) return `${diffHr} hour${diffHr > 1 ? "s" : ""} ago`;
        if (diffHr < 7) return `${diffDay} day${diffDay > 1 ? "s" : ""} ago`;

        return date.toLocaleDateString();
    }

    return (
        <div className="lobby-search-results">
            <table className="lobby-search-results__table">
                <thead className="lobby-search-results__header">
                    <tr>
                        <th>Name</th>
                        <th>DM</th>
                        <th>Number of players</th>
                        <th>Created</th>
                    </tr>
                </thead>
                <tbody className="lobby-search-results__body">
                    {lobbies?.map(lobby => (
                        <tr 
                        key={lobby.id}
                        className={`lobby-search-results__row${selectedLobby?.id === lobby.id ? "--selected" : ""}`}
                        onClick={() => handleLobbyRowClick(lobby)}
                        >
                            <td className="lobby-search-results__lobby-name">{lobby.name}</td>
                            <td className="lobby-search-results__owner-name">{lobby.ownerUsername}</td>
                            <td className="lobby-search-results__players-number">{lobby.players.length}/6</td>
                            <td className="lobby-search-results__created-at">{formatTimeAgo(lobby.createdAt)}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <div className="lobby-search-results__pagination">
            </div>
        </div>
    );
}