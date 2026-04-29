import { NavLink, Route, Routes } from 'react-router-dom'
import './App.css'
import DemandeDetail from './pages/DemandeDetail.jsx'
import DemandeSearchList from './pages/DemandeSearchList.jsx'

function App() {
  return (
    <div className="app-shell">
      <header className="topbar">
        <div className="brand">
          <span className="brand-mark">V</span>
          <div>
            <div className="brand-title">Visa Desk</div>
            <div className="brand-subtitle">Suivi des demandes</div>
          </div>
        </div>
        <nav className="nav">
          <NavLink to="/" end>
            Demandes
          </NavLink>
        </nav>
      </header>

      <main className="main">
        <Routes>
          <Route path="/" element={<DemandeSearchList />} />
          <Route path="/demandes/:id" element={<DemandeDetail />} />
        </Routes>
      </main>

      <footer className="footer">
        <span>Visa Desk • Gestion des demandes</span>
        <span>API RestDemandeController</span>
      </footer>
    </div>
  )
}

export default App
