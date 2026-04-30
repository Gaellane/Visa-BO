import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { fetchDemandeDetails } from '../api/demandes.js'
import AlertMessage from '../components/AlertMessage.jsx'
import DetailRow from '../components/DetailRow.jsx'
import EmptyState from '../components/EmptyState.jsx'
import StatusBadge from '../components/StatusBadge.jsx'
import { formatDateTime, formatDemandeur } from '../utils/formatters.js'

function DemandeDetail() {
    const { id } = useParams()
    const [data, setData] = useState(null)
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState('')

    useEffect(() => {
        let active = true

        const load = async () => {
            setLoading(true)
            setError('')
            try {
                const response = await fetchDemandeDetails(id)
                if (active) {
                    setData(response)
                }
            } catch (err) {
                if (active) {
                    setError(err?.message || 'Une erreur est survenue.')
                }
            } finally {
                if (active) {
                    setLoading(false)
                }
            }
        }

        load()

        return () => {
            active = false
        }
    }, [id])

    const demande = data?.demande
    const histories = data?.histories || []

    return (
        <div className="stack">
            <section className="panel">
                <div className="section-header">
                    <div>
                        <p className="eyebrow">Fiche demande</p>
                        <h1>Demande #{id}</h1>
                    </div>
                    <Link className="link" to="/">
                        Retour a la liste
                    </Link>
                </div>

                {loading ? <EmptyState>Chargement...</EmptyState> : null}
                <AlertMessage message={error} />

                {!loading && !error && demande ? (
                    <div className="detail-grid">
                        <div className="detail-card">
                            <h2>Resume</h2>
                            <DetailRow label="Numero" value={demande.numero || '—'} />
                            <DetailRow
                                label="Date"
                                value={formatDateTime(demande.dateDemande)}
                            />
                            <DetailRow
                                label="Demandeur"
                                value={formatDemandeur(demande.demandeur)}
                            />
                            <DetailRow
                                label="Type"
                                value={demande.type?.valeur || '—'}
                            />
                            <DetailRow
                                label="Visa"
                                value={demande.typeVisa?.valeur || '—'}
                            />
                            <DetailRow
                                label="Statut actuel"
                                value={<StatusBadge value={demande.status?.valeur} />}
                            />
                        </div>

                        <div className="detail-card">
                            <h2>Historique des statuts</h2>
                            {histories.length === 0 ? (
                                <EmptyState>
                                    <p>Aucun historique disponible.</p>
                                </EmptyState>
                            ) : (
                                <div className="history-list">
                                    {histories.map((history) => (
                                        <div className="history-item" key={history.id}>
                                            <div className="history-item-header">
                                                <StatusBadge value={history.status?.valeur} />
                                                <span className="muted">
                                                    {formatDateTime(history.dateChangement)}
                                                </span>
                                            </div>
                                            <p className="history-motif">
                                                {history.motif || 'Aucun motif.'}
                                            </p>
                                        </div>
                                    ))}
                                </div>
                            )}
                        </div>
                    </div>
                ) : null}
            </section>
        </div>
    )
}

export default DemandeDetail
