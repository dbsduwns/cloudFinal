import './styles/variable.css';
import './styles/global.css';
import './styles/utilties.css';
import './styles/components/button.css';
import './styles/components/card.css';
import './styles/components/sidebar.css';
import './styles/components/form.css';
import './styles/components/heatmap.css';

window.openModal = function(title, contentHtml) {
    const modal = document.getElementById('globalModal');
    const titleEl = document.getElementById('modalTitle');
    const contentEl = document.getElementById('modalContent');

    if (modal && titleEl && contentEl) {
        titleEl.innerText = title;
        contentEl.innerHTML = contentHtml;
        modal.style.display = 'flex';
        document.body.style.overflow = 'hidden';
    }
};

window.closeModal = function() {
    const modal = document.getElementById('globalModal');
    if (modal) {
        modal.style.display = 'none';
        document.body.style.overflow = '';
    }
};

window.showToast = function(message, type = 'success') {
    let container = document.querySelector('.toast-container');
    if (!container) {
        container = document.createElement('div');
        container.className = 'toast-container';
        document.body.appendChild(container);
    }

    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    const icon = type === 'success' ? 'bi-check-circle-fill' : (type === 'warning' ? 'bi-exclamation-triangle-fill' : 'bi-x-circle-fill');

    toast.innerHTML = `
        <i class="bi ${icon}"></i>
        <div class="toast-message">${message}</div>
    `;

    container.appendChild(toast);

    setTimeout(() => {
        toast.style.opacity = '0';
        toast.style.transform = 'translateX(100%)';
        toast.style.transition = 'all 0.3s ease-in';
        setTimeout(() => toast.remove(), 300);
    }, 3000);
};

window.updateTabBadge = function(count) {
    const originalTitle = document.title.replace(/^\(\d+\)\s/, '');
    document.title = count > 0 ? `(${count}) ${originalTitle}` : originalTitle;
};

window.formatPrice = function(value) {
    if (!value && value !== 0) return '-';
    return new Intl.NumberFormat('ko-KR', {
        style: 'currency',
        currency: 'KRW'
    }).format(value);
};

window.formatDate = function(dateStr) {
    if (!dateStr) return '-';
    return new Intl.DateTimeFormat('ko-KR').format(new Date(dateStr));
};

window.calculatePercent = function(current, total) {
    if (!total || total === 0) return 0;
    return Math.round((current / total) * 100);
};

window.renderHeatmap = function(containerId, data = [], alertLevel = 'NORMAL') {
    const container = document.getElementById(containerId);
    if (!container) return;

    const fragment = document.createDocumentFragment();
    for (let i = 0; i < 30; i++) {
        const cell = document.createElement('div');
        cell.className = 'heat-cell';

        if (data[i] === 1) {
            cell.classList.add('heat-green');
            cell.title = '사용함';
        } else {
            cell.classList.add('heat-gray');
            cell.title = '미사용';
        }

        if (i >= 23 && data[i] !== 1) {
            if (alertLevel === 'WARNING') cell.classList.add('heat-warning');
            if (alertLevel === 'DANGER' || alertLevel === 'CRITICAL') cell.classList.add('heat-danger');
        }

        fragment.appendChild(cell);
    }

    container.innerHTML = '';
    container.appendChild(fragment);
};

window.initAutoHeatmaps = function() {
    document.querySelectorAll('[data-heatmap-data]').forEach(el => {
        try {
            const data = JSON.parse(el.dataset.heatmapData || '[]');
            const level = el.dataset.alertLevel || 'NORMAL';
            window.renderHeatmap(el.id, data, level);
        } catch (e) {
            console.error('히트맵 데이터를 읽을 수 없습니다.', e);
        }
    });
};

window.openCheckInModal = function() {
    const content = `
        <div class="checkin-modal-content">
            <p class="mb-4 text-muted">내 구독 관리 화면에서 각 구독의 "오늘 사용" 버튼을 누르면 체크인이 기록됩니다.</p>
            <div class="modal-action-list">
                <a class="btn btn-primary btn-full" href="/subscriptions">
                    <i class="bi bi-layers-fill"></i>
                    내 구독 관리로 이동
                </a>
                <a class="btn btn-outline btn-full" href="/plans">
                    <i class="bi bi-plus-lg"></i>
                    구독 추가하기
                </a>
            </div>
        </div>
    `;
    openModal('오늘 사용 체크인', content);
};

document.addEventListener('DOMContentLoaded', () => {
    window.initAutoHeatmaps();
});
