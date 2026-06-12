-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS subscription
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE subscription;

-- 사용자 생성
CREATE USER IF NOT EXISTS 'sub_user'@'localhost'
    IDENTIFIED BY 'sub_password';

GRANT ALL PRIVILEGES ON subscription.*
    TO 'sub_user'@'localhost';

FLUSH PRIVILEGES;

-- 1. member (회원)
CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(50) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 2. subscription_plan (플랜 템플릿)
CREATE TABLE subscription_plan (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    monthly_price INT NOT NULL,
    yearly_price INT NOT NULL,
    provider_url VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 3. member_subscription (사용자의 실제 구독)
CREATE TABLE member_subscription (
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

-- 4. subscription_usage (체크인 기록)
CREATE TABLE subscription_usage (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_subscription_id BIGINT NOT NULL,
    used_date DATE NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    duration_minutes INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (member_subscription_id) REFERENCES member_subscription(id)
);

-- 5. cancellation_log (해지 다이어리)
CREATE TABLE cancellation_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    plan_id BIGINT NOT NULL,
    cancelled_at DATE NOT NULL,
    tags VARCHAR(255),
    memo TEXT,
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (plan_id) REFERENCES subscription_plan(id)
);
