CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS "priorities" (
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    priority VARCHAR(100) NOT NULL UNIQUE,
    CONSTRAINT pkey_priorities PRIMARY KEY (id)
);

INSERT INTO priorities (priority) VALUES ('CRITICAL');
INSERT INTO priorities (priority) VALUES ('HIGH');
INSERT INTO priorities (priority) VALUES ('MEDIUM');
INSERT INTO priorities (priority) VALUES ('LOW');