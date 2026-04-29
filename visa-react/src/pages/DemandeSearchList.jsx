import { useState } from 'react'
import { fetchDemandesByNumero } from '../api/demandes.js'
import AlertMessage from '../components/AlertMessage.jsx'
import DemandeSearchForm from '../components/DemandeSearchForm.jsx'
import DemandeTable from '../components/DemandeTable.jsx'
import EmptyState from '../components/EmptyState.jsx'

function DemandeSearchList() {
    const [numero, setNumero] = useState('')
    const [results, setResults] = useState([])
    const [searched, setSearched] = useState(false)
    const [loading, setLoading] = useState(false)
    const [error, setError] = useState('')

    const handleSubmit = async (event) => {
        event.preventDefault()
        const trimmed = numero.trim()
        if (!trimmed) {
            setError('Merci de saisir un numero de passeport ou de demande.')
            setResults([])
            setSearched(false)
            return
        }

        setLoading(true)
        setError('')
        try {
            const data = await fetchDemandesByNumero(trimmed)
            setResults(Array.isArray(data) ? data : [])
            setSearched(true)
        } catch (err) {
            setResults([])
            setSearched(true)
            setError(err?.message || 'Une erreur est survenue.')
        } finally {
            setLoading(false)
        }
    }

    return (
        <div className="stack">
            <section className="panel panel-hero">
                <div className="page-header">
                    <div>
                        <p className="eyebrow">Espace demandes</p>
                        <h1>Rechercher une demande</h1>
                        <p className="subtitle">
                            Saisis un numero de passeport ou de demande pour retrouver les
                            dossiers.
                        </p>
                    </div>
                    <div className="stat-card">
                        <div className="stat-label">API</div>
                        <div className="stat-value">/api/demandes</div>
                    </div>
                </div>

                <DemandeSearchForm
                    numero={numero}
                    onNumeroChange={setNumero}
                    onSubmit={handleSubmit}
                    loading={loading}
                />
                <AlertMessage message={error} />
            </section>

            <section className="panel">
                <div className="section-header">
                    <h2>Liste des demandes</h2>
                    <span className="muted">
                        {searched ? `${results.length} resultat(s)` : '—'}
                    </span>
                </div>

                {!searched && !loading ? (
                    <EmptyState>
                        <p>Commence par lancer une recherche pour afficher des demandes.</p>
                    </EmptyState>
                ) : null}

                {searched && results.length === 0 && !loading && !error ? (
                    <EmptyState>
                        <p>Aucune demande trouvee pour ce numero.</p>
                    </EmptyState>
                ) : null}

                {results.length > 0 ? <DemandeTable demandes={results} /> : null}
            </section>
        </div>
    )
}

export default DemandeSearchList
