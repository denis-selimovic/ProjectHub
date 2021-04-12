CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS "subscription_configs"
(
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    user_id uuid NOT NULL,
    email varchar(50) UNIQUE NOT NULL,
    created_at timestamptz,
    updated_at timestamptz,
    CONSTRAINT pkey_subscription_configs PRIMARY KEY (id)
);