function DemandeSearchForm({ numero, onNumeroChange, onSubmit, loading }) {
    return (
        <form className="search-form" onSubmit={onSubmit}>
            <label className="field">
                <span>Numero</span>
                <input
                    className="input"
                    type="text"
                    value={numero}
                    onChange={(event) => onNumeroChange(event.target.value)}
                    placeholder="Ex: P12345678 ou D-2026-042"
                />
            </label>
            <button className="button" type="submit" disabled={loading || !numero.trim()}>
                {loading ? 'Recherche...' : 'Rechercher'}
            </button>
        </form>
    )
}

export default DemandeSearchForm
