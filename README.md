# SubTrack - 구독 관리 서비스

## ERD

### member
| 컬럼 | 타입 | 설명 |
|------|------|------|
| id | BIGINT PK | |
| email | VARCHAR(100) UNIQUE | 로그인 ID |
| password | VARCHAR(255) | BCrypt 암호화 |
| name | VARCHAR(50) | 사용자 이름 |
| role | VARCHAR(20) | ADMIN / USER |
| created_at | DATETIME | 가입일 |

### subscription_plan (플랜 템플릿)
| 컬럼 | 타입 | 설명 |
|------|------|------|
| id | BIGINT PK | |
| name | VARCHAR(100) | 예: 넷플릭스 스탠다드 |
| category | VARCHAR(50) | OTT, MUSIC, CLOUD, ETC |
| monthly_price | INT | 월간 비용(원) |
| yearly_price | INT | 연간 비용(원) |
| provider_url | VARCHAR(500) | 해지/관리 링크 |

### member_subscription (사용자 구독)
| 컬럼 | 타입 | 설명 |
|------|------|------|
| id | BIGINT PK | |
| member_id | BIGINT FK | |
| plan_id | BIGINT FK | |
| start_date | DATE | 구독 시작일 |
| due_date | DATE | 다음 결제일 |
| status | VARCHAR(20) | ACTIVE, CANCELLED, PAUSED |
| last_used_at | DATE | 마지막 체크인일 |
| alert_level | VARCHAR(20) | NORMAL, WARNING, DANGER, CRITICAL |
| created_at | DATETIME | |

### subscription_usage (체크인 기록)
| 컬럼 | 타입 | 설명 |
|------|------|------|
| id | BIGINT PK | |
| member_subscription_id | BIGINT FK | |
| used_date | DATE | 체크인 날짜 |
| used | BOOLEAN | 사용 여부 |
| duration_minutes | INT | 선택적 사용시간 |
| created_at | DATETIME | |

### cancellation_log (해지 다이어리)
| 컬럼 | 타입 | 설명 |
|------|------|------|
| id | BIGINT PK | |
| member_id | BIGINT FK | |
| plan_id | BIGINT FK | |
| cancelled_at | DATE | 해지일 |
| tags | VARCHAR(255) | 콤마 구분 태그 |
| memo | TEXT | 선택적 메모 |

## Controller-View Model 규약
| 화면 | URL | Model Key | 타입 |
|------|-----|-----------|------|
| 플랜 목록 | GET / | plans | List<Plan> |
| 플랜 상세 | GET /plans/{id} | plan | Plan |
| 회원가입 | GET /join | memberForm | MemberForm |
| 로그인 | /login | (Security 처리) | - |
| 마이페이지 | GET /member/mypage | member | Member |
| 내 구독 | GET /subscriptions | subscriptions | List<MemberSubscription> |
| 사용통계 | GET /member/usage-summary | usageStats | UsageStatsDto |

## 역할 분담
- 팀장: 인프라/통합/배포/인사이트/해지다이어리
- 백엔드1: 회원/인증/알림
- 백엔드2: 구독/체크인/집계/경보
- 프론트1: 레이아웃/CSS/JS/npm
- 프론트2: 회원 화면
- 프론트3: 구독 화면
