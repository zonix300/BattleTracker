import React, { useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route, Navigate, useNavigate } from 'react-router-dom';
import { LobbyPage } from './components/Lobby/Lobby';
import { AuthManager } from './components/Auth/AuthManager';
import { SecureWebSocket } from './components/type/SecureWebSocket';
import { send } from 'process';
import { HomePage } from './components/Home/components/HomePage';
import { BattleTracker } from './components/BattleTracker/BattleTracker';
import { ToastContainer } from 'react-toastify';
import { SideBarProvider } from './components/SideBar/SideBarContext';
import { SideBar } from './components/SideBar/SideBar';
import "../src/App.css"
import { AuthProvider } from './components/Auth/AuthContext';
import { EntitiesPage } from './components/EntitiesPage/EntitiesPage';
import { PCForm } from './components/PCFormPage/PCForm';

type AppLayoutProps = {
  authManager: AuthManager;
  webSocket: SecureWebSocket;
};

const AppLayout = ({authManager, webSocket}: AppLayoutProps) => {
  const [isSideBarOpen, setIsSideBarOpen] = useState(false);

  return (
    <div className={`App-layout${isSideBarOpen ? " sidebar-open" : ""}`}>
      <SideBar isSideBarOpen={isSideBarOpen} setIsSideBarOpen={setIsSideBarOpen}/>
      <main className="app__main-content">
        <Routes>
          <Route path="/" element={<Navigate to="/home" replace />} />
          <Route path="/home" element={<HomePage />} />
          <Route path="/battle-tracker" element={<BattleTracker authManager={authManager} webSocket={webSocket}/>} />
          <Route path="/lobbies" element={<LobbyPage authManager={authManager} webSocket={webSocket}/>} />
          <Route path="/lobby/:lobbyId" element={<BattleTracker authManager={authManager} webSocket={webSocket}/>} />
          <Route path="/entities" element={<EntitiesPage />} />
          <Route path="/pc-sheet/:characterId" element={<PCForm />} />
        </Routes>
      </main>
    </div>
  );
}

function App() {
  const [authManager] = useState(() => new AuthManager());
  const [webSocket] = useState(() => SecureWebSocket.getInstance(authManager));

  useEffect(() => {
    if (authManager.getAuthState() === "AUTHENTICATED") webSocket.connect();
    return () => webSocket.disconnect();
  }, [webSocket]);

  return (
    <AuthProvider>
      <SideBarProvider>
        <BrowserRouter>
          <AppLayout authManager={authManager} webSocket={webSocket}/>
        </BrowserRouter>
        <ToastContainer position="top-right" autoClose={5000} />
      </SideBarProvider>
    </AuthProvider>
  );
}

export default App;
