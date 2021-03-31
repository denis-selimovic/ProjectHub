CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS "notification_user" (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    notification_id uuid NOT NULL,
    user_id uuid NOT NULL,
    read BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT pkey_notification_user PRIMARY KEY (id),
    CONSTRAINT unique_notification_user UNIQUE (notification_id, user_id),
    CONSTRAINT fkey_notifuser_notifications FOREIGN KEY (notification_id) REFERENCES notifications(id) ON DELETE CASCADE
);