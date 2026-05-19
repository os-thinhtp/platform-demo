CREATE DATABASE IF NOT EXISTS platform_demo;
USE platform_demo;

CREATE TABLE IF NOT EXISTS applications (
  id BIGINT PRIMARY KEY,
  business_key VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(120) NOT NULL,
  description VARCHAR(255) NOT NULL,
  launcher_url VARCHAR(255) NOT NULL,
  display_order INT NOT NULL,
  enabled BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS user_mappings (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  external_user_id VARCHAR(120) NOT NULL,
  local_username VARCHAR(120) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS audit_logs (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(120) NOT NULL,
  action VARCHAR(120) NOT NULL,
  target VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO applications (id, business_key, name, description, launcher_url, display_order, enabled) VALUES
  (1, 'business-a', 'Business A', 'Existing business portal A integrated through platform SSO.', 'http://localhost:8084/business-a/', 1, TRUE),
  (2, 'business-b', 'Business B', 'Existing business portal B integrated through platform SSO.', 'http://localhost:8084/business-b/', 2, TRUE)
ON DUPLICATE KEY UPDATE
  name = VALUES(name),
  description = VALUES(description),
  launcher_url = VALUES(launcher_url),
  display_order = VALUES(display_order),
  enabled = VALUES(enabled);
