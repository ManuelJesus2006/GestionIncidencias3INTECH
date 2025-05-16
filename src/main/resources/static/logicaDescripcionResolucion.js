const estadoSelect = document.getElementById('nuevoEstado');
const resolucionDiv = document.getElementById('resolucionDiv');
const descripcionResolucionTextarea = document.getElementById('descripcionResolucion');

function toggleResolucionField() {
    if (estadoSelect.value === '2') {
        resolucionDiv.style.display = 'block';
        descripcionResolucionTextarea.required = true;
    } else {
        resolucionDiv.style.display = 'none';
        descripcionResolucionTextarea.required = false;
    }
}

document.addEventListener('DOMContentLoaded', toggleResolucionField);

estadoSelect.addEventListener('change', toggleResolucionField);