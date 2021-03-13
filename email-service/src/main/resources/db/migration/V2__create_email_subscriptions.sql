CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS "email_subsrciptions" (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    task_id uuid NOT NULL,
    config_id uuid NOT NULL,
    CONSTRAINT pkey_esubscriptions PRIMARY KEY (id),
    CONSTRAINT fkey_esubscription_econfigs FOREIGN KEY (config_id) REFERENCES email_configs(id) ON DELETE CASCADE
);