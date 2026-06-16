window.openModal=function(t,e){const n=document.getElementById("globalModal"),o=document.getElementById("modalTitle"),i=document.getElementById("modalContent");n&&o&&i&&(o.innerText=t,i.innerHTML=e,n.style.display="flex",document.body.style.overflow="hidden")};window.closeModal=function(){const t=document.getElementById("globalModal");t&&(t.style.display="none",document.body.style.overflow="")};window.showToast=function(t,e="success"){let n=document.querySelector(".toast-container");n||(n=document.createElement("div"),n.className="toast-container",document.body.appendChild(n));const o=document.createElement("div");o.className=`toast ${e}`;const i=e==="success"?"bi-check-circle-fill":e==="warning"?"bi-exclamation-triangle-fill":"bi-x-circle-fill";o.innerHTML=`
        <i class="bi ${i}"></i>
        <div class="toast-message">${t}</div>
    `,n.appendChild(o),setTimeout(()=>{o.style.opacity="0",o.style.transform="translateX(100%)",o.style.transition="all 0.3s ease-in",setTimeout(()=>o.remove(),300)},3e3)};window.updateTabBadge=function(t){const e=document.title.replace(/^\(\d+\)\s/,"");t>0?document.title=`(${t}) ${e}`:document.title=e};window.formatPrice=function(t){return!t&&t!==0?"-":new Intl.NumberFormat("ko-KR",{style:"currency",currency:"KRW"}).format(t)};window.formatDate=function(t){if(!t)return"-";const e=new Date(t);return new Intl.DateTimeFormat("ko-KR").format(e)};window.calculatePercent=function(t,e){return!e||e===0?0:Math.round(t/e*100)};window.renderHeatmap=function(t,e=[],n="NORMAL"){const o=document.getElementById(t);if(!o)return;const i=document.createDocumentFragment();for(let c=0;c<30;c++){const a=document.createElement("div");a.className="heat-cell",e[c]===1?a.classList.add("heat-green"):a.classList.add("heat-gray"),c>=23&&e[c]!==1&&(n==="WARNING"?a.classList.add("heat-warning"):(n==="DANGER"||n==="CRITICAL")&&a.classList.add("heat-danger")),i.appendChild(a)}o.innerHTML="",o.appendChild(i)};window.initAutoHeatmaps=function(){document.querySelectorAll("[data-heatmap-data]").forEach(t=>{try{const e=JSON.parse(t.dataset.heatmapData),n=t.dataset.alertLevel||"NORMAL";window.renderHeatmap(t.id,e,n)}catch(e){console.error("Heatmap data parse error",e)}})};window.openCheckInModal=function(){openModal("오늘의 체크인",`
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
    `)};window.confirmCheckIn=function(t){showToast(`${t} 체크인이 완료되었습니다!`,"success"),closeModal()};document.addEventListener("DOMContentLoaded",()=>{console.log("SubTrack Frontend System Initialized"),window.initAutoHeatmaps()});
