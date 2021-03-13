CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS "project_collaborators"
(
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    project_id uuid not null,
    collaborator_id uuid not null,
    CONSTRAINT pkey_pc PRIMARY KEY (id),
    CONSTRAINT unique_pc UNIQUE (project_id, collaborator_id)
);