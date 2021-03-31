CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS "notifications" (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at timestamptz,
    updated_at timestamptz,
    read BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT pkey_notifications PRIMARY KEY (id)
);