CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS "projects"
(
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    name varchar(50) NOT NULL,
    owner_id uuid NOT NULL,
    created_at timestamptz,
    updated_at timestamptz,
    CONSTRAINT pkey_projects PRIMARY KEY (id)
);

