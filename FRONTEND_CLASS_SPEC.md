# 🎨 프론트엔드 클래스 명세서 (Subscription UI)

**작성자:** 프론트엔드 3 (구독 화면 담당)
**수신:** 프론트엔드 1 (UI 총괄/Asset/레이아웃)
**목적:** `main.css` 구축 시 구독 관련 페이지(`list`, `detail`, `form`, `recommendation`)에 적용된 클래스 스타일 가이드라인 제공

## 1. 공통 컴포넌트 (README 규약 준수)
| 클래스명 | 역할 | 비고 |
|:---|:---|:---|
| `.btn-primary` | 주요 액션 버튼 (체크인, 구독하기 등) | |
| `.btn-outline-danger` | 위험 액션 버튼 (해지 등) | |
| `.card` | 모든 컨텐츠의 기본 박스 모델 | |
| `.badge` | 상태 표시용 배지 기본형 | |
| `.badge-normal` | 사용량 정상 상태 (Blue/Green) | |
| `.badge-warning` | 방치 주의 상태 (Yellow/Orange) | |
| `.badge-danger` | 방치 위험 상태 (Red) | |
| `.badge-critical` | 해지 권고 상태 (Deep Red) | |

## 2. 히트맵 시스템 (핵심 차별 UI)
| 클래스명 | 역할 | 스타일 가이드 |
|:---|:---|:---|
| `.heatmap-grid` | 30일 사용량 그리드 컨테이너 | `display: grid`, `grid-template-columns: repeat(10, 1fr)` |
| `.heatmap-large` | 상세 페이지용 확대 히트맵 | 그리드 간격 및 박스 크기 확대 필요 |
| `.heat-box` | 개별 날짜 사각형 | `aspect-ratio: 1/1`, `border-radius: 2px` (추천) |
| `.heat-green` | 사용함 (Checked-in) | 브랜드 메인 컬러 반영 (예: `#4caf50`) |
| `.heat-gray` | 미사용 (Empty) | 연한 회색 (예: `#ebedf0`) |

## 3. 페이지별 레이아웃 클래스
*   **플랜 목록 (`.plan-grid`):** 구독 카드들을 배치하는 반응형 그리드 (minmax 300px 추천)
*   **비교 카드 (`.comparison-card`):** 대체 추천 페이지에서 '현재 서비스'와 '추천 서비스'를 대비시키는 강조 카드 스타일
*   **폼 섹션 (`.form-wrapper`):** 구독 신청 폼의 중앙 정렬 및 여백 관리
*   **빈 상태 (`.empty-state`):** 데이터가 없을 때 표시되는 안내 영역의 스타일

---
**비고:** 모든 HTML은 `layout.html`을 상속받으며, 위 클래스명들은 `templates/plan/` 및 `templates/subscribe/` 하위 파일에 이미 적용되어 있습니다.
