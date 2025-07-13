import React from 'react';
import CreateCreatureForm from './components/CreatureForm/CreatureForm';
import BattleTracker from './components/BattleTracker/BattleTracker';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { ProtectedRoute } from './components/ProtectedRoute/ProtectedRoute'
import { Register } from './components/Auth/Register/Register'
import { ToastContainer } from 'react-toastify';
import { Login } from './components/Auth/Login/Login';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
        
        <Route 
          path="/battle-tracker"
          element={
            <ProtectedRoute>
              <BattleTracker />
            </ProtectedRoute>
          }
        />
      </Routes>
      <ToastContainer
        position="bottom-left"
        autoClose={3000}
      />
    </BrowserRouter>
  );
}

export default App;
