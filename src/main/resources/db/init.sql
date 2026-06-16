-- 🎯 SubTrack Database Initialization Script
-- 이 스크립트는 MySQL root 계정으로 실행해야 합니다.

-- 1. 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS subscription
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE subscription;

-- 2. 사용자 생성 및 권한 부여
-- 애플리케이션이 사용할 전용 계정을 생성합니다.
CREATE USER IF NOT EXISTS 'sub_user'@'%' IDENTIFIED BY 'sub_password';
GRANT ALL PRIVILEGES ON subscription.* TO 'sub_user'@'%';
FLUSH PRIVILEGES;

-- 3. 테이블 생성 (ERD 설계 반영)

-- 3-1. member (회원)
CREATE TABLE IF NOT EXISTS member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 3-2. subscription_plan (플랜 템플릿)
CREATE TABLE IF NOT EXISTS subscription_plan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    monthly_price INT NOT NULL,
    yearly_price INT NOT NULL,
    provider_url VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 3-3. member_subscription (사용자의 실제 구독)
CREATE TABLE IF NOT EXISTS member_subscription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    plan_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    due_date DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    last_used_at DATE,
    alert_level VARCHAR(20) DEFAULT 'NORMAL',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (plan_id) REFERENCES subscription_plan(id)
);

-- 3-4. subscription_usage (체크인 기록)
CREATE TABLE IF NOT EXISTS subscription_usage (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_subscription_id BIGINT NOT NULL,
    used_date DATE NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    duration_minutes INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (member_subscription_id) REFERENCES member_subscription(id)
);

-- 3-5. cancellation_log (해지 다이어리)
CREATE TABLE IF NOT EXISTS cancellation_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    plan_id BIGINT NOT NULL,
    cancelled_at DATE NOT NULL,
    tags VARCHAR(255),
    memo TEXT,
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (plan_id) REFERENCES subscription_plan(id)
);

-- 4. 샘플 데이터 삽입 (테스트용)

-- 4-1. 기본 구독 플랜 (넷플릭스, 유튜브 등)
INSERT INTO subscription_plan (name, category, monthly_price, yearly_price, provider_url) VALUES 
('넷플릭스 스탠다드', 'OTT', 13500, 162000, 'https://www.netflix.com'),
('유튜브 프리미엄', 'MUSIC', 14900, 178800, 'https://www.youtube.com/premium'),
('쿠팡 와우 멤버십', 'LIFE', 7890, 94680, 'https://www.coupang.com'),
('스포티파이 프리미엄', 'MUSIC', 10900, 130800, 'https://www.spotify.com'),
('어도비 크리에이티브 클라우드', 'WORK', 62000, 744000, 'https://www.adobe.com');

-- 4-2. 테스트 회원
INSERT INTO member (email, password, name, role) VALUES 
('user@example.com', '{noop}password123', '김철수', 'USER'),
('admin@example.com', '{noop}admin123', '관리자', 'ADMIN');

-- 4-3. 사용자의 실제 구독 정보 (테스트 케이스별 설정)
INSERT INTO member_subscription (member_id, plan_id, start_date, last_used_at, alert_level, status) VALUES 
(1, 1, '2024-01-15', CURDATE(), 'NORMAL', 'ACTIVE'),           -- 최근 사용 (정상)
(1, 2, '2024-02-01', DATE_SUB(CURDATE(), INTERVAL 10 DAY), 'WARNING', 'ACTIVE'), -- 10일 전 사용 (주의)
(1, 3, '2024-03-10', DATE_SUB(CURDATE(), INTERVAL 20 DAY), 'DANGER', 'ACTIVE'),  -- 20일 전 사용 (위험)
(1, 4, '2024-04-05', DATE_SUB(CURDATE(), INTERVAL 40 DAY), 'CRITICAL', 'ACTIVE'); -- 한 달 이상 미사용 (해지권장)

-- 4-4. 샘플 체크인 기록 (최근 5일간)
INSERT INTO subscription_usage (member_subscription_id, used_date, used) VALUES 
(1, DATE_SUB(CURDATE(), INTERVAL 1 DAY), TRUE),
(1, DATE_SUB(CURDATE(), INTERVAL 2 DAY), TRUE),
(1, CURDATE(), TRUE);
