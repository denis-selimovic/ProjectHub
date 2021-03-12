CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS "users" (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    created_at timestamptz,
    updated_at timestamptz,
    CONSTRAINT pkey_users PRIMARY KEY (id)
);