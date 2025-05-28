document.getElementById('waitlist-form').addEventListener('submit', async function (e) {
    e.preventDefault()

    const form = this
    const submitBtn = document.getElementById('submit-btn')
    const submitIcon = document.getElementById('submit-icon')
    const loadingSpinner = document.getElementById('loading-spinner')
    const alertSection = document.getElementById('alert-section')
    const alert = document.getElementById('alert')
    const alertIcon = document.getElementById('alert-icon')
    const alertMessage = document.getElementById('alert-message')
    const emailInput = document.getElementById('email')
    const projectInput = document.getElementById('project-name')

    // loading state
    submitBtn.disabled = true
    submitIcon.classList.add('hidden')
    loadingSpinner.classList.remove('hidden')

    try {
        const payload = {
            email: emailInput.value,
            projectName: projectInput.value
        }

        const response = await fetch(form.action, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-Requested-With': 'XMLHttpRequest'
            },
            body: JSON.stringify(payload)
        })

        const result = await response.json()

        // hide form and show result
        form.classList.add('hidden')
        alertSection.classList.remove('hidden')

        switch (result.status) {
            case 'new':
                alert.className = 'alert alert-success'
                alertIcon.textContent = 'check'
                alertMessage.textContent = "You're in!"
                break
            case 'exists':
                alert.className = 'alert alert-success'
                alertIcon.textContent = 'check'
                alertMessage.textContent = "You're already in!"
                break
            case 'error':
            default:
                alert.className = 'alert alert-error'
                alertIcon.textContent = 'close'
                alertMessage.textContent = "Something went wrong. Try again later"
                break
        }

    } catch (error) {
        // hide form and show error
        form.classList.add('hidden')
        alertSection.classList.remove('hidden')
        alert.className = 'alert alert-error'
        alertIcon.textContent = 'close'
        alertMessage.textContent = "Something went wrong. Try again later"
    }

    submitBtn.disabled = false
    submitIcon.classList.remove('hidden')
    loadingSpinner.classList.add('hidden')
})
