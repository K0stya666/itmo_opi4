function updateClock() {
    const clockElement = document.getElementById('clock');
    if (!clockElement) return;

    const now = new Date();
    const options = {
        weekday: 'long',
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        timeZoneName: 'short'
    };

    try {
        clockElement.textContent = new Intl.DateTimeFormat('en-US', options).format(now);
        clockElement.style.opacity = '1';
    } catch (error) {
        console.error('Error formatting date:', error);
        clockElement.textContent = now.toLocaleString();
    }
}

// Add fade transition
const style = document.createElement('style');
style.textContent = `
    .clock {
        transition: opacity 0.5s ease-in-out;
        opacity: 0;
    }
`;
document.head.appendChild(style);

// Update immediately and then every 8 seconds
updateClock();
setInterval(updateClock, 8000);