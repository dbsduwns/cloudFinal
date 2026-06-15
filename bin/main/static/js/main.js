// SubDiary Common JS System

// 모달 제어 함수
window.openModal = function(id) {
    const modal = document.getElementById(id);
    if (modal) {
        modal.style.display = 'flex';
        setTimeout(() => modal.classList.add('active'), 10);
    }
};

window.closeModal = function(id) {
    const modal = document.getElementById(id);
    if (modal) {
        modal.classList.remove('active');
        setTimeout(() => modal.style.display = 'none', 300);
    }
};

window.closeModalByOverlay = function(event) {
    if (event.target.classList.contains('modal-overlay')) {
        event.target.classList.remove('active');
        setTimeout(() => event.target.style.display = 'none', 300);
    }
};

// 토스트 알림 기능 (가이드 명시)
window.showToast = function(message, type = 'success') {
    // 기존 토스트 제거
    const existingToast = document.querySelector('.toast-container');
    if (existingToast) existingToast.remove();

    const toast = document.createElement('div');
    toast.className = `toast-container toast-${type}`;
    toast.style.cssText = `
        position: fixed; bottom: 30px; left: 50%; transform: translateX(-50%);
        background: #1A1D23; color: white; padding: 12px 24px; border-radius: 12px;
        font-weight: 600; z-index: 5000; box-shadow: 0 10px 30px rgba(0,0,0,0.2);
        animation: toast-in 0.3s forwards;
    `;
    toast.innerText = message;
    document.body.appendChild(toast);

    setTimeout(() => {
        toast.style.animation = 'toast-out 0.3s forwards';
        setTimeout(() => toast.remove(), 300);
    }, 3000);
};

// 애니메이션 스타일 동적 추가
const style = document.createElement('style');
style.textContent = `
    @keyframes toast-in { from { bottom: 0; opacity: 0; } to { bottom: 30px; opacity: 1; } }
    @keyframes toast-out { from { bottom: 30px; opacity: 1; } to { bottom: 0; opacity: 0; } }
`;
document.head.appendChild(style);

console.log("SubDiary Common JS Loaded");