function DetailRow({ label, value }) {
    const displayValue = value ?? '—'
    return (
        <div className="detail-row">
            <span>{label}</span>
            <strong>{displayValue}</strong>
        </div>
    )
}

export default DetailRow
