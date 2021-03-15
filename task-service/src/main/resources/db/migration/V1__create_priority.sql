CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS "priorities" (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    priority VARCHAR(100) NOT NULL UNIQUE,
    CONSTRAINT pkey_priorities PRIMARY KEY (id)
);