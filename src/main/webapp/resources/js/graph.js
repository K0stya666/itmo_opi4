let tempPoint = null;
let boundaryAnimation = null;
let boundaryOpacity = 0;
let boundaryY = null;

function drawGraph() {
    const canvas = document.getElementById('graph');
    if (!canvas) {
        console.error('Canvas element not found');
        return;
    }

    const ctx = canvas.getContext('2d');
    const width = canvas.width;
    const height = canvas.height;
    const centerX = width / 2;
    const centerY = height / 2;

    // Clear canvas
    ctx.clearRect(0, 0, width, height);

    // Adjust scale based on R value to ensure graph fits
    const scale = Math.min(width, height) / 6;

    // Enhanced styling
    ctx.strokeStyle = 'rgba(44, 62, 80, 0.8)';
    ctx.fillStyle = 'rgba(44, 62, 80, 0.8)';
    ctx.lineWidth = 2;

    // Draw coordinate system
    ctx.beginPath();
    ctx.moveTo(0, centerY);
    ctx.lineTo(width, centerY);
    ctx.moveTo(centerX, 0);
    ctx.lineTo(centerX, height);
    ctx.stroke();

    // Draw arrows
    ctx.beginPath();
    ctx.moveTo(width - 10, centerY - 5);
    ctx.lineTo(width, centerY);
    ctx.lineTo(width - 10, centerY + 5);
    ctx.moveTo(centerX - 5, 10);
    ctx.lineTo(centerX, 0);
    ctx.lineTo(centerX + 5, 10);
    ctx.stroke();

    // Draw shapes
    ctx.fillStyle = 'rgba(52, 152, 219, 0.3)';

    // Rectangle in second quadrant
    ctx.beginPath();
    ctx.moveTo(centerX - scale, centerY);
    ctx.lineTo(centerX, centerY);
    ctx.lineTo(centerX, centerY - (2 * scale));
    ctx.lineTo(centerX - (scale), centerY - (2 * scale));
    ctx.closePath();
    ctx.fill();

    // Triangle in first quadrant
    ctx.beginPath();
    ctx.moveTo(centerX, centerY);
    ctx.lineTo(centerX + (scale), centerY);
    ctx.lineTo(centerX, centerY - (2 * scale));
    ctx.closePath();
    ctx.fill();

    // Quarter circle in third quadrant
    ctx.beginPath();
    ctx.arc(centerX, centerY, scale, Math.PI / 2, Math.PI, false);
    ctx.lineTo(centerX, centerY);
    ctx.closePath();
    ctx.fill();

    // Draw axis labels
    ctx.fillStyle = 'rgba(44, 62, 80, 0.9)';
    ctx.font = '13px "Segoe UI", sans-serif';
    ctx.textAlign = 'center';
    ctx.textBaseline = 'middle';

    // X axis labels
    ctx.fillText('R', centerX + (2 * scale), centerY + 20);
    ctx.fillText('R/2', centerX + (scale), centerY + 20);
    ctx.fillText('-R/2', centerX - (scale), centerY + 20);
    ctx.fillText('-R', centerX - (2 * scale), centerY + 20);

    // Y axis labels
    ctx.fillText('R', centerX - 20, centerY - (2 * scale));
    ctx.fillText('R/2', centerX - 20, centerY - (scale));
    ctx.fillText('-R/2', centerX - 20, centerY + (scale));
    ctx.fillText('-R', centerX - 20, centerY + (2 * scale));

    // Get R value with validation
    const rSelect = document.querySelector('[id$=":r"]');
    const RSelected = rSelect && !isNaN(parseFloat(rSelect.value)) ? parseFloat(rSelect.value) : 0;

    // Draw boundary animation if active
    if (boundaryOpacity > 0 && boundaryY !== null && RSelected) {
        const scale = Math.min(canvas.width, canvas.height) / 6;
        const yPos = centerY - (boundaryY * scale / RSelected * 2);

        ctx.strokeStyle = `rgba(231, 76, 60, ${boundaryOpacity})`;
        ctx.lineWidth = 2;
        ctx.beginPath();
        ctx.moveTo(0, yPos);
        ctx.lineTo(width, yPos);
        ctx.stroke();
    }

    // Draw all points from the table
    drawAllPoints(ctx, centerX, centerY, scale, RSelected);

    // Draw temporary point if exists
    if (tempPoint) {
        drawPoint(ctx, tempPoint.x, tempPoint.y, 'rgba(52, 152, 219, 0.8)');
    }
}

