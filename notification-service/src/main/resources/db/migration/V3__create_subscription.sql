CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS "subscriptions" (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    config_id uuid NOT NULL,
    task_id uuid NOT NULL,
    created_at timestamptz,
    updated_at timestamptz,
    CONSTRAINT pkey_subscriptions PRIMARY KEY (id),
    CONSTRAINT unique_user_task UNIQUE (config_id, task_id),
    CONSTRAINT fk_config FOREIGN KEY (config_id) references subscription_configs(id)
);