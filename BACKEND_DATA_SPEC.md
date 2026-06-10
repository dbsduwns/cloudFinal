# ⚙️ 백엔드 데이터 명세서 (Model & API)

**작성자:** 프론트엔드 3 (구독 화면 담당)
**수신:** 백엔드 2 (구독/체크인), 팀장 (인사이트/추천)
**참조:** README 5번 (Model 규약) 및 6번 (ERD) 기반

## 1. 화면별 Model Key 및 데이터 요구사항

### A. 플랜 목록 (`GET /`)
*   **Model Key:** `plans` (List<MemberSubscription>)
*   **필수 데이터 필드:**
    *   `plan.name`, `plan.category`, `plan.id`
    *   `alertLevel`: NORMAL, WARNING, DANGER, CRITICAL (String)
    *   `monthlyUsageCount`: 최근 30일 총 체크인 횟수 (Integer)
    *   `recentUsage`: 30개의 Boolean 리스트 (List<Boolean>)
    *   `todayCheckedIn`: 오늘 날짜의 체크인 완료 여부 (Boolean)

### B. 플랜 상세 (`GET /plans/{id}`)
*   **Model Key:** `plan` (SubscriptionPlan), `subscription` (MemberSubscription), `usageStats` (List<SubscriptionUsage>)
*   **필수 데이터 필드:**
    *   `subscription.startDate`, `subscription.dueDate`, `subscription.lastUsedAt`
    *   `usageStats` 내 각 객체의 `usedDate`(LocalDate), `used`(Boolean)

### C. 대체 추천 (`GET /recommendations`)
*   **Model Key:** `recommendations` (List<RecommendationDto>)
*   **필수 데이터 필드:**
    *   `currentSubscription`: 현재 사용자의 방치 중인 구독 정보
    *   `suggestedPlan`: 추천하고자 하는 새로운 플랜 정보
    *   `reason`: 추천 사유 (예: "월 요금을 5,000원 아낄 수 있습니다")
    *   `expectedSavings`: 절약 예상 금액 (Integer)

## 2. API Endpoint 규약 (Form 전송 및 액션)
| 기능 | Method | URL | 필요한 파라미터 |
|:---|:---|:---|:---|
| 체크인 기록 | `POST` | `/checkin/{id}` | `id` (MemberSubscription PK) |
| 구독 신청 | `POST` | `/subscribe` | `planId`, `startDate`, `dueDate` (Form Data) |
| 구독 해지 | `POST` | `/subscriptions/cancel/{id}` | `id` (MemberSubscription PK) |

---
**비고:** 위 Key 값들은 Thymeleaf 템플릿에 엄격히 반영되어 있으므로, Controller에서 Model에 담을 때 반드시 위 명칭을 준수해 주시기 바랍니다.
