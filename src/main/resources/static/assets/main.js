window.openModal=function(t,e){const n=document.getElementById("globalModal"),a=document.getElementById("modalTitle"),i=document.getElementById("modalContent");n&&a&&i&&(a.innerText=t,i.innerHTML=e,n.style.display="flex",document.body.style.overflow="hidden")};window.closeModal=function(){const t=document.getElementById("globalModal");t&&(t.style.display="none",document.body.style.overflow="")};window.showToast=function(t,e="success"){let n=document.querySelector(".toast-container");n||(n=document.createElement("div"),n.className="toast-container",document.body.appendChild(n));const a=document.createElement("div");a.className=`toast ${e}`;const i=e==="success"?"bi-check-circle-fill":e==="warning"?"bi-exclamation-triangle-fill":"bi-x-circle-fill";a.innerHTML=`
        <i class="bi ${i}"></i>
        <div class="toast-message">${t}</div>
    `,n.appendChild(a),setTimeout(()=>{a.style.opacity="0",a.style.transform="translateX(100%)",a.style.transition="all 0.3s ease-in",setTimeout(()=>a.remove(),300)},3e3)};window.updateTabBadge=function(t){const e=document.title.replace(/^\(\d+\)\s/,"");document.title=t>0?`(${t}) ${e}`:e};window.formatPrice=function(t){return!t&&t!==0?"-":new Intl.NumberFormat("ko-KR",{style:"currency",currency:"KRW"}).format(t)};window.formatDate=function(t){return t?new Intl.DateTimeFormat("ko-KR").format(new Date(t)):"-"};window.calculatePercent=function(t,e){return!e||e===0?0:Math.round(t/e*100)};window.renderHeatmap=function(t,e=[],n="NORMAL"){const a=document.getElementById(t);if(!a)return;const i=document.createDocumentFragment();for(let c=0;c<30;c++){const o=document.createElement("div");o.className="heat-cell",e[c]===1?(o.classList.add("heat-green"),o.title="사용함"):(o.classList.add("heat-gray"),o.title="미사용"),c>=23&&e[c]!==1&&(n==="WARNING"&&o.classList.add("heat-warning"),(n==="DANGER"||n==="CRITICAL")&&o.classList.add("heat-danger")),i.appendChild(o)}a.innerHTML="",a.appendChild(i)};window.initAutoHeatmaps=function(){document.querySelectorAll("[data-heatmap-data]").forEach(t=>{try{const e=JSON.parse(t.dataset.heatmapData||"[]"),n=t.dataset.alertLevel||"NORMAL";window.renderHeatmap(t.id,e,n)}catch(e){console.error("히트맵 데이터를 읽을 수 없습니다.",e)}})};window.openCheckInModal=function(){openModal("오늘 사용 체크인",`
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
    `)};document.addEventListener("DOMContentLoaded",()=>{window.initAutoHeatmaps()});
