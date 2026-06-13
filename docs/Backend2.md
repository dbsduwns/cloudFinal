# 📋 Backend 2 개발 수행 보고서 (SubTrack)

**담당 역할:** 구독·체크인·집계·경보 (핵심 도메인)  
**작업 브랜치:** `feature/backend2`

---

## 1. 개발 개요
사용자의 구독 서비스를 관리하고, 실제 사용 여부를 기록(체크인)하여 방치된 구독을 파악할 수 있는 핵심 비즈니스 로직을 설계 및 구현하였습니다.

---

## 2. 단계별 개발 상세

### [1단계] 도메인 모델 설계 및 엔티티 구현
구독 서비스 관리에 필요한 핵심 데이터 구조를 설계하고 JPA 엔티티로 구현하였습니다.

*   **`SubscriptionPlan.java`**: 서비스에서 제공하는 구독 상품(예: 넷플릭스, 유튜브 프리미엄)의 기본 정보(가격, 카테고리 등) 정의.
*   **`MemberSubscription.java`**: 사용자가 실제로 구독 중인 상품 정보. 시작일, 상태, 경보 단계(`AlertLevel`) 등을 포함.
*   **`SubscriptionUsage.java`**: 사용자의 실제 서비스 이용 기록(체크인 데이터)을 저장.
*   **`AlertLevel.java` (Enum)**: 구독 방치 상태를 구분하는 단계 정의 (`NORMAL`, `WARNING`, `DANGER`, `CRITICAL`).

### [2단계] 데이터 접근 계층(Repository) 구축
Spring Data JPA를 활용하여 각 엔티티에 대한 CRUD 및 커스텀 쿼리 인터페이스를 생성하였습니다.

*   **`SubscriptionPlanRepository.java`**: 전체 플랜 목록 및 상세 조회.
*   **`MemberSubscriptionRepository.java`**: 특정 회원의 구독 목록 조회.
*   **`SubscriptionUsageRepository.java`**: 특정 기간 내의 체크인 기록 조회.

### [3단계] 비즈니스 로직(Service) 구현
프로젝트의 핵심 차별점인 "체크인"과 "방치 경보" 로직을 구현하였습니다.

*   **`CheckInService.java`**: 사용자가 '오늘 사용함'을 클릭했을 때 이용 기록을 생성하고, 구독 정보의 '마지막 사용일'을 갱신.
*   **`UsageStatisticsService.java`**: 최근 30일간의 데이터를 분석하여 총 체크인 횟수와 이용 시간을 집계.
*   **`AlertLevelResolver.java`**: 마지막 사용일로부터 오늘까지의 기간을 계산하여 경보 등급을 자동으로 결정하는 알고리즘 구현.
    *   7일 미만: `NORMAL`
    *   14일 미만: `WARNING`
    *   30일 미만: `DANGER`
    *   30일 이상: `CRITICAL`
*   **`SubscriptionService.java`**: 플랜 조회 및 회원 구독 관리를 총괄하는 통합 서비스.

### [4단계] 웹 인터페이스(Controller) 연결
Thymeleaf 템플릿과 데이터를 주고받기 위한 컨트롤러를 구현하고, 프론트엔드 팀과 약속된 Model 규약을 준수하였습니다.

*   **`PlanController.java`**: 메인 페이지(`/`)의 플랜 목록 노출 및 상세 페이지 연결.
*   **`SubscriptionController.java`**: 
    *   내 구독 목록 조회 (`GET /subscriptions`)
    *   새로운 구독 신청 (`POST /subscribe`)
    *   이용 체크인 처리 (`POST /checkin/{id}`)

### [5단계] 프로젝트 구조 및 환경 최적화
*   **`.gitignore` 최상위 이동**: 프로젝트 루트에 `.gitignore`를 배치하여 `build/`, `.idea/`, `application-local.yml` 등 불필요하거나 민감한 파일이 Git에 포함되지 않도록 최적화.

---

## 3. 생성 및 수정 파일 목록

| 구분 | 파일 경로 | 내용 |
| :--- | :--- | :--- |
| **Entity** | `.../domain/entity/SubscriptionPlan.java` | 구독 상품 정보 정의 |
| | `.../domain/entity/MemberSubscription.java` | 사용자 구독 정보 정의 |
| | `.../domain/entity/SubscriptionUsage.java` | 이용 기록(체크인) 정의 |
| | `.../domain/entity/AlertLevel.java` | 경보 단계 Enum |
| **Repository** | `.../repository/SubscriptionPlanRepository.java` | 플랜 데이터 접근 |
| | `.../repository/MemberSubscriptionRepository.java` | 회원 구독 데이터 접근 |
| | `.../repository/SubscriptionUsageRepository.java` | 이용 기록 데이터 접근 |
| **Service** | `.../service/SubscriptionService.java` | 구독 관련 통합 로직 |
| | `.../service/CheckInService.java` | 체크인 처리 로직 |
| | `.../service/UsageStatisticsService.java` | 이용량 통계 분석 로직 |
| | `.../service/AlertLevelResolver.java` | 경보 단계 결정 로직 |
| **Controller** | `.../controller/PlanController.java` | 플랜 목록/상세 컨트롤러 |
| | `.../controller/SubscriptionController.java` | 내 구독/체크인 컨트롤러 |
| **Config** | `.gitignore` | 프로젝트 제외 파일 설정 (최적화) |

---

## 4. 향후 과제
1. **Security 연동**: 현재 하드코딩된 `MemberID`를 실제 로그인 세션 정보와 연동.
2. **데이터 유효성 검사**: 체크인 중복 방지 및 날짜 범위 검증 로직 강화.
3. **단위 테스트**: `AlertLevelResolver` 알고리즘에 대한 테스트 케이스 보완.
