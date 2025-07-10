import React from 'react';
import CreateCreatureForm from './components/CreatureForm/CreatureForm';
import BattleTracker from './components/BattleTracker/BattleTracker';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

function App() {
  return (
    <Router>
      <Routes>
        <Route path='/' element={<BattleTracker />} />
        <Route path='/create' element={<CreateCreatureForm />} />
      </Routes>
    </Router>
  );
}

export default App;
