CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS "email_configs" (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    email VARCHAR(50) NOT NULL,
    user_id uuid NOT NULL,
    CONSTRAINT pkey_econfigs PRIMARY KEY (id)
);