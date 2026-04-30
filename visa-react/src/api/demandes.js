const API_BASE = import.meta.env.VITE_API_BASE_URL ?? ''

async function requestJson(path) {
  const response = await fetch(`${API_BASE}${path}`, {
    headers: { Accept: 'application/json' },
  })

  if (!response.ok) {
    const message = `Erreur ${response.status} sur ${path}`
    throw new Error(message)
  }

  const contentType = response.headers.get('content-type') || ''
  if (contentType.includes('application/json')) {
    return response.json()
  }

  return null
}

export function fetchDemandesByNumero(numero) {
  const safeNumero = encodeURIComponent(numero)
  return requestJson(`/api/demandes/${safeNumero}`)
}

export function fetchDemandeDetails(id) {
  return requestJson(`/api/demandes/details/${id}`)
}
