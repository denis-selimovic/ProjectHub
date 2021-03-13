CREATE TABLE IF NOT EXISTS "statuses" (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    status VARCHAR(100) NOT NULL UNIQUE,
    CONSTRAINT pkey_statuses PRIMARY KEY (id)
);