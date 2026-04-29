export const statusClass = (status) => {
  if (!status) return 'status-unknown'
  return `status-${status.toLowerCase().replace(/[^a-z0-9]+/g, '-')}`
}
