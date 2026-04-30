import { statusClass } from '../utils/status.js'

function StatusBadge({ value, fallback = 'Inconnu' }) {
    const label = value || fallback
    return <span className={`status-badge ${statusClass(value)}`}>{label}</span>
}

export default StatusBadge
