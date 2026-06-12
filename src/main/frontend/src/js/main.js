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
  const toast = document.createElement('div');
  toast.className = 'toast-box';
  
  const icon = type === 'success' ? 'bi-check-circle-fill' : 'bi-exclamation-triangle-fill';
  
  toast.innerHTML = `
    <i class="bi ${icon}"></i>
    <span style="font-weight: 600; font-size: 14.5px;">${message}</span>
  `;
  
  document.body.appendChild(toast);
  
  // 등장 애니메이션
  toast.style.opacity = '0';
  toast.style.transform = 'translateY(20px)';
  toast.style.transition = 'all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275)';
  
  requestAnimationFrame(() => {
    toast.style.opacity = '1';
    toast.style.transform = 'translateY(0)';
  });

  // 3.5초 후 자동 제거
  setTimeout(() => {
    toast.style.opacity = '0';
    toast.style.transform = 'translateY(20px)';
    setTimeout(() => toast.remove(), 400);
  }, 3500);
};

/**
 * 모달 열기/닫기 제어
 */
window.openModal = function(modalId) {
  const modal = document.getElementById(modalId);
  if (modal) {
    modal.style.display = 'flex';
    setTimeout(() => modal.classList.add('active'), 10);
    document.body.style.overflow = 'hidden';
  }
};

window.closeModal = function(modalId) {
  const modal = document.getElementById(modalId);
  if (modal) {
    modal.classList.remove('active');
    setTimeout(() => {
      modal.style.display = 'none';
      document.body.style.overflow = '';
    }, 300);
  }
};

window.closeModalByOverlay = function(event) {
  if (event.target.classList.contains('modal-overlay')) {
    window.closeModal(event.target.id);
  }
};

/**
 * 탭 타이틀 알림 배지 (가이드 준수)
 */
window.updateTabBadge = function(count) {
  const originalTitle = document.title.replace(/^\(\d+\) /, '');
  document.title = count > 0 ? `(${count}) ${originalTitle}` : originalTitle;
};

document.addEventListener('DOMContentLoaded', () => {
  console.log('SubDiary Design System Initialized');
});
