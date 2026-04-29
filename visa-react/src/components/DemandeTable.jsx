import { Link } from 'react-router-dom'
import StatusBadge from './StatusBadge.jsx'
import { formatDate, formatDemandeur } from '../utils/formatters.js'

function DemandeTable({ demandes }) {
    return (
        <div className="table-wrapper">
            <table className="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Numero</th>
                        <th>Date</th>
                        <th>Demandeur</th>
                        <th>Type</th>
                        <th>Visa</th>
                        <th>Statut</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {demandes.map((demande) => (
                        <tr key={demande.id}>
                            <td>{demande.id}</td>
                            <td>{demande.numero || '—'}</td>
                            <td>{formatDate(demande.dateDemande)}</td>
                            <td>{formatDemandeur(demande.demandeur)}</td>
                            <td>{demande.type?.valeur || '—'}</td>
                            <td>{demande.typeVisa?.valeur || '—'}</td>
                            <td>
                                <StatusBadge value={demande.status?.valeur} />
                            </td>
                            <td>
                                <Link className="link" to={`/demandes/${demande.id}`}>
                                    Voir la fiche
                                </Link>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}

export default DemandeTable
