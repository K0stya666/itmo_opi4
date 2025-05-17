function validateForm() {
    const xSelect = document.querySelector('[id$=":x"]');
    const yInput = document.querySelector('[id$=":y"]');
    const rSelect = document.querySelector('[id$=":r"]');

    // Reset previous error states
    clearErrors();

    let isValid = true;

    // Validate X
    if (!xSelect?.value) {
        showError(xSelect, 'X value is required');
        isValid = false;
    }

    // Validate Y
    if (yInput) {
        const yValue = yInput.value.trim();

        // Check if empty
        if (!yValue) {
            showError(yInput, 'Y value is required');
            isValid = false;
        }
        // Check if it's a valid number
        else if (!/^-?\d*\.?\d+$/.test(yValue)) {
            showError(yInput, 'Y must be a valid number and use a dot as a decimal separator');
            isValid = false;
        }
        // Check range
        else {
            const y = parseFloat(yValue);
            if (y < -3 || y > 5) {
                showError(yInput, 'Y must be between -3 and 5');
                isValid = false;
            }
        }
    }

    // Validate R
    if (!rSelect?.value) {
        showError(rSelect, 'R value is required');
        isValid = false;
    }

    return isValid;
}

function showError(element, message) {
    // Add error class to parent container
    element.parentElement?.classList.add('error');

    // Create or update error message
    let errorDiv = element.parentElement?.querySelector('.error-message');
    if (!errorDiv) {
        errorDiv = document.createElement('div');
        errorDiv.className = 'error-message';
        element.parentElement?.appendChild(errorDiv);
    }
    errorDiv.textContent = message;

    // Highlight the input
    element.classList.add('input-error');
}

function clearErrors() {
    // Remove all error messages and classes
    document.querySelectorAll('.error-message').forEach(el => el.remove());
    document.querySelectorAll('.input-error').forEach(el => el.classList.remove('input-error'));
    document.querySelectorAll('.error').forEach(el => el.classList.remove('error'));
}

// Add form validation
document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');

    // Validate on submit
    form?.addEventListener('submit', function (event) {
        if (!validateForm()) {
            event.preventDefault();
            event.stopPropagation();
            return false;
        }
        return true;
    });

    // Real-time validation for Y input
    const yInput = document.querySelector('[id$=":y"]');
    yInput?.addEventListener('input', function () {
        validateYInput(this);
    });
});

function validateYInput(input) {
    const value = input.value.trim();

    // Allow empty value (will be caught on submit)
    if (!value) return;

    // Check if it's a valid number
    if (!/^-?\d*\.?\d+$/.test(value)) {
        showError(input, 'Y must be a valid number');
        return;
    }

    const y = parseFloat(value);
    if (y < -3 || y > 5) {
        showError(input, 'Y must be between -3 and 5');
    } else {
        clearErrors();
    }
}