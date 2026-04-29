// BiblioSENA - Funcionalidades Frontend

document.addEventListener('DOMContentLoaded', function() {
    // Inicializar modales
    initModals();
    
    // Inicializar navegación
    initNavigation();
    
    // Inicializar búsquedas
    initSearch();
});

// Abrir modales
function initModals() {
    document.querySelectorAll('[data-modal]').forEach(btn => {
        btn.addEventListener('click', () => {
            const modalId = btn.dataset.modal;
            const modal = document.getElementById(modalId);
            if (modal) modal.showModal();
        });
    });
    
    // Cerrar modales
    document.querySelectorAll('.modal-close').forEach(btn => {
        btn.addEventListener('click', () => {
            btn.closest('dialog').close();
        });
    });
    
    // Cerrar al hacer click fuera
    document.querySelectorAll('dialog').forEach(modal => {
        modal.addEventListener('click', (e) => {
            if (e.target === modal) modal.close();
        });
    });
}

// Marcar enlace activo
function initNavigation() {
    const currentPage = window.location.pathname.split('/').pop() || 'index.html';
    document.querySelectorAll('.nav__link').forEach(link => {
        if (link.getAttribute('href') === currentPage) {
            link.classList.add('nav__link--active');
        }
    });
}

// Búsqueda en tablas
function initSearch() {
    const searchInputs = document.querySelectorAll('input[type="search"]');
    searchInputs.forEach(input => {
        if (input.closest('.toolbar')) {
            input.addEventListener('input', (e) => {
                const term = e.target.value.toLowerCase();
                const table = document.querySelector('table');
                if (table) {
                    table.querySelectorAll('tbody tr').forEach(row => {
                        row.style.display = row.textContent.toLowerCase().includes(term) ? '' : 'none';
                    });
                }
            });
        }
    });
}

// Exportar funciones globales
window.BiblioSENA = {
    showModal: (id) => document.getElementById(id)?.showModal(),
    closeModal: (id) => document.getElementById(id)?.close(),
    search: (term) => {
        document.querySelectorAll('tbody tr').forEach(row => {
            row.style.display = row.textContent.toLowerCase().includes(term) ? '' : 'none';
        });
    }
};