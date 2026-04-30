export const formatDate = (value) => {
  if (!value) return '—'
  const date = new Date(value)
  if (Number.isNaN(date.getTime())) return value
  return date.toLocaleDateString('fr-FR')
}

export const formatDateTime = (value) => {
  if (!value) return '—'
  const date = new Date(value)
  if (Number.isNaN(date.getTime())) return value
  return date.toLocaleString('fr-FR')
}

export const formatDemandeur = (demandeur) => {
  if (!demandeur) return '—'
  const parts = [demandeur.prenom, demandeur.nom].filter(Boolean)
  return parts.length ? parts.join(' ') : '—'
}
