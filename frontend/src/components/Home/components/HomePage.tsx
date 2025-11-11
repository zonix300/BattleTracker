import { useNavigate } from "react-router-dom"
import "../components/HomePage.css"

export const HomePage = () => {
    const navigate = useNavigate();

    return (
    <div className="homepage">
      {/* Title */}
      <h1 className="homepage__title">
        Battle Tracker
      </h1>
      <p className="homepage__subtitle">
        Manage battles, lobbies, and creatures for your D&D sessions.
      </p>

      {/* Main navigation grid */}
      <div className="homepage__grid">
        {/* Start new battle */}
        <button
          onClick={() => navigate("/battle-tracker")}
          className="homepage__card"
        >
          <span className="homepage__card-title">Start New Battle</span>
          <p><span className="homepage__card-desc">
            Run a quick single-player battle tracker
          </span></p>
        </button>

        {/* Create or join lobby */}
        <button
          onClick={() => navigate("/lobbies")}
          className="homepage__card"
        >
          <span className="homepage__card-title">Lobbies</span>
          <p><span className="homepage__card-desc">
            Host or join a multiplayer lobby
          </span></p>
        </button>

        {/* Entity library */}
        <button
          onClick={() => navigate("/entities")}
          className="homepage__card"
        >
          <span className="homepage__card-title">Entities Library</span>
          <p><span className="homepage__card-desc">
            Manage your creatures and NPCs
          </span></p>
        </button>

        {/* Settings */}
        <button
          className="homepage__card"
        >
          <span className="homepage__card-title">Settings</span>
          <p><span className="homepage__card-desc">
            Customize your experience
          </span></p>
        </button>
      </div>
    </div>
  );
};