function drawPoint(ctx, x, y, color) {
    ctx.fillStyle = color;
    ctx.beginPath();
    ctx.arc(x, y, 4, 0, 2 * Math.PI);
    ctx.fill();
}

function animateBoundary(yValue) {
    if (boundaryAnimation) {
        cancelAnimationFrame(boundaryAnimation);
    }

    boundaryOpacity = 0.8;
    boundaryY = yValue;

    function fade() {
        boundaryOpacity -= 0.02;
        if (boundaryOpacity > 0) {
            drawGraph();
            boundaryAnimation = requestAnimationFrame(fade);
        } else {
            boundaryAnimation = null;
            boundaryY = null;
            drawGraph();
        }
    }

    boundaryAnimation = requestAnimationFrame(fade);
}

function drawAllPoints(ctx, centerX, centerY, scale, currentR) {
    const table = document.querySelector('.data-table');
    if (!table || !currentR) return;

    const rows = table.getElementsByTagName('tr');
    console.log(`Found ${rows.length} rows`);
    for (let i = 1; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName('td');
        console.log(`Row ${i} cells:`, cells);
        if (cells.length >= 4) {
            const x = parseFloat(cells[0].textContent);
            const y = parseFloat(cells[1].textContent);
            const r = parseFloat(cells[2].textContent);
            console.log(`Parsed values - x: ${x}, y: ${y}, r: ${r}`);

            if (r !== currentR) continue;

            const hit = cells[3].textContent.trim().toLowerCase() === 'yes';
            const scaledX = centerX + (x * scale * 2 / currentR);
            const scaledY = centerY - (y * scale * 2 / currentR);

            console.log(`Drawing point at - scaledX: ${scaledX}, scaledY: ${scaledY}, hit: ${hit}`);
            drawPoint(ctx, scaledX, scaledY, hit ? '#2ecc71' : '#e74c3c');
        }
    }

}

// Initial draw with error handling
window.onload = () => {
    try {
        drawGraph();
    } catch (error) {
        console.error('Error during initial graph drawing:', error);
    }
}

// Handle canvas clicks
document.getElementById('graph')?.addEventListener('click', function (event) {
    try {
        const canvas = event.target;
        const rect = canvas.getBoundingClientRect();
        const x = event.clientX - rect.left;
        const y = event.clientY - rect.top;
        const centerX = canvas.width / 2;
        const centerY = canvas.height / 2;

        const rSelect = document.querySelector('[id$=":r"]');
        if (!rSelect || !rSelect.value) {
            alert('Please select R value first');
            return;
        }
        const R = parseFloat(rSelect.value);

        const scale = Math.min(canvas.width, canvas.height) / 6;

        // Convert coordinates with correct scaling
        let xCoordinate = ((x - centerX) / scale * (R / 2));
        let yCoordinate = ((centerY - y) / scale * (R / 2));

        // Find closest valid X value
        const validXValues = [-3, -2, -1, 0, 1, 2, 3, 4, 5];
        const closestX = validXValues.reduce((prev, curr) => {
            return (Math.abs(curr - xCoordinate) < Math.abs(prev - xCoordinate) ? curr : prev);
        });

        // Clamp Y value to valid range and round to 2 decimal places
        if (yCoordinate > 5 || yCoordinate < -3) {
            yCoordinate = Math.min(Math.max(yCoordinate, -3), 5);
            animateBoundary(yCoordinate);
        }
        yCoordinate = Math.round(yCoordinate * 100) / 100;

        // Store temporary point with correct scaling
        tempPoint = {
            x: centerX + (closestX * scale / R * 2),
            y: centerY - (yCoordinate * scale / R * 2)
        };

        // Update form inputs
        const xSelect = document.querySelector('[id$=":x"]');
        const yInput = document.querySelector('[id$=":y"]');

        if (xSelect && yInput) {
            xSelect.value = closestX;
            yInput.value = yCoordinate;
        }

        // Redraw graph with temporary point
        drawGraph();
    } catch (error) {
        console.error('Error handling canvas click:', error);
    }
});

// Clear temporary point when form is submitted
document.querySelector('form')?.addEventListener('submit', function (event) {
    if (!validateForm()) {
        event.preventDefault();
    } else {
        tempPoint = null;
    }
});

// Redraw graph when R value changes
document.querySelector('[id$=":r"]')?.addEventListener('change', function () {
    tempPoint = null;
    drawGraph();
});