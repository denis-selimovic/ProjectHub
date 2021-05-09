CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS "system_events" (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    timestamp timestamptz NOT NULL,
    service VARCHAR(255) NOT NULL,
    principal VARCHAR(255) NOT NULL,
    method VARCHAR(50) NOT NULL,
    action VARCHAR(50) NOT NULL,
    resource VARCHAR(100) NOT NULL,
    request_url VARCHAR(255) NOT NULL,
    status INT NOT NULL,
    CONSTRAINT pkey_system_events PRIMARY KEY (id)
);