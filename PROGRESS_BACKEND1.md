# 📝 백엔드 1 (회원·인증·알림) 작업 보고서

## 1. 역할 확인
본 작업자는 README.md에 정의된 **백엔드 1** 역할을 수행하였으며, 추가로 프론트엔드 2와의 원활한 연동을 위해 필요한 DTO 및 공통 유틸리티 작업을 병행하였습니다.

## 2. 주요 구현 내역

### 📂 도메인 및 엔티티 (Domain & Entity)
- `Member.java`: 회원 정보 (이메일, 비밀번호, 이름, 전화번호, 권한, 생성일)
- `Role.java`: 사용자 권한 Enum (`USER`, `ADMIN`)
- `CancellationLog.java`: 해지 기록 저장 엔티티
- `CancellationTag.java`: 해지 사유 Enum (`EXPENSIVE`, `NOT_USED` 등)
- `MemberSubscription.java`: (협업용) 사용자 구독 상태 및 날짜 정보
- `SubscriptionPlan.java`: (협업용) 플랜 기본 정보 스켈레톤

### 📂 리포지토리 (Repository)
- `MemberRepository.java`: 이메일 기반 회원 조회 (`findByEmail`)
- `MemberSubscriptionRepository.java`: 회원별 구독 목록 조회 (`findByMemberId`)

### 📂 서비스 (Service)
- `MemberService.java`: 
    - `join()`: 회원가입 (BCrypt 비밀번호 암호화)
    - `update()`: 회원 정보 수정
    - `findByEmail()`: 프로필 조회
- `CustomUserDetailsService.java`: Spring Security 인증 연동 (UserDetails 구현)
- `ReminderService.java`: 매일 오전 9시 알림 발송 스케줄러 (`@Scheduled`)
- `UsageAnalyticsService.java`: 대시보드용 데이터 공급 (현재 프론트 연동용 더미 데이터)

### 📂 컨트롤러 및 DTO (Controller & DTO)
- `MemberController.java`: `/join`, `/member/mypage`, `/member/update` 경로 처리
- `LoginController.java`: `/login` 경로 및 에러 메시지 처리
- `UsageAnalyticsController.java`: `/member/usage-summary` 대시보드 처리
- `MemberForm.java`: 회원가입 및 수정 폼 데이터 객체
- `UsageStats.java`: 대시보드 및 마이페이지용 통합 통계 DTO

### 📂 기타 공통 작업
- `MailUtil.java`: 이메일 발송 기초 기능 구현
- `SubscriptionApplication.java`: JPA Auditing 활성화 (`@EnableJpaAuditing`)

## 3. 프론트엔드 2 (회원 화면) 연동 현황
프론트엔드 2가 작성한 HTML 템플릿과 백엔드 데이터를 100% 매핑 완료하였습니다.
- **로그인/가입**: 폼 필드 일치 확인 및 에러 메시지 처리 완료
- **마이페이지**: 회원 정보, 구독 목록, 요약 통계 데이터 공급 완료
- **대시보드**: 지출 트렌드(바 차트), 다음 결제일, 경보 수치 등 모든 UI 요소에 데이터 바인딩 완료

## 4. 향후 과제
- 백엔드 2(구독 코어)의 로직이 완성되면 `UsageAnalyticsService`의 더미 데이터를 실제 DB 쿼리 기반으로 교체
- `ReminderService`에 실제 만료 임박 구독자 추출 로직 추가
