const downloadBtn = document.getElementById("download-csv-btn")
const copyBtn = document.getElementById("copy-embed-btn")

if (downloadBtn) downloadBtn.addEventListener('click', exportToCSV)
if (copyBtn) copyBtn.addEventListener('click', copyWaitlistEmbed)

function exportToCSV() {
    const table = document.querySelector('.table')
    if (!table) return

    const rows = Array.from(table.querySelectorAll('tr'))
    const csv = rows.map(row => {
        const cells = Array.from(row.querySelectorAll('th, td'))
        return cells.map(cell => `"${cell.textContent.trim()}"`).join(',')
    }).join('\n')

    const projectName = document.getElementById('project-name')?.value || 'waitlist'
    const filename = `${projectName}-waitlist-${new Date().toISOString().split('T')[0]}.csv`

    const blob = new Blob([csv], { type: 'text/csv' })
    const url = window.URL.createObjectURL(blob)
    const a = document.createElement('a')
    a.href = url
    a.download = filename
    document.body.appendChild(a)
    a.click()
    document.body.removeChild(a)
    window.URL.revokeObjectURL(url)
}

async function copyWaitlistEmbed() {
    const projectName = document.getElementById('project-name').value
    const BASE_URL = "http://localhost:8080/waitlist?projectName="
    const embedCode = `<iframe src="${BASE_URL}${projectName}" width="100%" height="300" style="border:none"></iframe>`

    try {
        await navigator.clipboard.writeText(embedCode)
        alert('Embed code copied to clipboard! ✅')
    } catch (err) {
        console.error('Failed to copy:', err)
        alert('Failed to copy. Please try again later. ❌')
    }
}
