/**
 * main.js - [프론트엔드 1] 공통 스크립트 및 스타일 엔트리
 * 모든 페이지에서 공통으로 사용되는 JS 유틸리티와 CSS를 관리합니다.
 */

// --- 1. CSS 전역 임포트 (Vite 빌드용) ---
import './styles/variable.css';
import './styles/global.css';
import './styles/utilties.css';
import './styles/components/button.css';
import './styles/components/card.css';
import './styles/components/sidebar.css';
import './styles/components/form.css';
import './styles/components/heatmap.css';

// --- 2. 공통 UI 제어 유틸리티 ---

// 공통 모달 열기
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
}

// 공통 모달 닫기
window.closeModal = function() {
    const modal = document.getElementById('globalModal');
    if (modal) {
        modal.style.display = 'none';
        document.body.style.overflow = '';
    }
}

// 토스트 알림 표시
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
}

// 탭 타이틀 배지 갱신 (가이드 명세)
window.updateTabBadge = function(count) {
    const originalTitle = document.title.replace(/^\(\d+\)\s/, '');
    if (count > 0) {
        document.title = `(${count}) ${originalTitle}`;
    } else {
        document.title = originalTitle;
    }
}

// --- 3. 데이터 포맷팅 유틸리티 ---

// 금액 포맷팅 (14900 -> ₩14,900)
window.formatPrice = function(value) {
    if (!value && value !== 0) return '-';
    return new Intl.NumberFormat('ko-KR', {
        style: 'currency',
        currency: 'KRW'
    }).format(value);
}

// 날짜 포맷팅 (2026-06-15 -> 2026. 06. 15.)
window.formatDate = function(dateStr) {
    if (!dateStr) return '-';
    const date = new Date(dateStr);
    return new Intl.DateTimeFormat('ko-KR').format(date);
}

// 퍼센트 계산 (사용량 표시용)
window.calculatePercent = function(current, total) {
    if (!total || total === 0) return 0;
    return Math.round((current / total) * 100);
}

// --- 4. 히트맵(잔디) 렌더링 시스템 [프론트엔드 1 핵심 구현] ---

window.renderHeatmap = function(containerId, data = [], alertLevel = 'NORMAL') {
    const container = document.getElementById(containerId);
    if (!container) return;

    const fragment = document.createDocumentFragment();
    // 가이드 명세: 30칸 그리드
    for (let i = 0; i < 30; i++) {
        const cell = document.createElement('div');
        cell.className = 'heat-cell';
        
        // 가이드 명세 클래스 적용
        if (data[i] === 1) {
            cell.classList.add('heat-green');
        } else {
            cell.classList.add('heat-gray');
        }
        
        // 최근 미사용 시 경보 로직 (마지막 7일 기준)
        if (i >= 23 && data[i] !== 1) {
            if (alertLevel === 'WARNING') cell.classList.add('heat-warning');
            else if (alertLevel === 'DANGER' || alertLevel === 'CRITICAL') cell.classList.add('heat-danger');
        }
        fragment.appendChild(cell);
    }
    container.innerHTML = '';
    container.appendChild(fragment);
};

// 자동 히트맵 스캔 (data-heatmap-data 속성 기반)
window.initAutoHeatmaps = function() {
    document.querySelectorAll('[data-heatmap-data]').forEach(el => {
        try {
            const data = JSON.parse(el.dataset.heatmapData);
            const level = el.dataset.alertLevel || 'NORMAL';
            window.renderHeatmap(el.id, data, level);
        } catch (e) { 
            console.error('Heatmap data parse error', e); 
        }
    });
};

// 오늘의 체크인 모달 (가이드 예시 대응)
window.openCheckInModal = function() {
    const content = `
        <div class="checkin-modal-content">
            <p class="mb-4 text-muted">오늘은 어떤 서비스를 이용하셨나요? 체크인하여 사용량을 기록하세요.</p>
            <div class="checkin-list" style="display: grid; gap: 12px;">
                <button class="btn btn-outline" style="justify-content: flex-start;" onclick="confirmCheckIn('넷플릭스')">
                    <i class="bi bi-play-btn-fill text-error"></i> 넷플릭스
                </button>
                <button class="btn btn-outline" style="justify-content: flex-start;" onclick="confirmCheckIn('유튜브 프리미엄')">
                    <i class="bi bi-youtube text-error"></i> 유튜브 프리미엄
                </button>
                <button class="btn btn-outline" style="justify-content: flex-start;" onclick="confirmCheckIn('스포티파이')">
                    <i class="bi bi-spotify text-success"></i> 스포티파이
                </button>
            </div>
        </div>
    `;
    openModal('오늘의 체크인', content);
}

window.confirmCheckIn = function(serviceName) {
    showToast(`${serviceName} 체크인이 완료되었습니다!`, 'success');
    closeModal();
}

// --- 5. 초기화 ---
document.addEventListener('DOMContentLoaded', () => {
    console.log('SubTrack Frontend System Initialized');
    window.initAutoHeatmaps();
});
