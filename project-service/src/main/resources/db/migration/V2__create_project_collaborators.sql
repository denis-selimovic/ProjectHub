CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE IF NOT EXISTS "project_collaborators"
(
    project_id uuid not null,
    collaborator_id uuid not null,
    CONSTRAINT pkey_pc PRIMARY KEY (project_id, collaborator_id),
    CONSTRAINT fkey_pc_projects FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);