/**
 * SubDiary UI Core Interaction
 * [프론트엔드 1] 공통 UI 컴포넌트 제어 및 상태 관리
 */

/**
 * 토스트 알림 표시
 * @param {string} message - 표시할 메시지
 * @param {string} type - 알림 유형 (success, danger 등)
 */
window.showToast = function(message, type = 'success') {
  // 기존 토스트 제거 (중복 방지)
  const existingToast = document.querySelector('.toast-box');
  if (existingToast) existingToast.remove();

  const toast = document.createElement('div');
  toast.className = `toast-box toast-${type}`;
  
  const icon = type === 'success' ? 'bi-check-circle-fill' : 'bi-exclamation-triangle-fill';
  
  toast.innerHTML = `
    <i class="bi ${icon}"></i>
    <span style="font-weight: 600; font-size: 14.5px;">${message}</span>
  `;
  
  document.body.appendChild(toast);
  
  // 등장 애니메이션
  toast.animate([
    { opacity: 0, transform: 'translateY(20px)' },
    { opacity: 1, transform: 'translateY(0)' }
  ], { duration: 400, easing: 'cubic-bezier(0.175, 0.885, 0.32, 1.275)' });

  // 3.5초 후 제거
  setTimeout(() => {
    if (toast.parentNode) {
      toast.animate([
        { opacity: 1, transform: 'translateY(0)' },
        { opacity: 0, transform: 'translateY(20px)' }
      ], { duration: 400 }).onfinish = () => toast.remove();
    }
  }, 3500);
};

/**
 * 모달 제어
 */
window.openModal = function(modalId) {
  const modal = document.getElementById(modalId);
  if (modal) {
    modal.style.display = 'flex';
    requestAnimationFrame(() => modal.classList.add('active'));
    document.body.style.overflow = 'hidden';
  }
};

window.closeModal = function(modalId) {
  const modal = document.getElementById(modalId);
  if (modal) {
    modal.classList.remove('active');
    modal.addEventListener('transitionend', function handler() {
      modal.style.display = 'none';
      document.body.style.overflow = '';
      modal.removeEventListener('transitionend', handler);
    }, { once: true });
  }
};

window.closeModalByOverlay = function(event) {
  if (event.target.classList.contains('modal-overlay')) {
    window.closeModal(event.target.id);
  }
};

/**
 * 히트맵(잔디) 렌더링 시스템
 */
window.renderHeatmap = function(containerId, data = [], alertLevel = 'NORMAL') {
  const container = document.getElementById(containerId);
  if (!container) return;

  const fragment = document.createDocumentFragment();
  for (let i = 0; i < 30; i++) {
    const cell = document.createElement('div');
    cell.className = 'heat-cell';
    if (data[i] === 1) cell.classList.add('active');
    
    // 마지막 7일 집중 경보
    if (i >= 23 && data[i] !== 1) {
      if (alertLevel === 'WARNING') cell.classList.add('warning');
      else if (alertLevel === 'DANGER') cell.classList.add('danger');
    }
    fragment.appendChild(cell);
  }
  container.innerHTML = '';
  container.appendChild(fragment);
};

/**
 * 자동 히트맵 스캔 (데이터 속성 기반)
 */
window.initAutoHeatmaps = function() {
  document.querySelectorAll('[data-heatmap-data]').forEach(el => {
    try {
      const data = JSON.parse(el.dataset.heatmapData);
      const level = el.dataset.alertLevel || 'NORMAL';
      window.renderHeatmap(el.id, data, level);
    } catch (e) { console.error('Heatmap data parse error', e); }
  });
};

document.addEventListener('DOMContentLoaded', () => {
  console.log('SubDiary Design System Optimized');
  window.initAutoHeatmaps();
  
  // 초기 테스트 데이터 (추후 백엔드 연동 시 제거)
  if (document.getElementById('heatmap-netflix')) {
    window.renderHeatmap('heatmap-netflix', [1,0,1,1,0,1,1,1,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0], 'WARNING');
  }
});
